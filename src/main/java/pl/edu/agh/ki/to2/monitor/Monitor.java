package pl.edu.agh.ki.to2.monitor;

import dagger.ObjectGraph;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.core.server.HornetQServer;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.monitor.gui.Chart;
import pl.edu.agh.ki.to2.monitor.gui.MonitorTabPanel;
import pl.edu.agh.ki.to2.monitor.messaging.MessagingModule;

import javax.inject.Inject;
import javax.swing.*;

public class Monitor {

    @Inject MonitorPubSub pubSub;
    @Inject HornetQServer server;
    @Inject ClientSession session;
    @Inject ClientSessionFactory sessionFactory;
    @Inject MonitorTabPanel panel;

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
        sessionFactory.close();
        try {
            server.stop();
        } catch (Exception e) {
            System.out.println("HornetQ server shutdown failed");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(Monitor.getInstance().getMonitorTabPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
