package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.AltWordStrategy;

public class VariantStrategy extends AltWordStrategy {

    public VariantStrategy(IWordProvider wordProvider) {
        super(wordProvider);
        wordMapper = wordProvider::getVariants;
    }
}
