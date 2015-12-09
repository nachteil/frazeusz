package pl.edu.agh.ki.to2.crawler.model;

import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Crawler {

    TaskQueue taskQueue;
    BlockingQueue<ParserFile> fileQueue;
    private HashSet downloadedURLS;
    ExecutorService executor;
    private Counter counter;
    private int maxSites;

    public Crawler(int workersPool, int maxSites, int maxDepth) {
        this.fileQueue = new LinkedBlockingQueue<>();
        this.counter = new Counter();
        this.taskQueue = new TaskQueue(fileQueue, maxDepth, counter);
        this.executor = Executors.newFixedThreadPool(workersPool);
        this.maxSites = maxSites;
        this.downloadedURLS = new HashSet();
        new Parser(fileQueue, taskQueue, new PatternMatcher(), maxDepth).start();
    }

    public void startCrawling() throws InterruptedException {
        DownloadTask task;
        URL url;
        while(notFinished()) {
            //TODO sending messages with statistics to Monitor
            task = taskQueue.get();
            url = task.getURL();
            if(isDownloaded(url))
                continue;
            executor.execute(task);
            markDownloaded(url);
        }
    }

    synchronized boolean notFinished() {
        return (counter.getCounter() < maxSites);
    }

    private synchronized void markDownloaded(URL url){
        downloadedURLS.add(url);
    }

    private synchronized boolean isDownloaded(URL url){
        return downloadedURLS.contains(url);
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    public BlockingQueue<ParserFile> getFileQueue() {
        return fileQueue;
    }
}