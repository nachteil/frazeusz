package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mistrz on 2015-12-10.
 */
public class Main {
    public static void main(String [] args){
        Map<SearchPattern, Occurrences> hashMapToCompare = new HashMap<SearchPattern, Occurrences>();
        SearchPattern searchPattern = new SearchPattern("kot", true, true, true, true);
        SearchPattern searchPattern2 = new SearchPattern("pies", true, true, true, true);
        String url = "http://www.elo.pl/";
        String url2 = "http://www.czesc.pl/";
        List<String> sentencesList = new ArrayList<String>();
        List<String> sentencesList2 = new ArrayList<String>();
        sentencesList.add("slon");
        sentencesList.add("pies");
        sentencesList.add("żyrafa");
        sentencesList2.add("kot");
        sentencesList2.add("mysz");
        Occurrences occurrences = new Occurrences(url, sentencesList);
        Occurrences occurrences2 = new Occurrences(url2, sentencesList2);



        Occurrences occurrencesFull = new Occurrences(url, sentencesList);
        occurrencesFull.add(url2, sentencesList2);
        hashMapToCompare.put(searchPattern, occurrencesFull);

        Ploter ploter = new Ploter();
        ViewFrame viewFrame = new ViewFrame();
        ploter.setViewFrame(viewFrame);
        viewFrame.setPloter(ploter);


        ploter.addMatches(searchPattern, sentencesList, url);
        ploter.addMatches(searchPattern2, sentencesList2, url2);


    }
}
