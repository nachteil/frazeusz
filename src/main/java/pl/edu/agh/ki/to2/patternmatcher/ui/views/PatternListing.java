package pl.edu.agh.ki.to2.patternmatcher.ui.views;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.patternmatcher.ui.models.PatternListingModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.List;

public class PatternListing extends JPanel implements TableModelListener {

    PatternListingModel model;

    private JScrollPane pane;
    private JTable patternListing;

    public PatternListing(List<SearchPattern> patterns) {
        model = new PatternListingModel(patterns);
        model.addTableModelListener(this);
        createUIComponents();
    }

    private void createUIComponents() {
        patternListing = new JTable(model);
        pane = new JScrollPane(patternListing);

        this.setLayout(new BorderLayout());
        this.add(patternListing.getTableHeader(), BorderLayout.PAGE_START);
        this.add(patternListing, BorderLayout.CENTER);
    }

    public void addRow(SearchPattern pattern) {
        model.addRow(pattern);
    }

    public void deleteSelected() {
        for (int row : patternListing.getSelectedRows())
            model.removeRow(row);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        patternListing.revalidate();
        validate();
    }
}
