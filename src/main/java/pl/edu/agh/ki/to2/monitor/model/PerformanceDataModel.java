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
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Singleton
public class PerformanceDataModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceDataModel.class);

    private final Map<EventType, EventData> eventTypeDataMap;
    private final AtomicInteger queueLength;
    private final DurationEstimator estimator;

    @Inject
    public PerformanceDataModel(TimeProvider timer) {
        this.eventTypeDataMap = new HashMap<>();
        for(EventType eventType : EventType.values()) {
            eventTypeDataMap.put(eventType, new EventData(timer));
        }
        this.queueLength = new AtomicInteger(0);
        this.estimator = new DurationEstimator(this);
    }

    public void update(List<Event> events) {
        for(EventType eventType : EventType.values()) {
            long s = System.currentTimeMillis();
            List<Event> eventsOfCurrentType = events.stream().filter(isOfType(eventType)).collect(Collectors.toList());
            LOGGER.debug("Filtering {} events: {}", events.size(), System.currentTimeMillis() - s);
            if(eventsOfCurrentType.size() > 0) {
                eventTypeDataMap.get(eventType).processNewEvent(eventsOfCurrentType);
            }
        }
    }

    public XYDataset getDataSet(int numberOfSeconds, EventType eventType) {
        double [][] eventDataSet = getData(numberOfSeconds, eventType);
        DefaultXYDataset resultDataSet = new DefaultXYDataset();
        resultDataSet.addSeries(eventType.toString(), eventDataSet);
        return resultDataSet;
    }

    public double [][] getData(int numberOfSeconds, EventType eventType) {
        double [][] eventDataSet = eventTypeDataMap.get(eventType).getDataForPastSeconds(numberOfSeconds);
        LOGGER.debug("Queried for {} dataset for {} seconds, returned {} entries", eventType.toString(), numberOfSeconds, eventDataSet[0].length);
        return eventDataSet;
    }

    public int setQueueLength(int newLength) {
        return queueLength.getAndUpdate(i -> newLength);
    }

    public int getQueueLength() {
        return queueLength.get();
    }

    public int getRemainingSeconds() {
        return estimator.getRemainingSeconds();
    }

    private static Predicate<Event> isOfType(EventType type) {
        return e -> e.getType() == type;
    }
}
