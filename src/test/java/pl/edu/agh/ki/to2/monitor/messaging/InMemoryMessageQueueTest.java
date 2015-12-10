package pl.edu.agh.ki.to2.monitor.messaging;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryMessageQueueTest {

    private InMemoryMessageQueue instance;

    @Mock ClientProducer mockProducer;
    @Mock ClientConsumer mockConsumer;
    @Mock ClientSession  mockSession;

    @Before
    public void setUp() {
        reset(mockProducer, mockConsumer, mockSession);
        this.instance = new InMemoryMessageQueue(mockConsumer, mockProducer, mockSession);
    }

    @Test
    public void shouldCreateAndSendEventInMessageOnPush() throws HornetQException {

        // given
        Event event = new Event(EventType.SENTENCES_MATCHED, 19, 43434343L);
        ClientMessage mockMessage = mock(ClientMessage.class);
        when(mockSession.createMessage(anyBoolean())).thenReturn(mockMessage);

        // when
        instance.push(event);

        // then
        verify(mockSession, times(1)).createMessage(eq(false));
        verify(mockProducer, times(1)).send(mockMessage);
        verify(mockMessage, times(1)).putStringProperty(eq("event.property.type"), eq("SENTENCES_MATCHED"));
        verify(mockMessage, times(1)).putLongProperty(eq("event.property.timestamp"), eq(43434343L));
        verify(mockMessage, times(1)).putIntProperty(eq("event.property.amount"), eq(19));
        verifyNoMoreInteractions(mockConsumer, mockSession, mockProducer);
    }

    @Test
    public void shouldAwaitMessageAndIgnoreExceptions() throws HornetQException {

        // given
        ClientMessage mockMessage = createMockClientMessage();

        when(mockConsumer.receive())
                .thenThrow(mock(HornetQException.class))
                .thenThrow(mock(HornetQException.class))
                .thenThrow(mock(HornetQException.class))
                .thenThrow(mock(HornetQException.class))
                .thenReturn(mockMessage);

        // when
        Event poppedEvent = instance.pop();

        // then
        assertEquals(1234567654321L, poppedEvent.getTimestamp());
        assertEquals(765, poppedEvent.getAmount());
        assertEquals(EventType.KILOBYTES_DOWNLOADED, poppedEvent.getType());
    }

    private ClientMessage createMockClientMessage() {
        ClientMessage mockMessage = mock(ClientMessage.class);
        when(mockMessage.getIntProperty(eq("event.property.amount"))).thenReturn(765);
        when(mockMessage.getLongProperty(eq("event.property.timestamp"))).thenReturn(1234567654321L);
        when(mockMessage.getStringProperty(eq("event.property.type"))).thenReturn(EventType.KILOBYTES_DOWNLOADED.toString());
        return mockMessage;
    }

}