package pl.edu.agh.ki.to2.monitor.contract;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Setter @Getter private EventType type;
    @Setter @Getter private int amount;
    @Setter @Getter private long timestamp;

}