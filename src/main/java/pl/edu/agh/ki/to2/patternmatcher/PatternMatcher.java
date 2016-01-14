package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.IMatcher;
import pl.edu.agh.ki.to2.patternmatcher.matcher.MatcherFactory;
import pl.edu.agh.ki.to2.patternmatcher.matcher.MatcherFactory.MatcherType;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.patternmatcher.ui.controllers.PatternController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PatternMatcher extends AbstractMatchProvider implements IPatternMatcher {

    private MonitorPubSub monitor;

    private List<SearchPattern> patterns = new ArrayList<>();
    private IWordProvider wordProvider;

    public PatternMatcher(IWordProvider wordProvider, MonitorPubSub monitor) {
        super();
        this.monitor = monitor;
        this.wordProvider = wordProvider;
    }

    @Override
    public void setWordProvider(IWordProvider wordProvider) {
        this.wordProvider = wordProvider;
    }
    
    //for easier testing
    public void setPatterns(List<SearchPattern> patterns){
    	this.patterns = patterns;
    }

    private void pushEvent(int sentences, long timestamp) {
        Event event = new Event();
        event.setType(EventType.SENTENCES_MATCHED);
        event.setAmount(sentences);
        event.setTimestamp(timestamp);
        monitor.pushEvent(event);
    }

    @Override
    public List<String> match(List<String> sentences, String url) {
        long start;

        List<String> result = new LinkedList<>();
        for (SearchPattern pattern : patterns) {
            start = System.currentTimeMillis();

            IMatcher matcher = MatcherFactory.getMatcher(MatcherType.REGEX, pattern, wordProvider);

            List<String> matched = matcher.match(sentences);
            result.addAll(matched);

            onMatchCompleted(pattern, matched, url);

            pushEvent(sentences.size(), System.currentTimeMillis() - start);
        }

        return result;
    }

    @Override
    public JPanel getView() {
        PatternController controller = new PatternController(patterns);
        controller.init();
        return controller.getView();
    }
}
