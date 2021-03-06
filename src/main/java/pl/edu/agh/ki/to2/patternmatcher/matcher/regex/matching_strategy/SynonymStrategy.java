package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

public class SynonymStrategy extends AltWordStrategy {

    public SynonymStrategy(IWordProvider wordProvider) {
        super(wordProvider);
        wordMapper = wordProvider::getSynonyms;
    }
}
