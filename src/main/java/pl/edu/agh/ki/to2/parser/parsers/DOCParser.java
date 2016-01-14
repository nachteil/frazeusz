package pl.edu.agh.ki.to2.parser.parsers;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

public class DOCParser implements IFileParser {
    public DOCParser() {
    }

    public Set<URL> getUrls(ParserFile parserFile) {
        try {
            // Getting content
            WordExtractor extractor = new WordExtractor(new HWPFDocument(parserFile.getUrl().openStream()));
            return Extractor.extractUrls(extractor.getText());
        } catch (IOException e) {
            // e.printStackTrace();
        }
        return new HashSet<>();
    }

    public List<String> getSentences(ParserFile parserFile) {
        try {
            // Getting content
            WordExtractor extractor = new WordExtractor(new HWPFDocument(parserFile.getUrl().openStream()));
            return Extractor.extractSentences(extractor.getText());
        } catch (IOException e) {
            // e.printStackTrace();
        }
        return new ArrayList<>();
    }
}