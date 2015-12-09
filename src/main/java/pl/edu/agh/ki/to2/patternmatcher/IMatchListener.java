package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.patternmatcher.models.ISearchPattern;

import java.util.List;

public interface IMatchListener {
    void addMatches(ISearchPattern pattern, List<String> sentences, String url);
}
