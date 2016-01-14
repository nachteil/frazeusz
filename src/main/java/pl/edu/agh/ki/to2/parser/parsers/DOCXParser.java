package pl.edu.agh.ki.to2.parser.parsers;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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

public class DOCXParser implements IFileParser {
    public DOCXParser() {
    }

    public Set<URL> getUrls(ParserFile parserFile) {
        try {
            // Getting content
            XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(parserFile.getUrl().openStream()));
            return Extractor.extractUrls(extractor.getText());
        } catch (IOException e) {
            // e.printStackTrace();
        }
        return new HashSet<>();

    }

    public List<String> getSentences(ParserFile parserFile) {
        try {
            // Getting content
            XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(parserFile.getUrl().openStream()));
            String str = extractor.getText();
            return Extractor.extractSentences(extractor.getText());
        } catch (IOException e) {
            // e.printStackTrace();
        }
        return new ArrayList<>();
    }
}