package pl.edu.agh.ki.to2.monitor.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.model.PerformanceDataModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    private static final int AGGREGATE_EVENTS = 10;
    private static final long EVENT_PUBLISH_INTERVAL_MILLIS = 100;

    private final MessageQueue messageQueue;
    private final PerformanceDataModel dataModel;

    private final List<Event> awaitingEvents = new ArrayList<>(AGGREGATE_EVENTS);

    @Inject
    public MessageListener(MessageQueue messageQueue, PerformanceDataModel dataModel) {
        LOGGER.info("Initializing Message Listener...");
        this.messageQueue = messageQueue;
        this.dataModel = dataModel;
    }

    private void processMessages() {
        LOGGER.info("Starting message processing thread...");
        while(true) {
            Event event = messageQueue.pop();
            LOGGER.debug("Message received: {}", event.toString());
            dispatch(event);
        }
    }

    private void dispatch(Event event) {
        if(event.getType() == EventType.QUEUE_LENGTH) {
            dataModel.setQueueLength(event.getAmount());
        } else {
            synchronized (awaitingEvents) {
                awaitingEvents.add(event);
            }
        }
    }

    private void scheduleEventDumps() {
        LOGGER.info("Starting event dumping thread...");
        while(true) {
            safeSleep(EVENT_PUBLISH_INTERVAL_MILLIS);
            publishEvents();
        }
    }

    private void publishEvents() {
        LOGGER.debug("Publishing {} events to model...", awaitingEvents.size());
        synchronized (awaitingEvents) {
            dataModel.update(Collections.unmodifiableList(awaitingEvents));
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
        new Thread(this::processMessages, "message-processing-thread").start();
        new Thread(this::scheduleEventDumps, "event-publishing-thread").start();
    }

}
