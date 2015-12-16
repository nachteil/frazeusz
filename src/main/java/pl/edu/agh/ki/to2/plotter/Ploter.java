package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.IMatchListener;
import pl.edu.agh.ki.to2.patternmatcher.models.ISearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-09.
 */
public class Ploter implements IMatchListener {
    private Map<ISearchPattern, Occurrences> patternOccurrencesHashMap = new HashMap<>();
    ViewFrame viewFrame;

    @Override
    public void addMatches(ISearchPattern pattern, List<String> sentences, String url) {

        if(!patternOccurrencesHashMap.containsKey(pattern)){
            patternOccurrencesHashMap.put(pattern, new Occurrences(url, sentences));
        }else if(patternOccurrencesHashMap.containsKey(pattern)){
            Occurrences occurrence = patternOccurrencesHashMap.get(pattern);
            occurrence.add(url, sentences);
        }
    }

    private void createAndShowGui(){


    }
    private void refreshGui(){

    }

    public Map<ISearchPattern, Occurrences> getPatternOccurrencesHashMap() {
        return patternOccurrencesHashMap;
    }

    public void setViewFrame(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

}
