package pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

public class SynonymStrategy extends AbstractMatchingStrategy {

    public SynonymStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    public String format(String pattern) {
        return null;
    }
}
