package pl.edu.agh.ki.to2.monitor;

import dagger.ObjectGraph;
import org.hornetq.api.core.client.ClientProducer;
import pl.edu.agh.ki.to2.monitor.messaging.MessagingModule;

import javax.inject.Inject;

public class Monitor {

    @Inject ClientProducer producer;

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

}
