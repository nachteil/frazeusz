package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.*;
import java.util.regex.Pattern;

public class MultiStrategy extends AbstractMatchingStrategy {

    private List<IMatchingStrategy> strategies;

    public MultiStrategy(List<IMatchingStrategy> strategies, IWordProvider wordProvider) {
        super(wordProvider);
        this.strategies = strategies;
    }

    public List<IMatchingStrategy> getStrategies() {
        return strategies;
    }

    public void addStrategy(IMatchingStrategy strategy) {
        strategies.add(strategy);
    }

    public void removeStrategy(IMatchingStrategy strategy) {
        strategies.remove(strategy);
    }

    @Override
    public String format(String pattern) {
        Map<String, Set<String>> wordMap = getWords(pattern);

        for (String word : wordMap.keySet()) {
            if (wordMap.get(word).size() == 1) {
                pattern = pattern.replaceAll(word, wordMap.get(word).iterator().next());
            }
            else
                pattern = pattern.replaceAll(word, MatchingStrategyHelper.join(wordMap.get(word)));
        }

        return pattern;
    }

    @Override
    public Pattern compile(String pattern) {
        return Pattern.compile(pattern, getFlags());
    }

    @Override
    public Map<String, Set<String>> getWords(String pattern) {
        Map<String, Set<String>> wordMap = new HashMap<>();
        Map<String, Set<String>> tmpMap;
        Set<String> addWords;

        for (IMatchingStrategy strategy : strategies) {
            tmpMap = strategy.getWords(pattern);
            for (String word : tmpMap.keySet()) {
                addWords = tmpMap.get(word);

                if (wordMap.containsKey(word))
                    wordMap.get(word).addAll(addWords);
                else
                    wordMap.put(word, addWords);
            }
        }

        return wordMap;
    }

    @Override
    public int getFlags() {
        int flags = 0;

        for (IMatchingStrategy strategy : strategies)
            flags |= strategy.getFlags();

        return flags;
    }
}
