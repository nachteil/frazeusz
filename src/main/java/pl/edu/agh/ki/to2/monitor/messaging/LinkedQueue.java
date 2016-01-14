package pl.edu.agh.ki.to2.monitor.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Singleton
public class LinkedQueue implements MessageQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkedQueue.class);

    private final BlockingQueue<Event> eventQueue;

    @Inject
    public LinkedQueue() {
        this.eventQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void push(Event event) {
        tryPush(event);
    }

    private void tryPush(Event event) {
        try {
            eventQueue.put(event);
        } catch (InterruptedException e) {
            LOGGER.warn("Error on message send", e);
        }
    }

    @Override
    public Event pop() {
        Event event = null;
        while(event == null) {
            event = tryPop();
        }
        return event;
    }

    private Event tryPop() {
        try {
            return eventQueue.take();
        } catch (InterruptedException e) {
            LOGGER.warn("Error on message receive", e);
        }
        return null;
    }
}
