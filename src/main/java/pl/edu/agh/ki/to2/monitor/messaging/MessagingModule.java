package pl.edu.agh.ki.to2.monitor.messaging;

import dagger.Module;
import dagger.Provides;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import pl.edu.agh.ki.to2.monitor.Monitor;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.monitor.messaging.hornet.HornetModule;
import pl.edu.agh.ki.to2.monitor.util.SystemTimeProvider;
import pl.edu.agh.ki.to2.monitor.util.TimeProvider;

import javax.inject.Singleton;

@Module(
        includes = HornetModule.class,
        injects = Monitor.class)
public class MessagingModule {

    @Provides
    @Singleton
    MonitorPubSub createMonitorPubSub(MessageQueue messageQueue) {
        return new MessageQueuePubSub(messageQueue);
    }

    @Provides
    @Singleton
    MessageQueue createInMemoryMessageQueue(ClientConsumer consumer, ClientProducer producer, ClientSession session) {
//        return new InMemoryMessageQueue(consumer, producer, session);
        return new LinkedQueue();
    }

    @Provides
    @Singleton
    TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }
}
