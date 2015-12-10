package pl.edu.agh.ki.to2.crawler.model;

import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;

public class Counter {

    private MonitorPubSub monitorPubSub;
    private int counter;
    private int chunk;
    private int pagesSinceLastMsg;

    Counter(MonitorPubSub monitorPubSub, int chunk){
        this.monitorPubSub = monitorPubSub;
        this.counter = 0;
        this.chunk = chunk;
        this.pagesSinceLastMsg = 0;
    }

    public synchronized int getCounter() {
        return counter;
    }

    public synchronized void increase(){
        this.counter++;
        if(++pagesSinceLastMsg == chunk){
            sendEvent();
            pagesSinceLastMsg = 0;
        }
    }

    private void sendEvent(int amount, EventType eventType) {
        Event event = new Event();
        event.setTimestamp(System.currentTimeMillis());
        event.setAmount(amount);
        event.setType(EventType.fromValue());
    }

}
