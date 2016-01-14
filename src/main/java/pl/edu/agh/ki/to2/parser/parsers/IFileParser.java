package pl.edu.agh.ki.to2.parser.parsers;

import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

// TODO stream from getContent() not working

public interface IFileParser {
    Set<URL> getUrls(ParserFile parserFile);
    List<String> getSentences(ParserFile parserFile);
}