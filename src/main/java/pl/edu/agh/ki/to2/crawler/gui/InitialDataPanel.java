package pl.edu.agh.ki.to2.crawler.gui;


import pl.edu.agh.ki.to2.crawler.gui.controllers.DateFrameController;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Created by Rav on 2015-11-25.
 */
public class InitialDataPanel extends JPanel{
    private JButton deleteButton;
    private JButton addButton;
    private JLabel maxDepthLabel;
    private JLabel maxNumberOfFilesLabel;
    private JLabel addUrlLabel;
    private JLabel speedLabel;
    private JTextField maxDepthField;
    private JTextField addUrlField;
    private JTextField maxNumberOfFilesField;
    private JTextField speedField;
    private JList listOfUrlsPane;
    private DefaultListModel model;


    private DateFrameController dateFrameController;

    public InitialDataPanel(DateFrameController dateFrameController) {
        this.dateFrameController = dateFrameController;
        this.initComponents();
        this.addComponents();
        this.addListeners();
    }

    private void initComponents(){
        this.deleteButton = new JButton("Delete");
        this.addButton = new JButton("Add");
        this.maxDepthLabel = new JLabel("Max Depth:");
        this.maxNumberOfFilesLabel = new JLabel("Max number of files:");
        this.addUrlLabel = new JLabel("Add URL:");
        this.speedLabel = new JLabel("Max files per second:");
        this.maxDepthField = new JTextField();
        this.maxNumberOfFilesField = new JTextField();
        this.addUrlField = new JTextField();
        this.speedField = new JTextField();
        this.model = new DefaultListModel();
        this.listOfUrlsPane = new JList(model);
    }

    private void addComponents(){
        setLayout(new BorderLayout());
        JPanel upInitialDatePanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        upInitialDatePanel.setLayout(gridbag);
        addLabelTextRows(new JLabel[]{maxDepthLabel, maxNumberOfFilesLabel, addUrlLabel, speedLabel},
                new JTextField[]{maxDepthField, maxNumberOfFilesField, addUrlField, speedField},
                gridbag, upInitialDatePanel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        upInitialDatePanel.add(addButton, c);
        upInitialDatePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Provide Initial Data"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel downInitialDatePanel = new JPanel();
        downInitialDatePanel.setLayout(new BorderLayout());
        downInitialDatePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Urls"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        listOfUrlsPane.setVisibleRowCount(6);
        JScrollPane listOfUrlsJScrollPane = new JScrollPane(listOfUrlsPane);
        listOfUrlsJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        downInitialDatePanel.add(listOfUrlsJScrollPane, BorderLayout.NORTH);
        JPanel delPanel = new JPanel();
        delPanel.setLayout(gridbag);
        delPanel.add(deleteButton,c);
        downInitialDatePanel.add(delPanel, BorderLayout.CENTER);

        add(upInitialDatePanel, BorderLayout.NORTH);
        add(downInitialDatePanel, BorderLayout.CENTER);
    }

    private void addLabelTextRows(JLabel[] labels,
                                  JTextField[] textFields,
                                  GridBagLayout gridbag,
                                  Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
            textFields[i].setSize(new Dimension(200,20));
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            container.add(labels[i], c);

            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }

    private void addListeners(){
        addButton.addActionListener(e -> dateFrameController.addUrl());
        deleteButton.addActionListener(e -> dateFrameController.deleteUrl());
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JLabel getMaxDepthLabel() {
        return maxDepthLabel;
    }

    public void setMaxDepthLabel(JLabel maxDepthLabel) {
        this.maxDepthLabel = maxDepthLabel;
    }

    public JLabel getMaxNumberOfFilesLabel() {
        return maxNumberOfFilesLabel;
    }

    public void setMaxNumberOfFilesLabel(JLabel maxNumberOfFilesLabel) {
        this.maxNumberOfFilesLabel = maxNumberOfFilesLabel;
    }

    public JLabel getAddUrlLabel() {
        return addUrlLabel;
    }

    public void setAddUrlLabel(JLabel addUrlLabel) {
        this.addUrlLabel = addUrlLabel;
    }

    public JTextField getMaxDepthField() {
        return maxDepthField;
    }

    public void setMaxDepthField(JTextField maxDepthField) {
        this.maxDepthField = maxDepthField;
    }

    public JTextField getAddUrlField() {
        return addUrlField;
    }

    public void setAddUrlField(JTextField addUrlField) {
        this.addUrlField = addUrlField;
    }

    public JTextField getMaxNumberOfFilesField() {
        return maxNumberOfFilesField;
    }

    public void setMaxNumberOfFilesField(JTextField maxNumberOfFilesField) {
        this.maxNumberOfFilesField = maxNumberOfFilesField;
    }

    public JList getListOfUrlsPane() {
        return listOfUrlsPane;
    }

    public void setListOfUrlsPane(JList listOfUrlsPane) {
        this.listOfUrlsPane = listOfUrlsPane;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }


    public DefaultListModel getModel() {
        return model;
    }

    public void setModel(DefaultListModel model) {
        this.model = model;
    }

    public JLabel getSpeedLabel() {
        return speedLabel;
    }

    public void setSpeedLabel(JLabel speedLabel) {
        this.speedLabel = speedLabel;
    }

    public JTextField getSpeedField() {
        return speedField;
    }

    public void setSpeedField(JTextField speedField) {
        this.speedField = speedField;
    }

}
