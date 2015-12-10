package pl.edu.agh.ki.to2.plotter;

import org.junit.Test;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sara on 2015-12-10.
 */


public class TableTest {
    @Test
    public void shouldShow() {
        Map<SearchPattern, Occurrences> hashMapToCompare = new HashMap<SearchPattern, Occurrences>();
        SearchPattern searchPattern = new SearchPattern("kot", true, true, true, true);

        String url = "http://www.wp.pl/";
        List<String> sentencesList = new ArrayList<String>();
        sentencesList.add("slon");
        sentencesList.add("pies");
        Occurrences occurrences = new Occurrences(url, sentencesList);

        hashMapToCompare.put(searchPattern, occurrences);

        Ploter ploter = new Ploter();
        ploter.addMatches(searchPattern, sentencesList, url);
    }
}
