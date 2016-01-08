package pl.edu.agh.ki.to2.parser.parsers;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

// TODO test me

public class DOCParser implements IFileParser {
    public DOCParser() {
    }

    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        try {

            // Getting content
            // TODO stream from content String ( need charset for that ) ?
            WordExtractor extractor = new WordExtractor(new HWPFDocument(parserFile.getUrl().openStream()));
            String content = extractor.getText();

            // Extracting links
            LinkExtractor linkExtractor = LinkExtractor.builder().build();
            Iterable<LinkSpan> links = linkExtractor.extractLinks(content);

            for (LinkSpan link : links) {
                try {
                    urls.add(new URL(content.substring(link.getBeginIndex(), link.getEndIndex())));
                    System.out.println("DOC FOUND URL: " + content.substring(link.getBeginIndex(), link.getEndIndex()));
                } catch (MalformedURLException e) {
                    // e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // e.printStackTrace();
        }

        return urls;

    }

    public List<String> getSentences(ParserFile parserFile) {

        List<String> sentences = new ArrayList<>();

        try {

            // Getting content
            // TODO stream from content String ( need charset for that ) ?
            WordExtractor extractor = new WordExtractor(new HWPFDocument(parserFile.getUrl().openStream()));
            String content = extractor.getText();

            // Extracting sentences
            // TODO smarter way to extract sentences
            for (String sentence : content.split("\\.")) {
                if(!(sentence = sentence.trim()).isEmpty()) {
                    sentences.add(sentence);
                    System.out.println("DOC FOUND SENTENCE: " + sentence);
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }

        return sentences;
    }
}
