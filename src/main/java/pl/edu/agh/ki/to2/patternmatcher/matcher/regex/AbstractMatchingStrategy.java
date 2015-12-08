package pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractMatchingStrategy implements IMatchingStrategy {

    protected Pattern wordPattern = Pattern.compile("(?<=\\s|^)[^\\s*]+(?=\\s|$)");

    protected IWordProvider wordProvider;

    public AbstractMatchingStrategy(IWordProvider wordProvider) {
        this.wordProvider = wordProvider;
    }

    protected Set<String> split(String pattern) {
        Set<String> matches = new HashSet<>();
        Matcher matcher = wordPattern.matcher(pattern);
        while (matcher.find())
            matches.add(matcher.group());

        return matches;
    }

    protected String join(Iterable<String> words) {
        return String.format("(%s)", String.join("|", words));
    }

    protected String createWordAltList(String pattern, Function<String, Set<String>> wordMapper) {
        Map<String, Set<String>> wordMap = this.createWordMap(pattern, wordMapper);

        for (String word : wordMap.keySet())
            pattern = pattern.replaceAll(word, join(wordMap.get(word)));

        return pattern;
    }

    protected Map<String, Set<String>> createWordMap(String pattern, Function<String, Set<String>> wordMapper) {
        Set<String> words = split(pattern);
        Set<String> addWords;
        Map<String, Set<String>> wordMap = new HashMap<>();

        for (String word : words) {
            addWords = wordMapper.apply(word);
            addWords.add(word);
            wordMap.put(word, addWords);
        }

        return wordMap;
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
