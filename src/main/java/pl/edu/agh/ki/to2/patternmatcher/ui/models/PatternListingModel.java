package pl.edu.agh.ki.to2.patternmatcher.ui.models;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PatternListingModel extends DefaultTableModel {

    private List<SearchPattern> patterns;
    private static String[] colNames = { "Szukana fraza", "Wielkie litery", "Synonimy", "Odmiany", "Zdrobnienia" };
    private static Map<Integer, Function<SearchPattern, String>> colToValMap = new HashMap<>(colNames.length);
    private static Map<Boolean, String> bToS = new HashMap<>(2);

    public PatternListingModel(List<SearchPattern> patterns) {
        super(colNames, colNames.length);
        this.patterns = patterns;

        bToS.put(true, "Tak");
        bToS.put(false, "Nie");

        colToValMap.put(0, SearchPattern::getPattern);
        colToValMap.put(1, p -> bToS.get(p.getCaseSensitive()));
        colToValMap.put(2, p -> bToS.get(p.getSynonyms()));
        colToValMap.put(3, p -> bToS.get(p.getVariants()));
        colToValMap.put(4, p -> bToS.get(p.getDiminutives()));
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        if (patterns != null) return patterns.size(); else return 0;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (patterns != null)
            return colToValMap.get(columnIndex).apply(patterns.get(rowIndex));
        else return null;
    }

    public void addRow(SearchPattern pattern) {
        patterns.add(pattern);
        fireTableRowsInserted(patterns.size()-1, patterns.size()-1);
    }

    @Override
    public void removeRow(int row) {
        patterns.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
