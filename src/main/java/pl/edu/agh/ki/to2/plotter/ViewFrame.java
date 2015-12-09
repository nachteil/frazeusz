package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.IMatchListener;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-09.
 */
public class ViewFrame extends JFrame implements IMatchListener{
    private Map<SearchPattern, Occurrences> searches = new HashMap<SearchPattern, Occurrences>();
    @Override
    public void addMatches(SearchPattern pattern, List<String> sentences, String url) {

        if(!searches.containsKey(pattern)){
            searches.put(pattern, new Occurrences(url, sentences));
        }else if(searches.containsKey(pattern)){
            Occurrences occurrence = searches.get(pattern);
            occurrence.add(url, sentences);
        }
    }
}
