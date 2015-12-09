package pl.edu.agh.ki.to2.monitor.messaging;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.model.PerformanceDataModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MessageListener {

    private static final int AGGREGATE_EVENTS = 10;
    private static final long EVENT_PUBLISH_INTERVAL_MILLIS = 100;

    private final MessageQueue messageQueue;
    private final PerformanceDataModel dataModel;

    private final List<Event> awaitingEvents = new ArrayList<>(AGGREGATE_EVENTS);

    @Inject
    public MessageListener(MessageQueue messageQueue, PerformanceDataModel dataModel) {
        this.messageQueue = messageQueue;
        this.dataModel = dataModel;
    }

    private void processMessages() {

        while(true) {
            Event event = messageQueue.pop();
            synchronized (awaitingEvents) {
                awaitingEvents.add(event);
            }
        }
    }

    private void scheduleEventDumps() {
        while(true) {
            safeSleep(EVENT_PUBLISH_INTERVAL_MILLIS);
            publishEvents();
        }
    }

    private void publishEvents() {
        synchronized (awaitingEvents) {
            dataModel.update(awaitingEvents);
            awaitingEvents.clear();
        }
    }

    private void safeSleep(long intervalMillis) {
        long now = System.currentTimeMillis();
        long desiredEnd = now + intervalMillis;

        while(now < desiredEnd) {
            try {
                Thread.sleep(intervalMillis);
            } catch (InterruptedException ignored) {}
            now = System.currentTimeMillis();
        }
    }

    public void startService() {
        new Thread(this::processMessages).start();
        new Thread(this::scheduleEventDumps).start();
    }

}
