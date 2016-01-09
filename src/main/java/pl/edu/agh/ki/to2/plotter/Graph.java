package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Paint;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends JPanel{
	
	DefaultCategoryDataset dataset;
	ChartPanel pane;
	Border empty;
	BarRenderer renderer;
	CategoryPlot plot;
	Paint color = new Color(0, 172, 178);
	
	
	public Graph(){
		setLayout(new GridLayout(1,1));
		empty = BorderFactory.createEmptyBorder(10,10,10,10);
		setBorder(empty); 
		setBackground(Color.white);
		
		dataset = new DefaultCategoryDataset();
		
		
		JFreeChart chart = ChartFactory.createBarChart("","","",dataset,PlotOrientation.HORIZONTAL, false, true, false);
		
		
		plot = chart.getCategoryPlot();
		
        plot.setNoDataMessage("No matches found");
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(false);
		
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
		numberAxis.setVisible(false);
		//numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
	
		renderer = (BarRenderer) plot.getRenderer();
		
		renderer.setGradientPaintTransformer(null);   
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setMaximumBarWidth(.35);
				
		renderer.setSeriesItemLabelGenerator(0,new StandardCategoryItemLabelGenerator()); 
		renderer.setSeriesItemLabelsVisible(0, true);
		
		
		//plot.setRenderer(barRenderer);
        //CategoryItemRenderer
		
		
		
		pane = new ChartPanel(chart);
		
		add(pane);
		
		
	}
	
	
	public void update(Map<SearchPattern,Occurrences> searches){
		synchronized (searches) {
			dataset.clear();
			SearchPattern key;
			Map<String, List<String>> map;
			//String url;
			List<String> list;
			int counter;
			for (Map.Entry<SearchPattern, Occurrences> entry : searches.entrySet()) {
				key = entry.getKey();
				map = entry.getValue().getUrlSentenceMap();
				counter = 0;
				for (Map.Entry<String, List<String>> lowerEntry : map.entrySet()) {
					//url = lowerEntry.getKey();
					list = lowerEntry.getValue();
					counter += list.size();
				}
				dataset.addValue(counter, "", key.getPattern());
			}
			renderer.setSeriesPaint(0, color);
		}

	}
	
	
}
