package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractMatchingStrategy implements IMatchingStrategy {

    protected IWordProvider wordProvider;

    public AbstractMatchingStrategy(IWordProvider wordProvider) {
        this.wordProvider = wordProvider;
    }

    @Override
    public String format(String pattern) {
        return pattern;
    }

    @Override
    public Pattern compile(String pattern) {
        return Pattern.compile(pattern);
    }

    @Override
    public Map<String, Set<String>> getWords(String pattern) {
        return new HashMap<>();
    }

    @Override
    public int getFlags() {
        return 0;
    }
}
