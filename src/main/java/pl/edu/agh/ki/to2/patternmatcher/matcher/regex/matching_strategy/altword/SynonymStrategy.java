package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.AltWordStrategy;

public class SynonymStrategy extends AltWordStrategy {

    public SynonymStrategy(IWordProvider wordProvider) {
        super(wordProvider);
        wordMapper = wordProvider::getSynonyms;
    }
}
