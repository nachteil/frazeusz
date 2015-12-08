package pl.edu.agh.ki.to2.patternmatcher;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.patternmatcher.matcher.IMatcher;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.EmptyStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.RegexMatcher;
import pl.edu.agh.ki.to2.patternmatcher.ui.controllers.PatternController;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static void createGUI() {
        JFrame frame = new JFrame("PatternUI");
        PatternController patternController = new PatternController(new ArrayList<>());
        patternController.init();
        frame.setContentPane(patternController.getView());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        createGUI();

        String[] patterns = {
                "to",
                "to **",
                "to **** and",
                "to **** and ** the",
                "** to **** and **** the ",
                "** to **** and **** the *** is *** a"
        };

        try {
            FileInputStream input = new FileInputStream("pg4351.txt");
            byte[] fileData = new byte[input.available()];

            input.read(fileData);
            input.close();

            String text = new String(fileData, "UTF-8");

            String[] sentences = text.split("[\\.?!]+");

            for (String pattern : patterns) {
                System.out.println(pattern);
                IMatcher matcher = new RegexMatcher(new SearchPattern(pattern), new EmptyStrategy(new MockWordProvider()));

                double start = System.currentTimeMillis();

                List<String> matched = matcher.match(Arrays.asList(sentences));

                System.out.println("Total sentences: " + sentences.length);
                System.out.println("Matched sentences: " + matched.size());
                System.out.println("Took " + (System.currentTimeMillis() - start));

                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
