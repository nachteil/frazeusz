package pl.edu.agh.ki.to2.monitor.model;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PerformanceDataModel {

    private final List<Event> bandwidthEvents;
    private final List<Event> crawlEvents;
    private final List<Event> matchEvents;

    public PerformanceDataModel() {
        this.bandwidthEvents = new ArrayList<>();
        this.crawlEvents = new ArrayList<>();
        this.matchEvents = new ArrayList<>();
    }

    public void update(List<Event> events) {

        List<Event> newBandwidthEvents = events.stream().filter(isBandwitch).collect(Collectors.toList());
        List<Event> newCrawlEvents = events.stream().filter(isCrawl).collect(Collectors.toList());
        List<Event> newMatchEvents = events.stream().filter(isMatch).collect(Collectors.toList());

        synchronized (this.bandwidthEvents) {
            bandwidthEvents.addAll(newBandwidthEvents);
        }
        synchronized (this.crawlEvents) {
            crawlEvents.addAll(newCrawlEvents);
        }
        synchronized (this.matchEvents) {
            matchEvents.addAll(newMatchEvents);
        }
    }

    public void getBandwidthDataSet() {

    }

    public void getSentenceMatchDataSet() {

    }

    public void getCrawledPagesDataSet() {

    }

    private final Predicate<Event> isBandwitch = e -> e.getType() == EventType.KILOBYTES_DOWNLOADED;
    private final Predicate<Event> isCrawl = e -> e.getType() == EventType.PAGES_CRAWLED;
    private final Predicate<Event> isMatch = e -> e.getType() == EventType.SENTENCES_MATCHED;
}
