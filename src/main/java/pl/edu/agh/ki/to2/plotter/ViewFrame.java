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
    Table table = new Table();
    Graph graph = new Graph();

    public ViewFrame(Ploter ploter) throws HeadlessException {
        this.ploter = ploter;
    }

    private void refreshPanels(){
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                //Refresh the panel
                table.revalidate();
                graph.revalidate();

                //if (/* condition to terminate the thread. */) {
                //    timer.stop();
                //}
            }
        });

        timer.start();


    }

}
