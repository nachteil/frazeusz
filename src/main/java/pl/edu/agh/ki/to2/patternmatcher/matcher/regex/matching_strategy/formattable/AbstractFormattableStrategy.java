package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.AbstractMatchingStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.MatchingStrategyHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractFormattableStrategy extends AbstractMatchingStrategy implements IFormattableStrategy {
    
    public AbstractFormattableStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }
    
    protected abstract String getRegex();
    
    private Pattern getPattern() {
        return Pattern.compile("" +
                "(?<=\\s|^)" +
                getRegex() +
                "(?=\\s|$)");
    }

    @Override
    public boolean containsFormattableText(String pattern) {
        Pattern p = getPattern();
        Matcher m = p.matcher(pattern);
        return m.find();
    }

    @Override
    public String format(String pattern) {
        String escaped = MatchingStrategyHelper.escape(getRegex());
        return pattern.replaceAll(getRegex(), escaped);
    }

    @Override
    public Map<String, Set<String>> getWords(String pattern) {
        Map<String, Set<String>> wordMap = new HashMap<>();
        Pattern p = getPattern();
        String escaped = MatchingStrategyHelper.escape(getRegex());

        Matcher m = p.matcher(pattern);
        while (m.find())
            wordMap.put(m.group(), Collections.singleton(escaped));

        return wordMap;
    }
}
