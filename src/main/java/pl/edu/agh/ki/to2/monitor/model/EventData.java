package pl.edu.agh.ki.to2.monitor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.util.TimeProvider;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventData {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventData.class);

    private final List<Event> events;
    private final Map<Integer, Double> processedData;
    private final TimeProvider timer;

    public EventData(TimeProvider timer) {
        this.events = new ArrayList<>();
        this.processedData = new HashMap<>();
        this.timer = timer;
    }

    public void processNewEvent(List<Event> newEvents) {
        LOGGER.debug("Received {} new events", newEvents.size());
        events.addAll(newEvents);
        processEvents(events);
    }

    private void processEvents(List<Event> eventsToProcess) {
        List<Event> processableEvents = eventsToProcess.stream().filter(e -> e.getTimestamp() / 1000L <= timer.nowSeconds())
                .collect(Collectors.toList());
        cacheDataFromEvents(processableEvents);
        eventsToProcess.removeAll(processableEvents);
    }

    public double [][] getDataForPastSeconds(int numberOfSeconds) {

        if(events.size() > 0) {
            processEvents(events);
        }

        int now = timer.nowSeconds();

        List<Integer> xValues = new ArrayList<>(numberOfSeconds);
        List<Double> yValues = new ArrayList<>(numberOfSeconds);

        for(int sec = now - numberOfSeconds; sec < now; ++sec) {
            xValues.add(toMillis(sec));
            double yValue = getWithZeroOnNull(processedData.get(sec));
            LOGGER.debug("Value in second {}: {}", sec, yValue);
            yValues.add(yValue);
        }
        return to2DDoubleArray(xValues, yValues);
    }

    private Integer toMillis(int sec) {
        return sec * 1000;
    }

    private double[][] to2DDoubleArray(List<? extends Number> xValues, List<? extends Number> yValues) {
        return new double [][] {
                xValues.stream().mapToDouble(Number::doubleValue).toArray(),
                yValues.stream().mapToDouble(Number::doubleValue).toArray()
        };
    }

    private double getWithZeroOnNull(Double value) {
        if(value != null) {
            return value;
        } else {
            return 0.0;
        }
    }

    private void cacheDataFromEvents(List<Event> newEvents) {

        Map<Integer, List<Event>> bucketedEvents = bucketizeBySecond(newEvents);
        updateData(bucketedEvents);
    }

    private void updateData(Map<Integer, List<Event>> bucketedEvents) {
        for(Integer second : bucketedEvents.keySet()) {
            LOGGER.debug("Updating cache for second {} with {} new events", second, bucketedEvents.get(second).size());
            double sumInSecond = bucketedEvents.get(second).stream().mapToInt(Event::getAmount).sum();
            double initialValue = getWithZeroOnNull(processedData.get(second));
            LOGGER.debug("Put {} for second {}", initialValue + sumInSecond, second);
            processedData.put(second, initialValue + sumInSecond);
        }
    }

    private Map<Integer, List<Event>> bucketizeBySecond(List<Event> newEvents) {
        Function<Event, Integer> eventsFullSecond = e -> (int) (e.getTimestamp() / 1000);
        return newEvents.stream()
                .collect(Collectors.groupingBy(eventsFullSecond));
    }

    class PrettyPrintingMap<K, V> {
        private Map<K, V> map;

        public PrettyPrintingMap(Map<K, V> map) {
            this.map = map;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(System.identityHashCode(map) + ": [");
            Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<K, V> entry = iter.next();
                sb.append(entry.getKey());
                sb.append('=').append('"');
                sb.append(entry.getValue());
                sb.append('"');
                if (iter.hasNext()) {
                    sb.append(',').append(' ');
                }
            }
            return sb.append("]").toString();

        }
    }
}
