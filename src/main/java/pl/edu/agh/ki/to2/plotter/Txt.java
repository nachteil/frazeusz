package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.IFile;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Txt implements IFile {

    @Override
    public void save(Map data, String path) {
        String name = path + ".txt";
        Map<SearchPattern, Occurrences> searches = data;
        List<String> lines = new ArrayList<String>();


        Path file = Paths.get(name);
        SearchPattern key;
        Map<String, List<String>> map;
        String url;
        List<String> list;
        for (Map.Entry<SearchPattern, Occurrences> entry : searches.entrySet()) {
            key = entry.getKey();
            map = entry.getValue().getUrlSentenceMap();
            for (Map.Entry<String, List<String>> lowerEntry : map.entrySet()) {
                url = lowerEntry.getKey();
                list = lowerEntry.getValue();
                for (String value : list) {
                    lines.add(key.getPattern() + "        " + url + "        " + value + "\n");
                }
            }
        }
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}