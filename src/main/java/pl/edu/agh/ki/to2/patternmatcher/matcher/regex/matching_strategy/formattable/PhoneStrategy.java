package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.Map;

public class PhoneStrategy extends AbstractFormattableStrategy {

    private static String phonePattern =
            "(?:(?:\\+|00)(?<country>\\d\\d)\\u0020?)?" +
            "(?:(?<num1>\\d{3})" +
            "(?<sep1>[ \\-]?)" +
            "(?<num2>\\d{3})" +
            "\\k<sep1>" +
            "(?<num3>\\d{3}))|" +
            "(?:(?<area>\\d{2})" +
            "[ \\-]" +
            "(?<anum1>\\d{3})" +
            "(?<sep2>[ \\-]?)" +
            "(?<anum2>\\d{2})" +
            "\\k<sep2>" +
            "(?<anum3>\\d{2}))";

    public PhoneStrategy(IWordProvider wordProvider) {
        super(wordProvider);
    }

    @Override
    protected String getFormatRegex() {
        return phonePattern;
    }

    @Override
    protected String[] getGroupNames() {
        return new String[] { "country", "area", "num1", "num2", "num3", "anum1", "anum2", "anum3" };
    }

    @Override
    protected String getReplaceRegex(Map<String, String> groupValues) {
        if (groupValues.get("area") != null) {
            return String.format(
                    "%s" +
                    "[ \\-]" +
                    "%s" +
                    "(?<sep2>[ \\-]?)" +
                    "%s" +
                    "\\k<sep2>" +
                    "%s",
                    groupValues.get("area"),
                    groupValues.get("anum1"),
                    groupValues.get("anum2"),
                    groupValues.get("anum3"));
        }
        else {
            return String.format(
                    "(?:(?:\\+|00)" +
                    "%s" +
                    "\\u0020?)?" +
                    "%s" +
                    "(?<sep1>[ \\-]?)" +
                    "%s" +
                    "\\k<sep1>" +
                    "%s",
                    groupValues.get("country") != null ? groupValues.get("country") : "48",
                    groupValues.get("num1"),
                    groupValues.get("num2"),
                    groupValues.get("num3")
            );
        }
    }
}
