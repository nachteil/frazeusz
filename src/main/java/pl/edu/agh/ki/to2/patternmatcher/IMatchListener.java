package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

import java.util.List;

public interface IMatchListener {
    void addMatches(SearchPattern pattern, List<String> sentences, String url);
}
