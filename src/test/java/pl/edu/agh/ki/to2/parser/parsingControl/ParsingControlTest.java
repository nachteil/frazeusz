package pl.edu.agh.ki.to2.parser.parsingControl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;
import pl.edu.agh.ki.to2.parser.parsers.FileParserFactory;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by lis on 14.01.16.
 * @author lis
 */
public class ParsingControlTest {

    @Mock
    IPatternMatcher iPatternMatcher;
    @Mock
    IPutter iPutter;
    @Mock
    BlockingQueue<ParserFile> fileQueue;
    FileParserFactory factory;
    ParserThread parserThread;
    Thread thread;
    ParserFile parserFile;
    ParserFile file;
    URL url;
    List<String> sentences;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        url = new URL("http://www.onet.pl");
        parserFile = new ParserFile("Lorem ipsum dolor sit amet enim. Etiam ullamcorper.","text/plain",url,0);
        sentences = new ArrayList<>();
        sentences.add("Lorem ipsum dolor sit amet enim");
        sentences.add("Etiam ullamcorper");
        when(fileQueue.poll(500, TimeUnit.MILLISECONDS)).thenReturn(parserFile);
        factory = new FileParserFactory();
        parserThread = new ParserThread(fileQueue,iPutter,iPatternMatcher,factory);
        thread = new Thread(parserThread);
    }

    @Test(expected=UnsupportedFileException.class)
    public void testUnsupportedFileException() throws Exception {
        file = new ParserFile("","some_unsupported_extension",url,0);
    }

    @Test
    public void testIsFileExtensionSupported(){
        assertTrue(ParserFile.isFileExtensionSupported("text/html"));
        assertFalse(ParserFile.isFileExtensionSupported("some_unsupported_extension"));
    }

    @Test
    public void testInProgress(){
        thread.start();
        assertTrue(parserThread.inProgress());
        parserThread.isWorking = false;
        assertFalse(parserThread.inProgress());
    }



}
