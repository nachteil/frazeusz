package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.AbstractMatchingStrategy;

import java.util.Map;
import java.util.Set;

public class SynonymStrategy extends AbstractMatchingStrategy {

    public SynonymStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    public String format(String pattern) {

        return this.createWordAltList(pattern, word -> wordProvider.getSynonyms(word));
    }

    @Override
    public Map<String, Set<String>> getWords(String pattern) {

        return this.createWordMap(pattern, word -> wordProvider.getSynonyms(word));
    }
}
