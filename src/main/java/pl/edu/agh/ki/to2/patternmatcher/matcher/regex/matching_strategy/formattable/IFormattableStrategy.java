package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.IMatchingStrategy;

public interface IFormattableStrategy extends IMatchingStrategy {

    boolean containsFormattableText(String pattern);
}
