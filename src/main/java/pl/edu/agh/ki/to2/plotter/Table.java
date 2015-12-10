package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Created by Nina on 2015-12-10.
 */
public class Table extends JPanel{

    JTable tab;
    String[] colNames = {"Szukana fraza", "Wystąpienie", "URL"};
    Border empty;
    DefaultTableModel dataModel;

    public Table(){
        setLayout(new GridLayout(1,1));
        empty = BorderFactory.createEmptyBorder(10,10,10,10);
        setBorder(empty);
        setBackground(Color.white);

        dataModel = new DefaultTableModel(colNames, 20);
        tab = new JTable(dataModel);

        JScrollPane scrollPane = new JScrollPane(tab);
        add(scrollPane);
    }
    public void update(Map<SearchPattern,Occurrences> searches){
        while (dataModel.getRowCount() > 0) {
    	       dataModel.removeRow(0);
    	}
        SearchPattern key;
        Map<String, List<String>> map;
        String url;
        List<String> list;
        for (Map.Entry<SearchPattern, Occurrences> entry : searches.entrySet()) {
            key = entry.getKey();
            map = entry.getValue().getUrlSentenceMap();
            for (Map.Entry<String, List<String>> lowerEntry : map.entrySet()){
                url = lowerEntry.getKey();
                list = lowerEntry.getValue();
                for (String value : list){
                    dataModel.addRow(new Object[] {key.getPattern(), url, value});
                }
            }
        }
        tab.repaint();
    }

}
