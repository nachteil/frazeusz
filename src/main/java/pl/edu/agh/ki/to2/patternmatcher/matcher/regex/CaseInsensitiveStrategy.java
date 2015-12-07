package main.java.pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import pl.edu.agh.to2.frazeusz.nlprocessor.IWordProvider;

import java.util.regex.Pattern;

public class CaseInsensitiveStrategy extends AbstractMatchingStrategy {

    public CaseInsensitiveStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    public Pattern compile(String pattern) {
        return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public int getFlags() {
        return Pattern.CASE_INSENSITIVE;
    }
}
