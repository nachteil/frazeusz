package pl.edu.agh.ki.to2.monitor.gui;

import javax.swing.*;
import java.awt.*;

public class MonitorTabPanel extends JPanel {

    private final JPanel chartPanel;

    public MonitorTabPanel() {
        super();
        chartPanel = createChartPanel();
        JPanel chartArea = createScalableChartArea(chartPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chartArea);
        this.add(createControlArea());
    }

    private JPanel createChartPanel() {
        JPanel chartPanel = new JPanel();
        chartPanel.setBackground(Color.lightGray);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
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

        JRadioButton bytes = new JRadioButton("Bajty");
        JRadioButton matches = new JRadioButton("Dopasowania");
        JRadioButton pages = new JRadioButton("Strony");

        ButtonGroup dataChoiceGroup = new ButtonGroup();
        dataChoiceGroup.add(bytes);
        dataChoiceGroup.add(matches);
        dataChoiceGroup.add(pages);

        leftBox.add(bytes);
        leftBox.add(matches);
        leftBox.add(pages);

        controlArea.add(leftBox);

        Box rightBox = Box.createVerticalBox();
        rightBox.add(new Label("Zakres czasowy danych"));
        rightBox.add(new JSlider());

        controlArea.add(rightBox);

        return controlArea;
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(new MonitorTabPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
