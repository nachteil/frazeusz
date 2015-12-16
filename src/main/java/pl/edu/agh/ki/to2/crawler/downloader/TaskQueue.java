package pl.edu.agh.ki.to2.crawler.downloader;

import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue implements IPutter {

    private BlockingQueue<DownloadTask> tasks;
    private BlockingQueue<ParserFile> fileQueue;
    private int maxDepth;
    private Counter counter;
    private String tempDir ;

    public TaskQueue(BlockingQueue<ParserFile> fileQueue, int maxDepth,
                     Counter counter, String tempDir) {
        this.tasks = new LinkedBlockingQueue<>();
        this.fileQueue = fileQueue;
        this.maxDepth = maxDepth;
        this.counter = counter;
        this.tempDir = tempDir;
    }

    DownloadTask get() throws InterruptedException {
        return this.tasks.take();
    }

    @Override
    public void put(URL url, int depth) {
        if(depth == maxDepth)
            return;
        try {
            this.tasks.put(new DownloadTask(url, depth, fileQueue, counter, tempDir));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
