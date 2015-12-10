package pl.edu.agh.ki.to2.monitor.messaging;

import pl.edu.agh.ki.to2.monitor.contract.Event;

public interface MessageQueue {
    void push(Event event);
    Event pop();
}
