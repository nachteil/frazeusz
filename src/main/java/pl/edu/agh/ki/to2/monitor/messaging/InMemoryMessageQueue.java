package pl.edu.agh.ki.to2.monitor.messaging;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InMemoryMessageQueue implements MessageQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryMessageQueue.class);

    private static final boolean MESSAGE_DURABLE_PROP = false;
    private static final String AMOUNT_PROPERTY_KEY = "event.property.amount";
    private static final String TYPE_PROPERTY_KEY = "event.property.type";
    private static final String TIMESTAMP_PROPERTY_KEY = "event.property.timestamp";

    private final ClientConsumer consumer;
    private final ClientProducer producer;
    private final ClientSession  session ;

    @Inject
    public InMemoryMessageQueue(ClientConsumer consumer, ClientProducer producer, ClientSession session) {
        this.producer = producer;
        this.consumer = consumer;
        this.session = session;
    }

    @Override
    public void push(Event event) {
        ClientMessage message = createClientMessage(event);
        trySend(message);
    }

    @Override
    public Event pop() {
        ClientMessage message = null;
        while(message == null) {
            message = tryPop();
        }

        return createEvent(message);
    }

    private Event createEvent(ClientMessage message) {


        EventType eventType = EventType.fromValue(message.getStringProperty(TYPE_PROPERTY_KEY));
        int amount = message.getIntProperty(AMOUNT_PROPERTY_KEY);
        long timestamp = message.getLongProperty(TIMESTAMP_PROPERTY_KEY);

        return new Event(eventType, amount, timestamp);
    }

    private ClientMessage createClientMessage(Event event) {
        ClientMessage message;
        synchronized (session) {
            message = session.createMessage(MESSAGE_DURABLE_PROP);
        }
        message.putIntProperty(AMOUNT_PROPERTY_KEY, event.getAmount());
        message.putStringProperty(TYPE_PROPERTY_KEY, event.getType().toString());
        message.putLongProperty(TIMESTAMP_PROPERTY_KEY, event.getTimestamp());

        return message;
    }

    private void trySend(ClientMessage message) {
        try {
            synchronized (producer) {
                producer.send(message);
            }
        } catch (HornetQException e) {
            LOGGER.error("Message send failed", e);
        }
    }

    private ClientMessage tryPop() {
        ClientMessage message = null;
        try {
            synchronized (consumer) {
                message = consumer.receive();
            }
        } catch (HornetQException e) {
            LOGGER.error("Exception while receiving message", e);
        }
        return message;
    }
}
