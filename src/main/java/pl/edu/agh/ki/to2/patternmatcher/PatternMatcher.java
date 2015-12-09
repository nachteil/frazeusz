package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.monitor.MonitorPubSub;
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

    @Override
    public List<String> match(List<String> sentences, String url) {

        List<String> result = new LinkedList<>();
        for (SearchPattern pattern : patterns) {
            IMatcher matcher = MatcherFactory.getMatcher(MatcherType.REGEX, pattern, wordProvider);

            List<String> matched = matcher.match(sentences);
            result.addAll(matched);
            onMatchCompleted(pattern, matched, url);
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
