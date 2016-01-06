package pl.edu.agh.ki.to2.monitor.model;

import org.junit.Test;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import java.util.function.IntFunction;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DurationEstimatorTest {

    @Test
    public void shouldCalculateWeightedAverageForFullDataSet() {

        // given
        IntFunction<Integer> generatorFunction = i -> 2 * i + 1;
        double [][] mockData = new double[2][30];
        for(int i = 0; i < 30; ++i) {
            mockData[1][i] = generatorFunction.apply(i+1);
        }
        PerformanceDataModel mockModel = mock(PerformanceDataModel.class);
        when(mockModel.getData(anyInt(), any(EventType.class))).thenReturn(mockData);
        when(mockModel.getQueueLength()).thenReturn(10000);
        DurationEstimator instance = new DurationEstimator(mockModel);

        // when
        int remainingSeconds = instance.getRemainingSeconds();

        // then
        assertEquals(256, remainingSeconds);
    }


}