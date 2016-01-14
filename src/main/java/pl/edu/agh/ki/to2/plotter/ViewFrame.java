package pl.edu.agh.ki.to2.plotter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Sara on 2015-12-09.
 */
public class ViewFrame extends JFrame implements ActionListener{
    public final static int INTERVAL = 1000;
    Timer timer;
    Ploter ploter;

    private JPanel leftPane;
    private JButton export;

    Table tab;
    Graph graph;

    public ViewFrame(){
        setLayout(new GridLayout(1,2));
        leftPane = new JPanel();
        export = new JButton("Export to file");
        export.addActionListener(this);
        tab = new Table();
        graph = new Graph();

        Border empty =BorderFactory.createEmptyBorder(10,10,10,10);
        leftPane.setBorder(empty);
        leftPane.setLayout(new BorderLayout());
        leftPane.add(tab, BorderLayout.CENTER);
        leftPane.add(export, BorderLayout.PAGE_END);

        add(leftPane);
        add(graph);

        setSize(1000,1000);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Export");

    }

}