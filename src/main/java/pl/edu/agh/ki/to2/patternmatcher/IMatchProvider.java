package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.patternmatcher.models.ISearchPattern;

import java.util.List;

public interface IMatchProvider {
    void onMatchCompleted(ISearchPattern pattern, List<String> sentences);
    void addListener(IMatchListener listener);
}
