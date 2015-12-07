package main.java.pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface IMatchingStrategy {
    String format(String pattern);
    Pattern compile(String pattern);
    Map<String, Set<String>> getWords(String pattern);
    int getFlags();
}