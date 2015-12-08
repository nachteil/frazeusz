package pl.edu.agh.ki.to2.monitor.messaging;

import dagger.Module;
import dagger.ObjectGraph;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.core.server.HornetQServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class InMemoryMessageQueueDaggerTest {

    @Module(
            includes = MessagingModule.class,
            injects = InMemoryMessageQueueDaggerTest.class,
            overrides = true
    )
    class TestModule {

    }

    @Inject InMemoryMessageQueue instance;
    @Inject HornetQServer server;
    @Inject ClientSessionFactory sessionFactory;

    @Before
    public void setUp() {
        ObjectGraph.create(new TestModule()).inject(this);
    }

    @After
    public void cleanUp() throws Exception {
        sessionFactory.close();
        server.stop();
    }

    @Test
    public void shouldCorrectlySendAndReceiveMessages() {

        // given
        long now = System.currentTimeMillis();
        Event eventToSend = new Event();
        eventToSend.setAmount(123);
        eventToSend.setTimestamp(now);
        eventToSend.setType(EventType.PAGES_CRAWLED);

        // when
        instance.push(eventToSend);
        Event receivedEvent = instance.pop();

        // then
        assertEquals(receivedEvent.getAmount(), eventToSend.getAmount());
        assertEquals(receivedEvent.getTimestamp(), eventToSend.getTimestamp());
        assertEquals(receivedEvent.getType(), eventToSend.getType());
    }

}