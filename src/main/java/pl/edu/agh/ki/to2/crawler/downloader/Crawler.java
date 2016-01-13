package pl.edu.agh.ki.to2.crawler.downloader;

import pl.edu.agh.ki.to2.monitor.Monitor;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Crawler {

    protected TaskQueue taskQueue;
    BlockingQueue<ParserFile> fileQueue;
    private HashSet<String> downloadedURLS;
    ExecutorService executor;
    private Counter counter;
    private int maxSites;
    private int maxStreamSize = 10485760; //10 MB

    public Crawler(int workersPool, int maxSites, int maxDepth, List<String> urls) {
        this.fileQueue = new LinkedBlockingQueue<>();
        this.counter = new Counter(Monitor.getInstance().getMonitorPubSub(), 10);
        this.taskQueue = makeTaskQueue(fileQueue, maxDepth, counter, maxStreamSize);
        for (String path : urls) {
            try {
                taskQueue.put(new URL(path), 0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        this.executor = Executors.newFixedThreadPool(workersPool);
        this.maxSites = maxSites;
        this.downloadedURLS = new HashSet<>();
    }

    public void startCrawling() throws InterruptedException {
        DownloadTask task;
        URL url;
        while (notFinished()) {
            if(counter.getSitesUnderExecution()<200) {
                task = taskQueue.get();
                url = task.getURL();
                if (isDownloaded(url))
                    continue;
                executor.execute(task);
                markDownloaded(url);
                counter.increasePagesCounter();
                counter.increaseSitesUnderExecution();
            }
        }
        counter.sendLastEvents();
    }

    synchronized boolean notFinished() {
        return (counter.getPagesCrawled() < maxSites);
    }

    private synchronized void markDownloaded(URL url) {
        downloadedURLS.add(url.toString());
    }

    private synchronized boolean isDownloaded(URL url) {
        return downloadedURLS.contains(url.toString());
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    public BlockingQueue<ParserFile> getFileQueue() {
        return fileQueue;
    }

    private TaskQueue makeTaskQueue(BlockingQueue<ParserFile> fileQueue, int maxDepth,
                                    Counter counter, int maxStreamSize) {
        return new TaskQueue(fileQueue, maxDepth, counter, maxStreamSize);
    }

}