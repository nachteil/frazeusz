package pl.edu.agh.ki.to2.patternmatcher.ui.views;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PatternPartial extends JPanel {

    private SearchPattern model;

    private JTextField patternTextField;
    private JCheckBox caseSensitiveCheckBox;
    private JCheckBox synonymsCheckBox;
    private JCheckBox variantCheckBox;
    private JCheckBox diminutiveCheckBox;

    public PatternPartial() {
        model = new SearchPattern();
        createUIComponents();
    }

    public SearchPattern getModel() {
        return model;
    }

    public void setModel(SearchPattern model) {
        this.model = model;
    }

    private void bindModel(SearchPattern model) {
        patternTextField.addPropertyChangeListener(e -> model.setPattern(patternTextField.getText()));
        caseSensitiveCheckBox.addPropertyChangeListener(e -> model.setCaseSensitive(caseSensitiveCheckBox.isSelected()));
        synonymsCheckBox.addPropertyChangeListener(e -> model.setSynonyms(synonymsCheckBox.isSelected()));
        variantCheckBox.addPropertyChangeListener(e -> model.setVariants(variantCheckBox.isSelected()));
        diminutiveCheckBox.addPropertyChangeListener(e -> model.setDiminutives(diminutiveCheckBox.isSelected()));
    }

    public void commit() {
        model.setPattern(patternTextField.getText());
        model.setCaseSensitive(caseSensitiveCheckBox.isSelected());
        model.setSynonyms(synonymsCheckBox.isSelected());
        model.setVariants(variantCheckBox.isSelected());
        model.setDiminutives(diminutiveCheckBox.isSelected());
    }

    public SearchPattern getPattern() {
        return model;
    }

    public boolean isEmpty() {
        return model.getPattern().isEmpty();
    }

    private void createUIComponents() {
        patternTextField = new JTextField(20);
        caseSensitiveCheckBox = new JCheckBox("wielkie litery");
        synonymsCheckBox = new JCheckBox("synonimy");
        variantCheckBox = new JCheckBox("odmiany");
        diminutiveCheckBox = new JCheckBox("zdrobnienia");

        this.add(patternTextField);
        this.add(caseSensitiveCheckBox);
        this.add(synonymsCheckBox);
        this.add(variantCheckBox);
        this.add(diminutiveCheckBox);

        bindModel(model);
    }
}
