package pl.edu.agh.ki.to2.parser.parsers;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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

public class DOCXParser implements IFileParser {
    public DOCXParser() {
    }

    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        try {

            // Getting content
            // TODO stream from content String ( need charset for that ) ?
            XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(parserFile.getUrl().openStream()));
            String str = extractor.getText();

            // Extracting links
            LinkExtractor linkExtractor = LinkExtractor.builder().build();
            Iterable<LinkSpan> links = linkExtractor.extractLinks(str);

            //Adding urls
            for (LinkSpan link : links) {
                try {
                    urls.add(new URL(str.substring(link.getBeginIndex(), link.getEndIndex())));
                    // System.out.println("DOCX FOUND URL: " + str.substring(link.getBeginIndex(), link.getEndIndex()));
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
            XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(parserFile.getUrl().openStream()));
            String str = extractor.getText();

            // Extracting sentences
            // TODO smarter way to extract sentences
            for (String sentence : str.split("\\.")) {
                if(!(sentence = sentence.trim()).isEmpty()) {
                    sentences.add(sentence);
                    // System.out.println("DOCX FOUND SENTENCE: " + sentence);
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }



        return sentences;
    }
}