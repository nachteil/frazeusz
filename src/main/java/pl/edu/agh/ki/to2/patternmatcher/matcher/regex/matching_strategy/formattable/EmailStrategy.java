package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

public class EmailStrategy extends AbstractFormattableStrategy {

    private static String emailPattern = "" +
            "[A-Za-z0-9%!#$%&'*+\\-\\/=?^_`{|}~]+" +
            "(?:\\.[A-Za-z0-9%!#$%&'*+\\-\\/=?^_`{|}~]+)*" +
            "(?:@|(?:\\s?\\[?at\\]?\\s?))" +
            "(?:[A-Za-z0-9\\-]+\\.)+" +
            "[A-Za-z]{2,}";

    public EmailStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    protected String getRegex() {
        return emailPattern;
    }
}
