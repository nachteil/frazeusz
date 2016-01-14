package pl.edu.agh.ki.to2.monitor.gui;

import net.time4j.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.to2.monitor.contract.Event;
import pl.edu.agh.ki.to2.monitor.contract.EventType;
import pl.edu.agh.ki.to2.monitor.model.PerformanceDataModel;
import pl.edu.agh.ki.to2.monitor.util.SystemTimeProvider;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MonitorTabPanel extends JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorTabPanel.class);

    private static final int REFRESH_INTERVAL_MILLIS = 300;
    private static final int INITIAL_RETROSPECTIVE_PERIOD = 20;

    private final JPanel chartPanel;
    private final PerformanceDataModel dataModel;
    private final Chart chart;
    private JPanel currentChart;
    private final JPanel statusPanel;
    private int retrospectivePeriod;
    private JLabel queueLengthLabel;
    private JLabel etaLabel;
    private EventType eventType = EventType.KILOBYTES_DOWNLOADED;

    @Inject
    public MonitorTabPanel(PerformanceDataModel dataModel, Chart chart) {
        super();

        this.dataModel = dataModel;
        this.chart = chart;
        this.chartPanel = createChartPanel();
        this.statusPanel = createStatusPanel();
        this.retrospectivePeriod = INITIAL_RETROSPECTIVE_PERIOD;

        JPanel chartArea = createScalableChartArea(chartPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(statusPanel);
        this.add(chartArea);
        this.add(createControlArea());

        currentChart = createChart();
        this.chartPanel.add(currentChart);

        this.startRefreshing();
    }

    private JPanel createStatusPanel() {

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        etaLabel = new JLabel("eta");
        queueLengthLabel = new JLabel("len");

        statusPanel.add(etaLabel);
        statusPanel.add(Box.createRigidArea(new Dimension(100, 10)));
        statusPanel.add(queueLengthLabel);

        return statusPanel;
    }

    private void updateLabels() {
        String etaFormat = "Remaining time: %s";
        String queueLengthFormat = "Pages queue length: %s";

        String qLenText = String.valueOf(dataModel.getQueueLength());
        Duration remainingDuration = Duration.ofSeconds(dataModel.getRemainingSeconds());
        String etaText = PrettyTime.of(Locale.ENGLISH).print(remainingDuration);

        etaLabel.setText(String.format(etaFormat, etaText));
        queueLengthLabel.setText(String.format(queueLengthFormat, qLenText));
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

        bytes.addActionListener(event -> {eventType = EventType.KILOBYTES_DOWNLOADED; paintChart();});
        matches.addActionListener(event -> {eventType = EventType.SENTENCES_MATCHED; paintChart();});
        pages.addActionListener(event -> {eventType = EventType.PAGES_CRAWLED; paintChart();});

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
        slider.setValue(INITIAL_RETROSPECTIVE_PERIOD);

        slider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            if(!source.getValueIsAdjusting()) {
                this.retrospectivePeriod = source.getValue();
                this.chartPanel.remove(currentChart);
                this.currentChart = createChart();
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
        this.currentChart = createChart();
        this.chartPanel.add(currentChart);
        chartPanel.revalidate();
    }

    private JPanel createChart() {
        return chart.createFromDataSet(dataModel.getDataSet(retrospectivePeriod, eventType), eventType);
    }

    private void startRefreshing() {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(REFRESH_INTERVAL_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                paintChart();
                updateLabels();
            }
        }, "tab-panel-refreshing-thread").start();
    }

    private static void generateRandomEvent(EventType type, PerformanceDataModel model) {
        Random random = new Random();
        int numSeconds = 180;
        final int millisPerSecond = 1000;
        long nowMillis = System.currentTimeMillis() - numSeconds * millisPerSecond;
        for(int i = 0; i < 360; ++i) {
            List<Event> events = new ArrayList<>();
            int i1 = random.nextInt(30);
            for(int j = 0; j < i1; ++j) {
                events.add(new Event(type, random.nextInt(100), nowMillis + random.nextInt(1000)));
            }
            nowMillis += millisPerSecond;
            model.update(events);
        }
    }

    public static void main(String[] args) {

        LOGGER.trace("Main started");

        PerformanceDataModel model = new PerformanceDataModel(new SystemTimeProvider());
        generateRandomEvent(EventType.KILOBYTES_DOWNLOADED, model);
        LOGGER.info("Bandwidth data populated");
        generateRandomEvent(EventType.SENTENCES_MATCHED, model);
        LOGGER.info("Match data populated");
        generateRandomEvent(EventType.PAGES_CRAWLED, model);
        LOGGER.info("Crawl data populated");

        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(new MonitorTabPanel(model, new Chart()));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
