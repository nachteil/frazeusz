package pl.edu.agh.ki.to2.monitor.util;

public interface TimeProvider {
    int nowSeconds();
    long currentTimeMillis();
}
