package pl.edu.agh.ki.to2.monitor.util;

import javax.inject.Inject;

public class SystemTimeProvider implements TimeProvider {

    @Inject
    public SystemTimeProvider() {
    }

    @Override
    public int nowSeconds() {
        return (int) (currentTimeMillis() / 1000L);
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
