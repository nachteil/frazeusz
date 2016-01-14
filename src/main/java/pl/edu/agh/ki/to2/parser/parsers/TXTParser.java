package pl.edu.agh.ki.to2.parser.parsers;

import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

public class TXTParser implements IFileParser {

    public TXTParser(){}

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {
        return Extractor.extractUrls(parserFile.getContent());
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {
        return Extractor.extractSentences(parserFile.getContent());
    }
}