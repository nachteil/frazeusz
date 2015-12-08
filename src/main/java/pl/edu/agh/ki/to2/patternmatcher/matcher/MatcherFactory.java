package pl.edu.agh.ki.to2.patternmatcher.matcher;

import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.*;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.*;

import java.util.ArrayList;
import java.util.List;

public class MatcherFactory {

    public static IMatcher getMatcher(SearchPattern pattern, IWordProvider wordProvider) {

        IMatchingStrategy strategy;

        List<IMatchingStrategy> strategies = new ArrayList<>(4);
        if (pattern.getSynonyms())
            strategies.add(new SynonymStrategy(wordProvider));
        if (pattern.getVariants())
            strategies.add(new VariantStrategy(wordProvider));
        if (pattern.getDiminutives())
            strategies.add(new DiminutiveStrategy(wordProvider));
        if (!pattern.getCaseSensitive())
            strategies.add(new CaseInsensitiveStrategy(wordProvider));

        if (strategies.size() == 0)
            strategy = new EmptyStrategy(wordProvider);
        else if (strategies.size() == 1)
            strategy = strategies.get(0);
        else
            strategy = new MultiStrategy(strategies, wordProvider);

        return new RegexMatcher(pattern, strategy);
    }
}
