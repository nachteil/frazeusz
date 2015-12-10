package pl.edu.agh.ki.to2.plotter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Sara on 2015-12-09.
 */
public class ViewFrame extends JFrame{
    public final static int INTERVAL = 1000;
    Timer timer;
    Ploter ploter;

    Table tab;
    Graph graph;

    public ViewFrame(){
        setSize(1000,1000);
        setLayout(new GridLayout(1,2));
        tab = new Table();
        add(tab);
        graph = new Graph();
        add(graph);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        setVisible(true);
        refreshPanels();

    }

    private void refreshPanels(){
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                //Refresh the panel

                tab.update(ploter.getPatternOccurrencesHashMap());
                tab.revalidate();
                graph.update(ploter.getPatternOccurrencesHashMap());
                graph.revalidate();

                //System.out.println("1");

                //if (/* condition to terminate the thread. */) {
                //    timer.stop();
                //}
            }
        });

        timer.start();


    }

    public void setPloter(Ploter ploter) {
        this.ploter = ploter;
    }

}
