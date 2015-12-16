package pl.edu.agh.ki.to2.monitor.model;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PerformanceDataModel {

    private final List<Event> bandwidthEvents;
    private final List<Event> crawlEvents;
    private final List<Event> matchEvents;

    @Inject
    public PerformanceDataModel() {
        this.bandwidthEvents = new ArrayList<>();
        this.crawlEvents = new ArrayList<>();
        this.matchEvents = new ArrayList<>();
    }

    public void update(List<Event> events) {

        List<Event> newBandwidthEvents = events.stream().filter(isBandwidth).collect(Collectors.toList());
        List<Event> newCrawlEvents = events.stream().filter(isCrawl).collect(Collectors.toList());
        List<Event> newMatchEvents = events.stream().filter(isMatch).collect(Collectors.toList());

        synchronized (this.bandwidthEvents) {
            bandwidthEvents.addAll(newBandwidthEvents);
            bandwidthEvents.sort((e1,e2) -> Long.compare(e1.getTimestamp(), e2.getTimestamp()));
        }
        synchronized (this.crawlEvents) {
            crawlEvents.addAll(newCrawlEvents);
        }
        synchronized (this.matchEvents) {
            matchEvents.addAll(newMatchEvents);
        }
    }

    public XYDataset getBandwidthDataSet(long startingPointTimestamp) {
        long now = System.currentTimeMillis();
        List<Event> eventsSinceStart = null;
        synchronized (bandwidthEvents) {
            eventsSinceStart = bandwidthEvents.stream()
                    .filter(event -> event.getTimestamp() >= startingPointTimestamp)
                    .collect(Collectors.toList());
        }
        int secondsRange = (int)((now - startingPointTimestamp) / 1000);
        Map<Integer, List<Event>> buckets = eventsSinceStart.stream()
                .collect(Collectors.groupingBy(event -> (int)((event.getTimestamp() - startingPointTimestamp)/1000)));
        double [] xValues = IntStream.range(0, secondsRange).mapToDouble(i -> i).toArray();
        List<Double> yValues = new ArrayList<>(secondsRange);
        for(int i = 0; i < secondsRange; ++i) {
            if(buckets.get(i) != null) {
                double sum = buckets.get(i).stream()
                        .mapToLong(Event::getAmount)
                        .mapToInt(amount -> (int)amount)
                        .sum();
                yValues.add(sum);
            } else {
                yValues.add(0.0);
            }
        }
        double [][] result = {xValues, yValues.stream().mapToDouble(d -> d).toArray()};
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("series1", result);
        return dataset;
    }

    public XYDataset getSentenceMatchDataSet(long startingPointTimestamp) {
        return null;
    }

    public XYDataset getCrawledPagesDataSet(long startingPointTimestamp) {
        return null;
    }

    private final Predicate<Event> isBandwidth = e -> e.getType() == EventType.KILOBYTES_DOWNLOADED;
    private final Predicate<Event> isCrawl = e -> e.getType() == EventType.PAGES_CRAWLED;
    private final Predicate<Event> isMatch = e -> e.getType() == EventType.SENTENCES_MATCHED;
}
