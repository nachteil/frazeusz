package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.flag;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.AbstractMatchingStrategy;

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
