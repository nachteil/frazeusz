package pl.edu.agh.ki.to2.monitor.model;

import pl.edu.agh.ki.to2.monitor.contract.EventType;

public class DurationEstimator {

    private final PerformanceDataModel dataModel;

    public DurationEstimator(PerformanceDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public int getRemainingSeconds() {
        int crawlSpeedPagesPerSecond = calculateCrawlSpeed();
        int pagesToCrawl = dataModel.getQueueLength();

        if(crawlSpeedPagesPerSecond == 0) {
            return Integer.MAX_VALUE;
        } else {
            return pagesToCrawl / crawlSpeedPagesPerSecond;
        }
    }

    private int calculateCrawlSpeed() {
        double [][] data = dataModel.getData(30, EventType.PAGES_CRAWLED);
        return calculateWeightedAverage(data[1]);
    }

    private int calculateWeightedAverage(double [] data) {
        double sum = 0;
        double weightSum = 0;
        for(int i = 0; i < data.length; ++i) {
            double weight = Math.exp(((i+1) - data.length)/20.0);
            weightSum += weight;
            sum += data[i] * weight;
        }
        return (int)(sum / weightSum);
    }
}
