package pl.edu.agh.ki.to2.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;

import javax.swing.*;
import java.util.Random;

class MonitorDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorDemo.class);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        Monitor monitor = Monitor.getInstance();
        frame.getContentPane().add(monitor.getMonitorTabPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);

        startProducer();

        frame.setVisible(true);
    }

    private static void startProducer() {
        new Thread(() -> {

            MonitorPubSub ps = Monitor.getInstance().getMonitorPubSub();
            Random random = new Random();

            long nextWakeUp = System.currentTimeMillis() + 1000;
            EventType[] values = new EventType[] {EventType.KILOBYTES_DOWNLOADED, EventType.PAGES_CRAWLED, EventType.SENTENCES_MATCHED};
            int numOfTypes = values.length;

            int startSec = (int) System.currentTimeMillis() / 1000;

            while(true) {

                int iterations = random.nextInt(40);
                for(int i = 0; i < iterations; ++i) {
                    EventType randomType = values[random.nextInt(numOfTypes)];
                    ps.pushEvent(new Event(randomType, random.nextInt(100), nextWakeUp + random.nextInt(1000)));
                }
                long toSleep = nextWakeUp - System.currentTimeMillis();
                try {
                    Thread.sleep(toSleep);
                } catch (InterruptedException e) {
                    LOGGER.warn("Interrupted", e);
                }
                int nowSec = (int) System.currentTimeMillis() / 1000;
                int duration = nowSec - startSec;
                int qLen = (int) (40000.0 * duration / Math.pow(duration + 10, 2));
                ps.pushEvent(new Event(EventType.QUEUE_LENGTH, qLen, System.currentTimeMillis()));
                nextWakeUp = System.currentTimeMillis() + 1000;
            }

        }, "monitor-mock-producer-thread").start();
    }
}
