package pl.edu.agh.ki.to2.plotter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by Sara on 2015-12-09.
 */
public class ViewFrame extends JFrame {
    public final static int INTERVAL = 1000;
    Timer timer;
    Ploter ploter;
    
    private JPanel leftPane;
    private JButton export;
    private JFileChooser fileChooser;
    private FileFactory fileFactory;


    Table tab;
    Graph graph;

    public ViewFrame(){
        setLayout(new GridLayout(1,2));
    	leftPane = new JPanel();
    	export = new JButton("Export to file");
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter(){
            @Override
            public boolean accept(File file) {
                return file.getName().toUpperCase().equals(".TXT");
            }

            @Override
            public String getDescription() {
                return ".txt files";
            }
        });
        fileChooser.addChoosableFileFilter(new FileFilter(){
            @Override
            public boolean accept(File file) {
                return file.getName().toUpperCase().equals(".XML");
            }

            @Override
            public String getDescription() {
                return ".xml files";
            }
        });
    	export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export");
                if(fileChooser.showSaveDialog(ViewFrame.this) == JFileChooser.APPROVE_OPTION){
                    //System.out.println(fileChooser.getSelectedFile().getName(););
                    //System.out.println(fileChooser.getFileFilter().getDescription());

                    IFile file = fileFactory.getFile(fileChooser.getFileFilter().getDescription());
                    if (file != null) {
                        //tu w metodzie getPatternOccurencesHashMap coś jest do zmiany, bo jak
                        // uruchamiałam bez wstawienia czegokolwiek do hashmapy, to rzuca nullpointerami
                        file.save(ploter.getPatternOccurrencesHashMap());
                    }
                }
            }
        });
    	tab = new Table();
    	graph = new Graph();
    	
    	Border empty =BorderFactory.createEmptyBorder(10,10,10,10);
    	leftPane.setBorder(empty);
    	leftPane.setLayout(new BorderLayout());
    	leftPane.add(tab, BorderLayout.CENTER);
    	leftPane.add(export, BorderLayout.PAGE_END);
    	
    	add(leftPane);
    	add(graph);
    	
        setSize(1000,500);

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
