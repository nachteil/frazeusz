package pl.edu.agh.ki.to2.plotter.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-09.
 */
public class Occurrences {
    private Map<String, List<String>> urlSentenceMap;

    public Occurrences(String url, List<String> matches) {
        urlSentenceMap = new HashMap<String, List<String>>();
        urlSentenceMap.put(url, matches);
    }

    public void add(String url, List<String> matches) {
        if(!urlSentenceMap.containsKey(url)){
            urlSentenceMap.put(url, matches);
        }else if(urlSentenceMap.containsKey(url)){
            List<String> actualMatches = urlSentenceMap.get(url);
            actualMatches.addAll(matches);
            urlSentenceMap.put(url, actualMatches);
        }

    }

    public String toString() {
        String toReturn = "";
        for (String url : urlSentenceMap.keySet()) {
            String urlSentenceMapString = url + ":\t";


            for (String s : urlSentenceMap.get(url)) {
                urlSentenceMapString += s + "\t";
            }
            toReturn += urlSentenceMapString + "\n";
        }
        return toReturn;
    }

    public Map<String, List<String>> getUrlSentenceMap() {
        return urlSentenceMap;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Occurrences that = (Occurrences) o;

        if (!urlSentenceMap.equals(that.urlSentenceMap)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return urlSentenceMap.hashCode();
    }

}
