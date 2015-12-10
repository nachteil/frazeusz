package pl.edu.agh.ki.to2.plotter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sara on 2015-12-09.
 */
public class ViewFrame extends JFrame{
    public final static int INTERVAL = 1000;
    Timer timer;
    Ploter ploter;

    Table tab = new Table();
    Graph graph = new Graph();

    public ViewFrame(){
        setLayout(new GridLayout(1,2));
        add(tab);
        add(graph);
    }

//    private void refreshPanels(){
//        timer = new Timer(INTERVAL, new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//
//                //Refresh the panel
//                tab.revalidate();
//                graph.revalidate();
//
//                //if (/* condition to terminate the thread. */) {
//                //    timer.stop();
//                //}
//            }
//        });
//
//        timer.start();
//
//
//    }

    public void setPloter(Ploter ploter) {
        this.ploter = ploter;
    }

}
