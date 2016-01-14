package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.Map;

public class EmailStrategy extends AbstractFormattableStrategy {

    private static String emailPattern = "" +
            "(?<name>[A-Za-z0-9%!#$%&'*+\\-/=?^_`{|}~]+" +
            "(?:\\.[A-Za-z0-9%!#$%&'*+\\-/=?^_`{|}~]+)*)" +
            "(?:@|(?:\\s?\\[?at\\]?\\s?))" +
            "(?<domain>(?:[A-Za-z0-9\\-]+\\.)+" +
            "[A-Za-z]{2,})";

    public EmailStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    protected String getFormatRegex() {
        return emailPattern;
    }

    @Override
    protected String[] getGroupNames() {
        return new String[] {"name", "domain"};
    }

    @Override
    protected String getReplaceRegex(Map<String, String> groupValues) {
        return String.format(
                "%s" +
                "(?:@|(?:\\s?\\[?at\\]?\\s?))" +
                "%s",
                groupValues.get("name"),
                groupValues.get("domain"));
    }
}
