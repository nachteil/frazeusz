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
    private int maxStreamSize;

    public TaskQueue(BlockingQueue<ParserFile> fileQueue, int maxDepth,
                     Counter counter, int maxStreamSize) {
        this.tasks = new LinkedBlockingQueue<>();
        this.fileQueue = fileQueue;
        this.maxDepth = maxDepth;
        this.counter = counter;
        this.maxStreamSize = maxStreamSize;
    }

    public DownloadTask get() throws InterruptedException {
        return this.tasks.take();
    }

    @Override
    public void put(URL url, int depth) {
        System.out.println(url.toString()+"asjdsahdssadjksakhdjksakjhdksajdjhsajhd2222222222");
        if (depth == maxDepth)
            return;
        try {
            this.tasks.put(
                    makeDownloadTask(counter, depth, fileQueue, maxStreamSize, url));
            System.out.println(url.toString()+"asjdsahdssadjksakhdjksakjhdksajdjhsajhd");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private DownloadTask makeDownloadTask(Counter counter, int depth,
                                          BlockingQueue<ParserFile> fileQueue,
                                          int maxStreamSize, URL url) {
        return new DownloadTask(counter, depth, fileQueue, maxStreamSize, url);
    }
}
