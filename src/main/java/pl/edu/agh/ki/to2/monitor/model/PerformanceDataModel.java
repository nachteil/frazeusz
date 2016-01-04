package pl.edu.agh.ki.to2.monitor.model;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.util.SystemTimeProvider;
import pl.edu.agh.ki.to2.monitor.util.TimeProvider;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PerformanceDataModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceDataModel.class);

    private final Map<EventType, EventData> eventTypeDataMap;

    @Inject
    public PerformanceDataModel(TimeProvider timer) {
        this.eventTypeDataMap = new HashMap<>();
        for(EventType eventType : EventType.values()) {
            eventTypeDataMap.put(eventType, new EventData(timer));
        }
    }

    public void update(List<Event> events) {
        for(EventType eventType : EventType.values()) {
            long s = System.currentTimeMillis();
            List<Event> eventsOfCurrentType = events.stream().filter(isOfType(eventType)).collect(Collectors.toList());
            LOGGER.debug("Filtering {} events: {}", events.size(), System.currentTimeMillis() - s);
            s = System.currentTimeMillis();
            eventTypeDataMap.get(eventType).processNewEvent(eventsOfCurrentType);
            LOGGER.debug("Processing: {}", System.currentTimeMillis() - s);
        }
    }

    public XYDataset getDataSet(long startingPointTimestamp, EventType eventType) {
        int numberOfSecondsToProcess = secondForTimestamp(System.currentTimeMillis()) - secondForTimestamp(startingPointTimestamp);
        return getDataSet(numberOfSecondsToProcess, eventType);
    }

    public XYDataset getDataSet(int numberOfSeconds, EventType eventType) {
        LOGGER.debug("Queried for {} dataset for {} seconds", eventType.toString(), numberOfSeconds);
        double [][] eventDataSet = eventTypeDataMap.get(eventType).getDataForPastSeconds(numberOfSeconds);
        DefaultXYDataset resultDataSet = new DefaultXYDataset();
        resultDataSet.addSeries(eventType.toString(), eventDataSet);
        return resultDataSet;
    }

    public XYDataset getBandwidthDataSet(long startingPointTimestamp) {
        return getDataSet(startingPointTimestamp, EventType.KILOBYTES_DOWNLOADED);
    }

    public XYDataset getSentenceMatchDataSet(long startingPointTimestamp) {
        return getDataSet(startingPointTimestamp, EventType.SENTENCES_MATCHED);
    }

    public XYDataset getCrawledPagesDataSet(long startingPointTimestamp) {
        return getDataSet(startingPointTimestamp, EventType.PAGES_CRAWLED);
    }

    private int secondForTimestamp(long timestamp) {
        return (int) (timestamp/1000L);
    }

    private static Predicate<Event> isOfType(EventType type) {
        return e -> e.getType() == type;
    }
}
