package pl.edu.agh.ki.to2.monitor.messaging;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import pl.edu.agh.ki.to2.monitor.contract.Event;

public class InMemoryMessageQueue implements MessageQueue {

    private static final boolean MESSAGE_DURABLE_PROP = false;
    private static final String EVENT_PROPERTY_KEY = "event.property";

    private final ClientConsumer consumer;
    private final ClientProducer producer;
    private final ClientSession  session ;

    public InMemoryMessageQueue(ClientConsumer consumer, ClientProducer producer, ClientSession session) {
        this.producer = producer;
        this.consumer = consumer;
        this.session = session;
    }

    @Override
    public void push(Event event) {
        ClientMessage message = session.createMessage(MESSAGE_DURABLE_PROP);
        message.putObjectProperty(EVENT_PROPERTY_KEY, event);
        trySend(message);
    }

    @Override
    public Event pop() {
        ClientMessage message = null;
        while(message == null) {
            message = tryPop();
        }
        return (Event) message.getObjectProperty(EVENT_PROPERTY_KEY);
    }

    private void trySend(ClientMessage message) {
        try {
            producer.send(message);
        } catch (HornetQException e) {
            System.out.println("Message send failed");
            e.printStackTrace();
        }
    }

    private ClientMessage tryPop() {
        ClientMessage message = null;
        try {
            message = consumer.receive();
        } catch (HornetQException e) {
            System.out.println("Exception while receiving message");
            e.printStackTrace();
        }
        return message;
    }
}
