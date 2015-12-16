package pl.edu.agh.ki.to2.crawler.downloader;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;

public class Counter {

    private MonitorPubSub monitorPubSub;
    private int pagesCrawled;
    private int pagesSinceLastEvent;
    private int pagesChunk;
    private int downloadedDataSinceLastEvent;
    private int downloadedDataChunk;    //in KB

    Counter(MonitorPubSub monitorPubSub, int pagesChunk) {
        this.monitorPubSub = monitorPubSub;
        this.pagesCrawled = 0;
        this.pagesChunk = pagesChunk;
        this.pagesSinceLastEvent = 0;
        this.downloadedDataChunk = 0;
        this.downloadedDataSinceLastEvent = 0;
    }

    public synchronized int getPagesCrawled() {
        return pagesCrawled;
    }

    public synchronized void updateDataCounter(int downloadedData) {
        if (downloadedDataSinceLastEvent + kb(downloadedData) < downloadedDataChunk)
            downloadedDataSinceLastEvent += kb(downloadedData);
        else {
            sendEvent(downloadedDataChunk, EventType.KILOBYTES_DOWNLOADED);
            downloadedDataSinceLastEvent += kb(downloadedData) - downloadedDataChunk;
        }
    }

    public synchronized void increasePagesCounter() {
        this.pagesCrawled++;
        if (++pagesSinceLastEvent == pagesChunk) {
            sendEvent(pagesChunk, EventType.PAGES_CRAWLED);
            pagesSinceLastEvent = 0;
        }
    }

    public void sendLastEvents() {
        sendEvent(downloadedDataSinceLastEvent, EventType.KILOBYTES_DOWNLOADED);
        sendEvent(pagesCrawled, EventType.PAGES_CRAWLED);
    }

    private void sendEvent(int amount, EventType eventType) {
        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setAmount(amount);
        event.setType(eventType);
        monitorPubSub.pushEvent(event);
    }

    private int kb(int bytes) {
        return bytes / 1024;
    }

}
