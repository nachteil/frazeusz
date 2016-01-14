package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.AbstractMatchingStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.MatchingStrategyHelper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractFormattableStrategy extends AbstractMatchingStrategy implements IFormattableStrategy {
    
    public AbstractFormattableStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }
    
    protected abstract String getFormatRegex();
    protected abstract String[] getGroupNames();
    protected abstract String getReplaceRegex(Map<String, String> groupValues);
    
    private Pattern getFormatPattern() {
        return Pattern.compile("" +
                "(?<=\\s|^)" +
                getFormatRegex() +
                "(?=\\s|$)");
    }

    @Override
    public boolean containsFormattableText(String pattern) {
        Pattern p = getFormatPattern();
        Matcher m = p.matcher(pattern);
        return m.find();
    }

    @Override
    public String format(String pattern) {
        Map<String, Set<String>> wordMap = getWords(pattern);

        for (String word : wordMap.keySet()) {
            for (String s : wordMap.get(word))
                pattern = pattern.replaceAll(word, s);
        }

        return pattern;
    }

    @Override
    public Map<String, Set<String>> getWords(String pattern) {
        Map<String, Set<String>> wordMap = new HashMap<>();
        Pattern p = getFormatPattern();

        Matcher m = p.matcher(pattern);
        while (m.find()) {
            Map<String, String> groupValues = new HashMap<>(getGroupNames().length);
            for (String group : getGroupNames()) {
                groupValues.put(group, m.group(group));
            }
            String replace = getReplaceRegex(groupValues);
            String escaped = MatchingStrategyHelper.escapeOnce(replace);
            wordMap.put(m.group(), Collections.singleton(escaped));
        }

        return wordMap;
    }
}
