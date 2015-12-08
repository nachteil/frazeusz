package pl.edu.agh.ki.to2.monitor.contract;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Event {

    protected EventType type;
    protected int amount;
    protected long timestamp;


    public EventType getType() {
        return type;
    }

    public void setType(EventType value) {
        this.type = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int value) {
        this.amount = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long value) {
        this.timestamp = value;
    }

}