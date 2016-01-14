package pl.edu.agh.ki.to2.monitor;

import dagger.ObjectGraph;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.core.server.HornetQServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.monitor.gui.Chart;
import pl.edu.agh.ki.to2.monitor.gui.MonitorTabPanel;
import pl.edu.agh.ki.to2.monitor.messaging.MessageListener;
import pl.edu.agh.ki.to2.monitor.messaging.MessagingModule;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.util.Random;

public class Monitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Monitor.class);

    @Inject MonitorPubSub pubSub;
    @Inject HornetQServer server;
    @Inject ClientSession session;
    @Inject ClientSessionFactory sessionFactory;
    @Inject MonitorTabPanel panel;
    @Inject MessageListener messageListener;

    private static final class LazyHolder {
        private static Monitor createInstance() {
            LOGGER.info("Initializing Monitor component...");
            ObjectGraph graph = ObjectGraph.create(new MessagingModule());
            Monitor m = graph.get(Monitor.class);
            m.run();
            LOGGER.info("Monitor initialized");
            return m;
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
        return panel;
    }


    /**
     *      Provides MonitorPubSub instance. From user perspective, this is the entry point for publishing
     *      performance data. No additional setup is required, just push events via the pubsub.
     * */
    public MonitorPubSub getMonitorPubSub() {
        return pubSub;
    }

    public void cleanUp() {
        LOGGER.info("Monitor component clean-up");
        sessionFactory.close();
        try {
            server.stop();
            LOGGER.info("Monitor clean-up completed");
        } catch (Exception e) {
            LOGGER.error("HornetQ server shutdown failed");
            e.printStackTrace();
        }
    }

    private void run() {
        messageListener.startService();
    }


}
