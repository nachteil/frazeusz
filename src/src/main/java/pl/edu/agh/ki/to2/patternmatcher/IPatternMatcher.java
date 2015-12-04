package src.main.java.pl.edu.agh.ki.to2.patternmatcher;

import src.main.java.pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import javax.swing.*;
import java.util.List;

public interface IPatternMatcher extends IMatchProvider {

    List<String> match(List<String> sentences, String url);
    void setWordProvider(IWordProvider wordProvider);

    JPanel getView();

}
