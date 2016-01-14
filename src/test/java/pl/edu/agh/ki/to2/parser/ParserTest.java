package pl.edu.agh.ki.to2.parser;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;

import java.util.concurrent.BlockingQueue;


/**
 * Created by lis on 14.01.16.
 * @author lis
 */
public class ParserTest {

    @Mock
    IPatternMatcher iPatternMatcher;
    @Mock
    IPutter iPutter;
    @Mock
    BlockingQueue<ParserFile> fileQueue;
    Parser parser;
    int threads;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        threads = 10;
    }

    @Test
    public void testParserWorking() throws InterruptedException {
        parser = new Parser(fileQueue,iPutter,iPatternMatcher,threads);
        assertTrue(parser.isWorking());
        parser.close();
        assertFalse(parser.isWorking());
    }

}
