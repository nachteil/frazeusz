package main.java.pl.edu.agh.ki.to2.patternmatcher;

import main.java.pl.edu.agh.ki.to2.patternmatcher.ui.models.SearchPattern;

import java.util.List;

public interface IMatchProvider {
    void onMatchCompleted(SearchPattern pattern, List<String> sentences);
    void addListener(IMatchListener listener);
}
