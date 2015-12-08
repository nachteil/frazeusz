package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

import java.util.List;

public interface IMatchProvider {
    void onMatchCompleted(SearchPattern pattern, List<String> sentences);
    void addListener(IMatchListener listener);
}
