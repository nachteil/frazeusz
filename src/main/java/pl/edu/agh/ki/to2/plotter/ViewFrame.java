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
    private boolean finishedSearching = false;

    private JPanel leftPane;
    private JButton export;
    private JFileChooser fileChooser;
    private FileFactory fileFactory;

    Table tab;
    Graph graph;

    public ViewFrame() {
        setLayout(new GridLayout(1, 2));
        this.setTitle("Plotter");
        fileFactory = new FileFactory();
        leftPane = new JPanel();
        export = new JButton("Export to file");
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toUpperCase().equals(".TXT");
            }

            @Override
            public String getDescription() {
                return ".txt files";
            }
        });
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toUpperCase().equals(".XML");
            }

            @Override
            public String getDescription() {
                return ".xml files";
            }
        });
        fileChooser.setAcceptAllFileFilterUsed(false);
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(ViewFrame.this) == JFileChooser.APPROVE_OPTION) {

                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    IFile file = fileFactory.getFile(fileChooser.getFileFilter().getDescription());
                    if (file != null) {
                        file.save(ploter.getPatternOccurrencesHashMap(), path);
                    }
                }
            }
        });
        tab = new Table();
        graph = new Graph();

        Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        leftPane.setBorder(empty);
        leftPane.setLayout(new BorderLayout());
        leftPane.add(tab, BorderLayout.CENTER);
        leftPane.add(export, BorderLayout.PAGE_END);

        add(leftPane);
        add(graph);

        setSize(1000, 500);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setVisible(true);
        refreshPanels();

    }

    private void refreshPanels() {
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                tab.update(ploter.getPatternOccurrencesHashMap());
                tab.revalidate();
                graph.update(ploter.getPatternOccurrencesHashMap());
                graph.revalidate();

                if (finishedSearching) {
                    timer.stop();
                }
            }
        });

        timer.start();


    }

    public void setPloter(Ploter ploter) {
        this.ploter = ploter;
    }

    public void setFinishedSearching(boolean finishedSearching) {
        this.finishedSearching = finishedSearching;
    }
}