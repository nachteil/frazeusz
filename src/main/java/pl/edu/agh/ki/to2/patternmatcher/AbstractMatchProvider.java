package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.patternmatcher.models.ISearchPattern;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMatchProvider implements IMatchProvider {

    protected List<IMatchListener> listeners;

    public AbstractMatchProvider() {
        listeners = new LinkedList<>();
    }

    @Override
    public void onMatchCompleted(ISearchPattern pattern, List<String> sentences, String url) {
        for (IMatchListener listener : listeners)
            listener.addMatches(pattern, sentences, url);
    }

    @Override
    public void addListener(IMatchListener listener) {
        listeners.add(listener);
    }
}
