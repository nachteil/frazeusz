package pl.edu.agh.ki.to2.monitor;

import dagger.ObjectGraph;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.monitor.messaging.MessagingModule;

import javax.inject.Inject;
import javax.swing.*;

public class Monitor {

    @Inject MonitorPubSub pubSub;

    private static final class LazyHolder {
        private static Monitor createInstance() {
            ObjectGraph graph = ObjectGraph.create(new MessagingModule());
            return graph.get(Monitor.class);
        }
        private static final Monitor INSTANCE = createInstance();
    }

    public static Monitor getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     *     This method will provide JPanel instance that will present Monitor performance data
     *     Returned panel should me embedded into window.
     *     Monitor component will take care of data collection and content refreshing
     */
    public JPanel getMonitorTabPanel() {
        // TODO: Implement
        return null;
    }


    /**
     *      Provides MonitorPubSub instance. From user perspective, this is the entry point for publishing
     *      performance data. No additional setup is required, just push events via the pubsub.
     * */
    public MonitorPubSub getMonitorPubSub() {
        return pubSub;
    }
}
