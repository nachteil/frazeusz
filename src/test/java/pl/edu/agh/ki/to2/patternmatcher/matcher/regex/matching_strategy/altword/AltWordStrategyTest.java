package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.AltWordStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.DiminutiveStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.edu.agh.ki.to2.patternmatcher.custom_matchers.IsRegexMatchingSequence.matchesSequence;

public class AltWordStrategyTest {

    static IWordProvider wordProvider;
    static String[] alts = {"test", "check", "trial"};
    String pattern;
    AltWordStrategy strategy;

    @BeforeClass
    public static void setUpClass() {
        wordProvider = mock(IWordProvider.class);
        when(wordProvider.getDiminutives("test")).thenReturn(new HashSet<>(Arrays.asList(alts)));
    }

    @Before
    public void setUp() throws Exception {
        pattern = "matching strategy under test";
        strategy = new DiminutiveStrategy(wordProvider);
    }

    @Test
    public void testFormat() throws Exception {
        String formatted = strategy.format(pattern);
        assertThat(formatted, is(equalTo("matching strategy under (test|check|trial)")));
    }

    @Test
    public void testCompile() throws Exception {
        pattern = strategy.format(pattern);
        Pattern p = strategy.compile(pattern);

        for (String alt : alts) {
            assertThat(p, matchesSequence("matching strategy under " + alt));
        }
    }

    @Test
    public void testGetWords() throws Exception {
        Map<String, Set<String>> wordMap = strategy.getWords(pattern);
        assertThat(wordMap.keySet(), hasItems("matching", "strategy", "under", "test"));
        assertThat(wordMap.get("test"), hasItems(alts));
    }

    @Test
    public void testGetFlags() throws Exception {
        assertThat(strategy.getFlags(), is(0));
    }
}