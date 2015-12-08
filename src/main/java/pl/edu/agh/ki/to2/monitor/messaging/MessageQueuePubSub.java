package pl.edu.agh.ki.to2.monitor.messaging;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;

import javax.inject.Inject;

public class MessageQueuePubSub implements MonitorPubSub {

    private final MessageQueue messageQueue;

    @Inject
    public MessageQueuePubSub(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void pushEvent(Event event) {
        messageQueue.push(event);
    }
}
