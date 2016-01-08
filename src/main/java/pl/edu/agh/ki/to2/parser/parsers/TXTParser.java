package pl.edu.agh.ki.to2.parser.parsers;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

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

//TODO test me

public class TXTParser implements IFileParser {

    public TXTParser(){}

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        // Getting content
        String content = parserFile.getContent();

        // Extracting links
        LinkExtractor linkExtractor = LinkExtractor.builder().build();
        Iterable<LinkSpan> links = linkExtractor.extractLinks(content);

        for (LinkSpan link : links) {
            try {
                urls.add(new URL(content.substring(link.getBeginIndex(), link.getEndIndex())));
                // System.out.println("TXT FOUND URL: " + content.substring(link.getBeginIndex(), link.getEndIndex()));
            } catch (MalformedURLException e) {

            }
        }

        return urls;
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {

        List<String> sentences = new ArrayList<>();

        // Extracting sentences
        // TODO smarter way to extract sentences
        for (String sentence : parserFile.getContent().split("\\.")) {
            if(!(sentence = sentence.trim()).isEmpty()) {
                sentences.add(sentence);
                // System.out.println("TXT FOUND SENTENCE: " + sentence);
            }
        }
        return sentences;
    }
}