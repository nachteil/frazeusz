package pl.edu.agh.ki.to2.monitor;

import org.junit.AfterClass;
import org.junit.Test;

public class LazyHolderTest {

    @AfterClass
    public static void shutDown() {
        Monitor.getInstance().cleanUp();
    }

    @Test
    public void should() {

        // given
        Monitor monitor = null;

        // when
        monitor = Monitor.getInstance();

        // then

    }

}