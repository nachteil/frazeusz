package pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

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
