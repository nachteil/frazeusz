package main.java.pl.edu.agh.ki.to2.parser.parsers;

import main.java.pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam on 29.11.2015.
 */
public interface IFileParser {
    public Set<URL> getUrls(ParserFile parserFile);
    public List<String> getSentences(ParserFile parserFile);
}
