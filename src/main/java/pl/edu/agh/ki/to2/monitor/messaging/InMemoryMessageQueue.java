package pl.edu.agh.ki.to2.monitor.messaging;

import pl.edu.agh.ki.to2.monitor.contract.Event;

public class InMemoryMessageQueue implements MessageQueue {
    @Override
    public void push(Event event) {

    }

    @Override
    public Event pop() {
        return null;
    }
}
