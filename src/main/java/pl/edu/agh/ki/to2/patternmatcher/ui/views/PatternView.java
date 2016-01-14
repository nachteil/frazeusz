package pl.edu.agh.ki.to2.patternmatcher.ui.views;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.patternmatcher.ui.controllers.PatternController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatternView extends JPanel implements ActionListener {

    private List<SearchPattern> model;
    private PatternController controller;

    private JPanel patternPanel;
    private PatternInput patternInput;
    private PatternListing patternListing;
    private JPanel msgPanel;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton deleteButton;

    private JLabel message;

    public PatternView(List<SearchPattern> patterns, PatternController controller) {
        model = patterns;
        this.controller = controller;
        createUIComponents();
    }

    private void createMsgPanel() {
        msgPanel = new JPanel();
        msgPanel.setLayout(new BorderLayout());
        message = new JLabel();
        msgPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        msgPanel.add(message, BorderLayout.PAGE_START);
    }

    private void createPatternPanel() {
        patternPanel = new JPanel();
        patternPanel.setLayout(new BoxLayout(patternPanel, BoxLayout.PAGE_AXIS));
        patternPanel.add(msgPanel);

        patternInput = new PatternInput(new SearchPattern());
        patternPanel.add(patternInput);

        patternPanel.add(buttonPanel);
    }

    private void createButtonPanel() {
        addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> {
            patternInput.commit();
            if (patternInput.isEmpty()) {
                message.setText("Wzorzec nie może być pusty!");
                return;
            }
            patternListing.addRow(new SearchPattern(patternInput.getPattern()));
            patternInput.setModel(new SearchPattern());
            message.setText("");
        });

        deleteButton = new JButton("Usuń");
        deleteButton.addActionListener(e -> {
            patternListing.deleteSelected();
            patternListing.revalidate();
            validate();
        });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        addButton.setPreferredSize(new Dimension(200, 20));
        deleteButton.setPreferredSize(new Dimension(200, 20));
    }

    private void createUIComponents() {
        createMsgPanel();
        createButtonPanel();
        createPatternPanel();

        patternListing = new PatternListing(model);

        this.setLayout(new BorderLayout());
        this.add(patternPanel, BorderLayout.PAGE_START);
        this.add(patternListing, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        PatternInput partial = (PatternInput) button.getParent();
        controller.removePattern(partial.getModel());
    }
}
