package pl.edu.agh.ki.to2.monitor.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.util.TimeProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventDataTest {

    private EventData instance;

    @Mock
    TimeProvider timer;

    @Before
    public void setUp() {
        instance = new EventData(timer);
    }

    @Test
    public void shouldReturnValueFromSingleEvent() {

        // given
        prepareTimerMock(10, 11);
        instance.processNewEvent(prepareEvents(EventType.KILOBYTES_DOWNLOADED, new int[]{10}, new long[]{10100}));

        // when
        double [][] res = instance.getDataForPastSeconds(1);

        // then
        assertEquals(2, res.length);
        assertEquals(1, res[0].length);
        assertEquals(1, res[1].length);
        assertEquals(10000, res[0][0], 1e-6);
        assertEquals(10, res[1][0], 1e-6);
    }

    @Test
    public void shouldProcessMultipleEventsForSingleSecond() {

        // given
        prepareTimerMock(10, 11);
        int [] amounts = new int [] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        long [] timestamps = new long [] {10100, 10200, 10300, 10400, 10500, 10600, 10700, 10800, 10900, 10905};
        instance.processNewEvent(prepareEvents(EventType.KILOBYTES_DOWNLOADED, amounts, timestamps));

        // when
        double [][] res = instance.getDataForPastSeconds(1);

        // then
        assertEquals(2, res.length);
        assertEquals(1, res[0].length);
        assertEquals(1, res[1].length);
        assertEquals(10000, res[0][0], 1e-6);
        assertEquals(55, res[1][0], 1e-6);
    }

    @Test
    public void shouldProcessMultipleEventsForMultipleSeconds() {

        // given
        prepareTimerMock(10, 11);
        int [] amounts = new int [] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        long [] timestamps = new long [] {8100, 8200, 8300, 9100, 9200, 9300, 10100, 10200, 10300, 10400};
        int [] updateAmounts = new int [] {5, 5};
        long [] updateTimestamps = new long [] {9250, 10111};
        instance.processNewEvent(prepareEvents(EventType.KILOBYTES_DOWNLOADED, amounts, timestamps));

        // when
        double [][] firstResultFor3Seconds = instance.getDataForPastSeconds(3);
        double [][] firstResultFor2Seconds = instance.getDataForPastSeconds(2);
        instance.processNewEvent(prepareEvents(EventType.KILOBYTES_DOWNLOADED, updateAmounts, updateTimestamps));
        double [][] resultFor2SecondsAfterUpdate = instance.getDataForPastSeconds(2);

        // then
        assertEquals(2,  firstResultFor3Seconds.length);
        assertEquals(3,  firstResultFor3Seconds[0].length);
        assertEquals(3,  firstResultFor3Seconds[1].length);
        assertEquals(8000,  firstResultFor3Seconds[0][0], 1e-6);
        assertEquals(9000,  firstResultFor3Seconds[0][1], 1e-6);
        assertEquals(10000, firstResultFor3Seconds[0][2], 1e-6);
        assertEquals(6,  firstResultFor3Seconds[1][0], 1e-6);
        assertEquals(15, firstResultFor3Seconds[1][1], 1e-6);
        assertEquals(34, firstResultFor3Seconds[1][2], 1e-6);

        assertEquals(2,  firstResultFor2Seconds.length);
        assertEquals(2,  firstResultFor2Seconds[0].length);
        assertEquals(2,  firstResultFor2Seconds[1].length);
        assertEquals(9000,  firstResultFor2Seconds[0][0], 1e-6);
        assertEquals(10000,  firstResultFor2Seconds[0][1], 1e-6);
        assertEquals(15,  firstResultFor2Seconds[1][0], 1e-6);
        assertEquals(34, firstResultFor2Seconds[1][1], 1e-6);

        assertEquals(2,  resultFor2SecondsAfterUpdate.length);
        assertEquals(2,  resultFor2SecondsAfterUpdate[0].length);
        assertEquals(2,  resultFor2SecondsAfterUpdate[1].length);
        assertEquals(9000,  resultFor2SecondsAfterUpdate[0][0], 1e-6);
        assertEquals(10000, resultFor2SecondsAfterUpdate[0][1], 1e-6);
        assertEquals(20, resultFor2SecondsAfterUpdate[1][0], 1e-6);
        assertEquals(39, resultFor2SecondsAfterUpdate[1][1], 1e-6);
    }

    private void prepareTimerMock(int ... timestamps) {
        for(int timestamp : timestamps) {
            when(timer.nowSeconds()).thenReturn(timestamp);
        }
    }

    private List<Event> prepareEvents(EventType type, int [] amounts, long [] timestamps) {
        Event [] events = new Event[amounts.length];
        IntStream.range(0, events.length).forEach(i -> events[i] = new Event(type, amounts[i], timestamps[i]));
        return Arrays.asList(events);
    }
}