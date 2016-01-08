package pl.edu.agh.ki.to2.parser.parsers;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
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

public class ODTParser implements IFileParser {

    public ODTParser(){}

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        try {

            // Getting content
            // TODO stream from content String ( need charset for that ) ?
            OdfTextDocument odt = (OdfTextDocument) OdfDocument.loadDocument(parserFile.getUrl().openStream());
            String content = odt.getContentRoot().toString();

            // Extracting links
            LinkExtractor linkExtractor = LinkExtractor.builder().build();
            Iterable<LinkSpan> links = linkExtractor.extractLinks(content);

            for (LinkSpan link : links) {
                try {
                    urls.add(new URL(content.substring(link.getBeginIndex(), link.getEndIndex())));
                    System.out.println("ODT FOUND URL: " + content.substring(link.getBeginIndex(), link.getEndIndex()));
                } catch (MalformedURLException e) {

                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }


        return urls;
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {

        List<String> sentences = new ArrayList<>();

        try {

            // Getting content
            // TODO stream from content String ( need charset for that ) ?
            OdfTextDocument odt = (OdfTextDocument) OdfDocument.loadDocument(parserFile.getUrl().openStream());
            String content = odt.getContentRoot().getTextContent();

            // Extracting sentences
            // TODO smarter way to extract sentences
            for (String sentence : content.split("\\.")) {
                if(!(sentence = sentence.trim()).isEmpty()) {
                    sentences.add(sentence);
                    System.out.println("ODT FOUND SENTENCE: " + sentence);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sentences;
    }
}