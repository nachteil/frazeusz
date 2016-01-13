package pl.edu.agh.ki.to2.crawler.gui;


import pl.edu.agh.ki.to2.crawler.gui.controllers.DateFrameController;
import pl.edu.agh.ki.to2.monitor.Monitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 *
 * Created by Rav on 2015-11-25.
 */
public class DataFrame extends JFrame {
    private JButton submitButton;
    private JButton closeButton;
    private InitialDataPanel initialDataPanel;

    private DateFrameController dateFrameController;

    public DataFrame(DateFrameController dateFrameController) {
        super("Frazeusz");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        this.dateFrameController = dateFrameController;
        dateFrameController.setDateFrame(this);
        initComponents();
        addListeners();
        setResizable(true);
        setSize(new Dimension( 1000, 1000 ));
    }

    private void initComponents() {
        submitButton = new JButton("Start");
        closeButton = new JButton("Close");
        initialDataPanel = new InitialDataPanel(dateFrameController);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Initial Data", initialDataPanel);
        tabbedPane.addTab("Patterns", dateFrameController.getPatternMatcher().getView());
        tabbedPane.addTab("Monitor", Monitor.getInstance().getMonitorTabPanel());
        add(tabbedPane, BorderLayout.NORTH);
        add(submitClosePanel(), BorderLayout.SOUTH);

    }

    private JPanel submitClosePanel(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1,2));
        jPanel.add(submitButton);
        jPanel.add(closeButton);
        jPanel.setSize(new Dimension(300, 50));
        return jPanel;
    }

    private void addListeners() {
        addSubmitListener();
        addCloseListener();
    }

    private void addSubmitListener() {
        submitButton.addActionListener(e -> {
            String error = dateFrameController.checkErrors();
            if(error!=null){
                JOptionPane.showMessageDialog(this,
                        error,
                        "Input data error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                dateFrameController.start();
//                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    private void addCloseListener() {
        closeButton.addActionListener(e -> {
            dateFrameController.close();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }

    public InitialDataPanel getInitialDataPanel() {
        return initialDataPanel;
    }
}
