package pl.edu.agh.ki.to2.parser.parsers;

import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

public class ODTParser implements IFileParser {

    public ODTParser(){}

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {
        try {
            // Getting content
            OdfTextDocument odt = (OdfTextDocument) OdfDocument.loadDocument(parserFile.getUrl().openStream());
            return Extractor.extractUrls(odt.getContentRoot().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {
        try {
            // Getting content
            OdfTextDocument odt = (OdfTextDocument) OdfDocument.loadDocument(parserFile.getUrl().openStream());
            return Extractor.extractSentences(odt.getContentRoot().getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}