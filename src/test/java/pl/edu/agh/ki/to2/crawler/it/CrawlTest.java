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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Rav on 2015-12-16.
 */
public class CrawlTest {

    Crawler crawler;
    DownloadTask downloadTaskMock;
    TaskQueue taskQueueMock;
    BlockingQueue <DownloadTask> queue = new LinkedBlockingQueue<>();
    int counter;
    int tasksNum;
    int workersPool;
    int maxPages;
    int maxDepth;

    @Before
    public void setUpBefore() throws InterruptedException {
        counter = 0;
        tasksNum = 10;
        workersPool = 1;
        maxPages = tasksNum;
        maxDepth = 10;
        List<String> urls = Arrays.asList("http://www.onet.pl");
        crawler = new TaskQueueTakingTestCrawler(workersPool, maxPages, maxDepth, urls);
        downloadTaskMock = mock(DownloadTask.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                synchronized (this) {
                    System.out.println("IN thread");
                    counter++;
                }
                return null;
            }
        }).when(downloadTaskMock).run();

        for(int i = 0; i<tasksNum; i++)
            queue.put(downloadTaskMock);

        taskQueueMock = crawler.getTaskQueue();
        when(taskQueueMock.get()).thenReturn(queue.take());
    }

    @Test
    public void queueTakingTest() throws InterruptedException {
        System.out.println("Starting test");
//TODO fix test
//        crawler.startCrawling();
//        assert(counter == tasksNum);
    }

    private class TaskQueueTakingTestCrawler extends Crawler{

        public TaskQueueTakingTestCrawler(int workersPool, int maxSites, int maxDepth, List<String> urls) {
            super(workersPool, maxSites, maxDepth, urls);
        }

        private TaskQueue makeTaskQueue(BlockingQueue<ParserFile> fileQueue, int maxDepth,
                                        Counter counter, int maxStreamSize){
            return mock(TaskQueue.class);
        }

        public TaskQueue getTaskQueue(){
            return mock(TaskQueue.class);
        }
    }
}
