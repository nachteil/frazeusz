package pl.edu.agh.ki.to2.parser.parsers;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lis on 14.01.16.
 * @author lis
 */

public class Extractor {

    public static List<String> extractSentences(String content){
        List<String> sentences = new ArrayList<>();
        // Extracting sentences
        // TODO is that regex enough ??
        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(content);
        while (reMatcher.find()) {
            String sentence = reMatcher.group();
            //replacing newline characters
            sentence = sentence.replaceAll("\\r\\n|\\r|\\n", " ");
            sentences.add(sentence);
            //System.out.println("FOUND SENTENCE: " + sentence);
        }
        return sentences;
    }

    public static Set<URL> extractUrls(String content){
        Set<URL> urls = new HashSet<>();
        // Extracting links
        LinkExtractor linkExtractor = LinkExtractor.builder().build();
        Iterable<LinkSpan> links = linkExtractor.extractLinks(content);
        for (LinkSpan link : links) {
            try {
                urls.add(new URL(content.substring(link.getBeginIndex(), link.getEndIndex())));
                //System.out.println("FOUND URL: " + content.substring(link.getBeginIndex(),link.getEndIndex()));
            } catch (MalformedURLException e) {
                // e.printStackTrace();
            }
        }
        return urls;
    }

}
