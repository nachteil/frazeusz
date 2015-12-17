package pl.edu.agh.ki.to2.crawler.it;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.edu.agh.ki.to2.crawler.downloader.Counter;
import pl.edu.agh.ki.to2.crawler.downloader.Crawler;
import pl.edu.agh.ki.to2.crawler.downloader.DownloadTask;
import pl.edu.agh.ki.to2.crawler.downloader.TaskQueue;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Rav on 2015-12-16.
 */
public class CrawlTest {

    static Crawler crawler;
    static DownloadTask downloadTaskMock;
    static TaskQueue taskQueueMock;
    static BlockingQueue queue  = new LinkedBlockingQueue<>();
    static int counter;
    static int tasksNum;
    static int workersPool;
    static int maxPages;
    static int maxDepth;

    @Before
    public static void setUpBeforeClass() throws InterruptedException {
        counter = 0;
        tasksNum = 1000;
        workersPool = 100;
        maxPages = 2000;
        maxDepth = 10;
        crawler = new TaskQueueTakingTestCrawler(workersPool, maxPages, maxDepth);
        downloadTaskMock = mock(DownloadTask.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                synchronized (this) {
                    counter++;
                }
                return null;
            }
        }).when(downloadTaskMock);

        for(int i = 0; i<workersPool; i++)
            queue.put(downloadTaskMock);

        when(taskQueueMock.get()).thenReturn((DownloadTask) queue.take());
    }

    @Test
    public void queueTakingTest() throws InterruptedException {
        crawler.startCrawling();
        assert(counter == tasksNum);
    }

    private static class TaskQueueTakingTestCrawler extends Crawler{

        public TaskQueueTakingTestCrawler(int workersPool, int maxSites, int maxDepth) {
            super(workersPool, maxSites, maxDepth);
        }

        private TaskQueue makeTaskQueue(BlockingQueue<ParserFile> fileQueue, int maxDepth,
                                        Counter counter, int maxStreamSize){
            return mock(TaskQueue.class);
        }
    }

}
