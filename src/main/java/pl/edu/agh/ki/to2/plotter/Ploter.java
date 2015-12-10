package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.IMatchListener;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-09.
 */
public class Ploter implements IMatchListener {
    private Map<SearchPattern, Occurrences> patternOccurrencesHashMap = new HashMap<SearchPattern, Occurrences>();
    Ploter ploter = this;
    ViewFrame viewFrame;

    @Override
    public void addMatches(SearchPattern pattern, List<String> sentences, String url) {

        if(!patternOccurrencesHashMap.containsKey(pattern)){
            patternOccurrencesHashMap.put(pattern, new Occurrences(url, sentences));
        }else if(patternOccurrencesHashMap.containsKey(pattern)){
            Occurrences occurrence = patternOccurrencesHashMap.get(pattern);
            occurrence.add(url, sentences);
        }
    }
    private void createAndShowGui(){

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                viewFrame = new ViewFrame(ploter);
            }
        });
        refreshGui();
    }

    private void refreshGui(){

    }

    public Map<SearchPattern, Occurrences> getPatternOccurrencesHashMap() {
        return patternOccurrencesHashMap;
    }

}
