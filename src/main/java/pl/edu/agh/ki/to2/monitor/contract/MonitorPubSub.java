package pl.edu.agh.ki.to2.monitor.contract;

public interface MonitorPubSub {
    public void pushEvent(Event event);
}
