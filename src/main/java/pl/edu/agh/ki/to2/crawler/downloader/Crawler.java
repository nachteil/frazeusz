package pl.edu.agh.ki.to2.crawler.downloader;

import pl.edu.agh.ki.to2.monitor.Monitor;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

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
    private int maxStreamSize = 10485760; //10 MB

    public Crawler(int workersPool, int maxSites, int maxDepth) {
        this.fileQueue = new LinkedBlockingQueue<>();
        this.counter = new Counter(Monitor.getInstance().getMonitorPubSub(), 10);
        this.taskQueue = new TaskQueue(fileQueue, maxDepth, counter, maxStreamSize);
        this.executor = Executors.newFixedThreadPool(workersPool);
        this.maxSites = maxSites;
        this.downloadedURLS = new HashSet();
    }

    public void startCrawling() throws InterruptedException {
        DownloadTask task;
        URL url;
        while (notFinished()) {
            task = taskQueue.get();
            url = task.getURL();
            if (isDownloaded(url))
                continue;
            executor.execute(task);
            markDownloaded(url);
        }
        counter.sendLastEvents();
    }

    synchronized boolean notFinished() {
        return (counter.getPagesCrawled() < maxSites);
    }

    private synchronized void markDownloaded(URL url) {
        downloadedURLS.add(url);
    }

    private synchronized boolean isDownloaded(URL url) {
        return downloadedURLS.contains(url);
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    public BlockingQueue<ParserFile> getFileQueue() {
        return fileQueue;
    }
}