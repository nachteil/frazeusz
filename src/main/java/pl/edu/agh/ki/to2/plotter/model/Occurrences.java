package pl.edu.agh.ki.to2.plotter.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sara on 2015-12-09.
 */
public class Occurrences {
    private Map<String, List<String>> list;

    public Occurrences(String url, List<String> matches) {
        list = new HashMap<String, List<String>>();
        list.put(url, matches);
    }

    public void add(String url, List<String> matches) {
        if(!list.containsKey(url)){
            list.put(url, matches);
        }else if(list.containsKey(url)){
            List<String> actualMatches = list.get(url);
            actualMatches.addAll(matches);
            list.put(url, actualMatches);
        }

    }

    public String toString() {
        String toReturn = "";
        for (String url : list.keySet()) {
            String listString = url + ":\t";


            for (String s : list.get(url)) {
                listString += s + "\t";
            }
            toReturn += listString + "\n";
        }
        return toReturn;
    }

    public Map<String, List<String>> getList() {
        return list;
    }
}
