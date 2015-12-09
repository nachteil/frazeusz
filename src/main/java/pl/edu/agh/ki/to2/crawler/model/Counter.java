package pl.edu.agh.ki.to2.crawler.model;

public class Counter {

    private int counter;

    Counter(){this.counter = 0;}

    public synchronized int getCounter() {
        return counter;
    }

    public synchronized void increase(){ this.counter++; }

}
