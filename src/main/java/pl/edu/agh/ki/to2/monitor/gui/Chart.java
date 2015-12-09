package pl.edu.agh.ki.to2.monitor.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;

public class Chart {

    public JPanel createFromDataSet(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("Title", "x", "y", dataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }
}
