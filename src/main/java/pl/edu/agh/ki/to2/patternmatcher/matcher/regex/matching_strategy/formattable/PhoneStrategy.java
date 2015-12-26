package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

public class PhoneStrategy extends AbstractFormattableStrategy {

    private static String phonePattern =
            "((?:\\+|00)\\d\\d\\u0020?)?" +
            "((\\d{3}(?<sep1>[ \\-]?)\\d{3}\\k<sep1>\\d{3})|" +
            "(\\d{2}[ \\-]\\d{3}(?<sep2>[ \\-]?)\\d{2}\\k<sep2>\\d{2}))";

    public PhoneStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    protected String getRegex() {
        return phonePattern;
    }
}
