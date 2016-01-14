package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchingStrategyHelper {

    private static Pattern wordPattern = Pattern.compile("(?<=\\s|^)[^\\s*]+(?=\\s|$)");

    public static String escapeOnce(String pattern) {
        return pattern.replace("\\", "\\\\");
    }
    public static String escape(String pattern) {
        return pattern.replace("\\", "\\\\\\\\");
    }

    public static Set<String> split(String pattern) {
        Set<String> matches = new HashSet<>();
        Matcher matcher = wordPattern.matcher(pattern);
        while (matcher.find())
            matches.add(matcher.group());

        return matches;
    }

    public static String join(Iterable<String> words) {
        return String.format("(%s)", String.join("|", words));
    }

    public static String createWordAltList(String pattern, Function<String, Set<String>> wordMapper) {
        Map<String, Set<String>> wordMap = createWordMap(pattern, wordMapper);

        for (String word : wordMap.keySet()) {
            if (wordMap.get(word).size() <= 1) continue;
            pattern = pattern.replaceAll(word, join(wordMap.get(word)));
        }

        return pattern;
    }

    public static Map<String, Set<String>> createWordMap(String pattern, Function<String, Set<String>> wordMapper) {
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
}
