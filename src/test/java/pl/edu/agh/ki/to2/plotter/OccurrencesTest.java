package pl.edu.agh.ki.to2.plotter;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-10.
 */
public class OccurrencesTest{

    @Test
    public void shouldConstruct(){
        //prepare
        String url = "http://www.wp.pl/";
        List<String> matchesList = new ArrayList<String>();
        matchesList.add("slon");
        matchesList.add("pies");
        Map<String, List<String>> fullList = new HashMap<String, List<String>>();
        fullList.put(url, matchesList);

        Occurrences occurrences = new Occurrences(url, matchesList);

        Assert.assertEquals(fullList, occurrences.getUrlSentenceMap());
    }

    @Test
    public void shouldAdd(){
        //prepare 1 url
        String url = "http://www.wp.pl/";
        List<String> matchesList = new ArrayList<String>();
        matchesList.add("slon");
        matchesList.add("pies");
        Map<String, List<String>> fullList = new HashMap<String, List<String>>();
        fullList.put(url, matchesList);
        //prepare 2 url
        String url2 = "http://www.onet.pl/";
        List<String> matchesList2 = new ArrayList<String>();
        matchesList2.add("kot");
        matchesList2.add("mysz");
        fullList.put(url2, matchesList2);

        //tested object
        Occurrences occurrences = new Occurrences(url, matchesList);
        occurrences.add(url2, matchesList2);

        Assert.assertEquals(fullList, occurrences.getUrlSentenceMap());
    }

    @Test
    public void shouldAddWhenUrlExists(){
        //prepare url and first part of list
        String url = "http://www.elo.pl/";
        List<String> matchesList = new ArrayList<String>();
        matchesList.add("slon");
        matchesList.add("pies");
        //prepare second part of list
        List<String> matchesList2 = new ArrayList<String>();
        matchesList2.add("kot");
        matchesList2.add("mysz");
        //tested object
        Occurrences occurrences = new Occurrences(url, matchesList);
        occurrences.add(url, matchesList2);

        //object to compare
        List<String> matchesListToCompare = new ArrayList<String>();
        matchesListToCompare.add("slon");
        matchesListToCompare.add("pies");
        matchesListToCompare.add("kot");
        matchesListToCompare.add("mysz");

        Map<String, List<String>> fullList = new HashMap<String, List<String>>();
        fullList.put("http://www.elo.pl/", matchesListToCompare);

        Assert.assertEquals(fullList, occurrences.getUrlSentenceMap());
    }

}