package pl.edu.agh.ki.to2.monitor.gui;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.model.PerformanceDataModel;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonitorTabPanel extends JPanel {

    private final DataFetchStrategy bandwidthStrategy = m -> m.getBandwidthDataSet(this.startingFetchPoint);
    private final DataFetchStrategy matchStrategy = m -> m.getSentenceMatchDataSet(this.startingFetchPoint);
    private final DataFetchStrategy crawlStrategy = m -> m.getCrawledPagesDataSet(this.startingFetchPoint);

    private final JPanel chartPanel;

    private final PerformanceDataModel dataModel;
    private final Chart chart;
    private DataFetchStrategy fetchStrategy = bandwidthStrategy;
    private long startingFetchPoint;
    private JPanel currentChart;
    private int retrospectivePeriod;
    private EventType eventType = EventType.KILOBYTES_DOWNLOADED;

    @Inject
    public MonitorTabPanel(PerformanceDataModel dataModel, Chart chart) {
        super();

        this.dataModel = dataModel;
        this.chart = chart;
        this.chartPanel = createChartPanel();
        this.retrospectivePeriod = 22;
        this.startingFetchPoint = System.currentTimeMillis() - retrospectivePeriod * 1000L;

        JPanel chartArea = createScalableChartArea(chartPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chartArea);
        this.add(createControlArea());

        currentChart = chart.createFromDataSet(fetchStrategy.fetch(dataModel), eventType);
        this.chartPanel.add(currentChart);

        this.startRefreshing();
    }

    private JPanel createChartPanel() {
        JPanel chartPanel = new JPanel();
        chartPanel.setBackground(Color.white);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), new EmptyBorder(2, 2, 2, 2)));
        return chartPanel;
    }

    private JPanel createScalableChartArea(JPanel chartPanel) {

        Dimension widthDonor = new Dimension(80, 50);
        Dimension heightDonor = new Dimension(100, 30);

        JPanel chartArea = new JPanel();

        chartArea.setLayout(new BorderLayout());

        Component right = new JPanel();
        right.setPreferredSize(widthDonor);

        Component top = new JPanel();
        top.setPreferredSize(heightDonor);

        Component left = new JPanel();
        left.setPreferredSize(widthDonor);

        Component bottom = new JPanel();
        bottom.setPreferredSize(heightDonor);

        chartArea.add(left, BorderLayout.EAST);
        chartArea.add(right, BorderLayout.WEST);
        chartArea.add(top, BorderLayout.NORTH);
        chartArea.add(bottom, BorderLayout.SOUTH);

        chartArea.add(chartPanel, BorderLayout.CENTER);

        chartArea.setPreferredSize(new Dimension(800, 600));

        return chartArea;
    }

    private JPanel createControlArea() {

        JPanel controlArea = new JPanel();

        controlArea.setLayout(new BoxLayout(controlArea, BoxLayout.X_AXIS));

        Box leftBox = Box.createVerticalBox();

        // RADIO BOX
        JRadioButton bytes = new JRadioButton("Bajty");
        JRadioButton matches = new JRadioButton("Dopasowania");
        JRadioButton pages = new JRadioButton("Strony");

        bytes.addActionListener(event -> {fetchStrategy = bandwidthStrategy; eventType = EventType.KILOBYTES_DOWNLOADED; paintChart();});
        matches.addActionListener(event -> {fetchStrategy = matchStrategy; eventType = EventType.SENTENCES_MATCHED; paintChart();});
        pages.addActionListener(event -> {fetchStrategy = crawlStrategy; eventType = EventType.PAGES_CRAWLED; paintChart();});

        ButtonGroup dataChoiceGroup = new ButtonGroup();
        dataChoiceGroup.add(bytes);
        dataChoiceGroup.add(matches);
        dataChoiceGroup.add(pages);

        leftBox.add(bytes);
        leftBox.add(matches);
        leftBox.add(pages);

        bytes.setSelected(true);

        controlArea.add(leftBox);

        Box rightBox = Box.createVerticalBox();
        rightBox.add(new Label("Zakres czasowy danych"));
        JSlider slider = new JSlider();
        slider.setMinimum(10);
        slider.setMaximum(180);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(60);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setValue(1);

        slider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            if(!source.getValueIsAdjusting()) {
                this.retrospectivePeriod = source.getValue();
                this.startingFetchPoint = System.currentTimeMillis() - (this.retrospectivePeriod * 1000L);
                this.chartPanel.remove(currentChart);
                this.currentChart = chart.createFromDataSet(fetchStrategy.fetch(dataModel), eventType);
                this.chartPanel.add(currentChart);
                chartPanel.revalidate();
            }
        });

        rightBox.add(slider);

        controlArea.add(rightBox);

        return controlArea;
    }

    private void paintChart() {
        this.chartPanel.remove(currentChart);
        this.currentChart = chart.createFromDataSet(fetchStrategy.fetch(dataModel), eventType);
        this.chartPanel.add(currentChart);
        chartPanel.revalidate();
    }

    private void startRefreshing() {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.startingFetchPoint = System.currentTimeMillis() - retrospectivePeriod * 1000L;
                paintChart();
            }
        }).start();
    }

    private static interface DataFetchStrategy {
        XYDataset fetch(PerformanceDataModel model);
    }

    private static void generateRandomEvent(EventType type, PerformanceDataModel model) {
        Random random = new Random();
        long start = System.currentTimeMillis() - 180000;
        for(int i = 0; i < 1800; ++i) {
            List<Event> events = new ArrayList<>();
            for(int j = 0; j < random.nextInt(30); ++j) {
                events.add(new Event(type, random.nextInt(100), start + random.nextInt(1000)));
            }
            start += 1000;
            model.update(events);
        }
    }

    public static void main(String[] args) {

        double[][] data = { {0.1, 0.2, 0.3}, {1, 2, 3} };
        DefaultXYDataset ds = new DefaultXYDataset();
        ds.addSeries("series1", data);

        PerformanceDataModel model = new PerformanceDataModel();
        generateRandomEvent(EventType.KILOBYTES_DOWNLOADED, model);
        generateRandomEvent(EventType.SENTENCES_MATCHED, model);
        generateRandomEvent(EventType.PAGES_CRAWLED, model);

        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(new MonitorTabPanel(model, new Chart()));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
