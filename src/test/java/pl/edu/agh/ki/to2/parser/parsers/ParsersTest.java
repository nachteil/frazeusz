package pl.edu.agh.ki.to2.parser.parsers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lis on 14.01.16.
 * @author lis
 */

// TODO test PDF, DOC, DOCX, ODT

public class ParsersTest {

    FileParserFactory factory;
    IFileParser parser;
    ParserFile txtFile;
    ParserFile htmlFile;
    List<String> sentences;
    Set<URL> urls;
    URL url;

    @Before
    public void setUp() throws Exception {
        url = new URL("http://www.onet.pl");
        urls = new HashSet<>();
        urls.add(url);
        sentences = new ArrayList<>();
        sentences.add("Lorem ipsum dolor sit amet enim.");
        sentences.add("Etiam ullamcorper.");
        factory = new FileParserFactory();
        txtFile = new ParserFile("Lorem ipsum dolor sit amet enim. Etiam ullamcorper.","text/plain",url,1);
        htmlFile = new ParserFile("<html><head></head><body><p>Lorem ipsum dolor sit amet enim." +
                "</p><a href=\"http://www.onet.pl\">Etiam ullamcorper.</a></body></html>","text/html",url,1);
    }

    @Test
    public void testFileParserFactory(){
        assertThat(factory.getFileParser(txtFile),instanceOf(TXTParser.class));
    }

    @Test
    public void testExtractor(){
        String text = "Lorem ipsum dolor sit amet enim. Etiam ullamcorper.";
        assertEquals(sentences,Extractor.extractSentences(text));
        text = "zazazazazz http://www.onet.pl asdasagsdfqwdqsdqf.wdwd";
        assertEquals(urls,Extractor.extractUrls(text));
    }

    @Test
    public void testHTMLParser(){
        parser = factory.getFileParser(htmlFile);
        assertEquals(sentences,parser.getSentences(htmlFile));
        assertEquals(urls, parser.getUrls(htmlFile));
    }
}
