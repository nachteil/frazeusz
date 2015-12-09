package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class AltWordStrategy extends AbstractMatchingStrategy {

    protected Function<String, Set<String>> wordMapper;

    public AltWordStrategy(IWordProvider wordProvider) {
        super(wordProvider);
        wordMapper = word -> new HashSet<>();
    }

    @Override
    public String format(String pattern) {

        return MatchingStrategyHelper.createWordAltList(pattern, wordMapper);
    }

    @Override
    public Map<String, Set<String>> getWords(String pattern) {

        return MatchingStrategyHelper.createWordMap(pattern, wordMapper);
    }
}
