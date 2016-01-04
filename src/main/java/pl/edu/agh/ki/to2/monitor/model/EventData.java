package pl.edu.agh.ki.to2.monitor.model;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.util.TimeProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventData {

    private final List<Event> events;
    private final Map<Integer, Double> processedData;
    private final TimeProvider timer;

    public EventData(TimeProvider timer) {
        this.events = new ArrayList<>();
        this.processedData = new HashMap<>();
        this.timer = timer;
    }

    public void processNewEvent(List<Event> newEvents) {
        events.addAll(newEvents);
        List<Event> processableEvents = events.stream().filter(e -> e.getTimestamp() / 1000L <= timer.nowSeconds()).collect(Collectors.toList());
        cacheDataFromEvents(processableEvents);
        events.removeAll(processableEvents);
    }

    public double [][] getDataForPastSeconds(int numberOfSeconds) {

        int now = timer.nowSeconds();

        List<Integer> xValues = new ArrayList<>(numberOfSeconds);
        List<Double> yValues = new ArrayList<>(numberOfSeconds);

        for(int sec = now - numberOfSeconds; sec < now; ++sec) {
            xValues.add(sec);
            double yValue = getWithZeroOnNull(processedData.get(sec));
            yValues.add(yValue);
        }
        return to2DDoubleArray(xValues, yValues);
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
            double sumInSecond = bucketedEvents.get(second).stream().mapToInt(Event::getAmount).sum();
            double initialValue = getWithZeroOnNull(processedData.get(second));
            processedData.put(second, initialValue + sumInSecond);
        }
    }

    private Map<Integer, List<Event>> bucketizeBySecond(List<Event> newEvents) {
        Function<Event, Integer> eventsFullSecond = e -> (int) (e.getTimestamp() / 1000);
        return newEvents.stream()
                .collect(Collectors.groupingBy(eventsFullSecond));
    }
}
