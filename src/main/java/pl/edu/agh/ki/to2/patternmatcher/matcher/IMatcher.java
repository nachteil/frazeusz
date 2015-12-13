package pl.edu.agh.ki.to2.patternmatcher.matcher;

import java.util.List;

public interface IMatcher {
    List<String> match(List<String> sentences);
}
