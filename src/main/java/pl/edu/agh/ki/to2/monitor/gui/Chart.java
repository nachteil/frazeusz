package pl.edu.agh.ki.to2.monitor.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import pl.edu.agh.ki.to2.monitor.contract.EventType;

import javax.inject.Inject;
import javax.swing.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Chart {

    private final Map<EventType, String> chartTitles;
    private final Map<EventType, String> yAxisDescriptions;

    @Inject
    public Chart() {

        chartTitles = new HashMap<>();
        yAxisDescriptions = new HashMap<>();

        chartTitles.put(EventType.KILOBYTES_DOWNLOADED, "Bandwidth usage");
        chartTitles.put(EventType.PAGES_CRAWLED, "Visited pages");
        chartTitles.put(EventType.SENTENCES_MATCHED, "Matches");

        // TODO - Migrate to .properties
        yAxisDescriptions.put(EventType.KILOBYTES_DOWNLOADED, "kbps");
        yAxisDescriptions.put(EventType.PAGES_CRAWLED, "# pages crawled");
        yAxisDescriptions.put(EventType.SENTENCES_MATCHED, "# sentences matched");
    }

    public JPanel createFromDataSet(XYDataset dataset, EventType eventType) {

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitles.get(eventType), "t [s]",
                yAxisDescriptions.get(eventType), dataset, PlotOrientation.VERTICAL, false, true, false);

        XYPlot plot = chart.getXYPlot();
        plot.setDomainAxis(new DateAxis("time", TimeZone.getTimeZone("GMT+2"), Locale.getDefault()));

        return new ChartPanel(chart);
    }
}
