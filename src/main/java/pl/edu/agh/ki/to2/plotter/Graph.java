package pl.edu.agh.ki.to2.plotter;

import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends JPanel{
	
	DefaultCategoryDataset dataset;
	ChartPanel pane;
	Border empty;
	
	
	public Graph(){
		setLayout(new GridLayout(1,1));
		empty = BorderFactory.createEmptyBorder(10,10,10,10);
		setBorder(empty); 
		setBackground(Color.white);
		
		dataset = new DefaultCategoryDataset();
		
		
		JFreeChart chart = ChartFactory.createBarChart("","","",dataset,PlotOrientation.HORIZONTAL, false, true, false);
		
		pane = new ChartPanel(chart);
		
		add(pane);
		
	}
	
	
	public void update(Map<SearchPattern,Occurrences> searches){
		dataset = null;
		SearchPattern key;
		Map<String, List<String>> map;
		//String url;
		List<String> list;
		int counter;
		for (Map.Entry<SearchPattern, Occurrences> entry : searches.entrySet()) {
			key = entry.getKey();
			map = entry.getValue().getMap();
			counter = 0;
			for (Map.Entry<String, List<String>> lowerEntry : map.entrySet()){
				//url = lowerEntry.getKey();
				list = lowerEntry.getValue();
				counter+=list.size();
			}
			dataset.addValue(counter, "" , key.getPattern());
		}
		//tab.repaint();
	}
	
	public void editValue (SearchPattern pattern, int value){
		
	}
	
}

