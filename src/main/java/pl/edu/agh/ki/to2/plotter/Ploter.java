package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.IMatchListener;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-09.
 */
public class Ploter implements IMatchListener {
    private Map<SearchPattern, Occurrences> patternOccurrencesHashMap = new HashMap<SearchPattern, Occurrences>();
    ViewFrame viewFrame;

    @Override
    public void addMatches(SearchPattern pattern, List<String> sentences, String url) {
        synchronized (patternOccurrencesHashMap) {
            if (!patternOccurrencesHashMap.containsKey(pattern)) {
                patternOccurrencesHashMap.put(pattern, new Occurrences(url, sentences));
            } else if (patternOccurrencesHashMap.containsKey(pattern)) {
                Occurrences occurrence = patternOccurrencesHashMap.get(pattern);
                occurrence.add(url, sentences);
            }
        }
    }

    public Map<SearchPattern, Occurrences> getPatternOccurrencesHashMap() {
        return patternOccurrencesHashMap;
    }

    public void setViewFrame(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

}