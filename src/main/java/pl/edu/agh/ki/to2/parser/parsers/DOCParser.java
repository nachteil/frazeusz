package pl.edu.agh.ki.to2.parser.parsers;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam on 29.11.2015.
 */
public class DOCParser implements IFileParser {
    public DOCParser() {
    }

    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        InputStream in = null;
        try {
            in = parserFile.getUrl().openStream();
            HWPFDocument wordDoc = new HWPFDocument(in);
            WordExtractor extractor = new WordExtractor(wordDoc);
            String str = extractor.getText();
            LinkExtractor linkExtractor = LinkExtractor.builder().build();
            Iterable<LinkSpan> links = linkExtractor.extractLinks(str);

            for (LinkSpan link : links) {
                System.out.println("FOUND URL: " + str.substring(link.getBeginIndex(), link.getEndIndex()));
                try {
                    urls.add(new URL(str.substring(link.getBeginIndex(), link.getEndIndex())));
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Caught malformed url!");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;

    }

    public List<String> getSentences(ParserFile parserFile) {

        List<String> sentences = new ArrayList<String>();

        InputStream in = null;
        try {
            in = parserFile.getUrl().openStream();
            HWPFDocument wordDoc = new HWPFDocument(in);
            WordExtractor extractor = new WordExtractor(wordDoc);
            String str = extractor.getText();
            //System.out.println("DOC FILE TEXT: " + str);
            for (String sentence : str.split("\\.")) {
                if(!sentence.trim().isEmpty()) {
                    sentences.add(sentence);
                    //System.out.println("SENTENCE TEXT: " + sentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        return sentences;
    }
}
