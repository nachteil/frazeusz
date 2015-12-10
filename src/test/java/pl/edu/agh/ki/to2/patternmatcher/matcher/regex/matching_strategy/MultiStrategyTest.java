package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.*;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.edu.agh.ki.to2.patternmatcher.custom_matchers.IsRegexMatchingSequence.matchesSequence;

public class MultiStrategyTest {

    static IWordProvider wordProvider;
    String pattern;
    CaseInsensitiveStrategy caseInsensitiveStrategy;
    MultiStrategy strategy;

    @BeforeClass
    public static void setUpClass() {
        wordProvider = mock(IWordProvider.class);
        when(wordProvider.getSynonyms("a")).thenReturn(new HashSet<>(Collections.singletonList("e")));
        when(wordProvider.getVariants("b")).thenReturn(new HashSet<>(Collections.singletonList("ba")));
        when(wordProvider.getDiminutives("c")).thenReturn(new HashSet<>(Collections.singletonList("c.")));
    }

    @Before
    public void setUp() throws Exception {
        pattern = "a b c D";
        caseInsensitiveStrategy = new CaseInsensitiveStrategy(wordProvider);
        strategy = new MultiStrategy(new ArrayList<>(Arrays.asList(
                caseInsensitiveStrategy,
                new SynonymStrategy(wordProvider),
                new VariantStrategy(wordProvider),
                new DiminutiveStrategy(wordProvider)
        )), wordProvider);
    }

    @Test
    public void testFormat() throws Exception {
        pattern = strategy.format(pattern);
        assertThat(pattern, is(equalTo("(a|e) (b|ba) (c|c.) D")));
    }

    @Test
    public void testDefault() {
        pattern = strategy.format(pattern);
        assertThat(strategy.compile(pattern), matchesSequence("a b c D"));
    }

    @Test
    public void testSynonym() {
        pattern = strategy.format(pattern);
        assertThat(strategy.compile(pattern), matchesSequence("e b c D"));;
    }

    @Test
    public void testVariant() {
        pattern = strategy.format(pattern);
        assertThat(strategy.compile(pattern), matchesSequence("a ba c D"));;
    }

    @Test
    public void testDiminutive() {
        pattern = strategy.format(pattern);
        assertThat(strategy.compile(pattern), matchesSequence("a b c. D"));;
    }

    @Test
    public void testCase() {
        pattern = strategy.format(pattern);
        assertThat(strategy.compile(pattern), matchesSequence("a b c d"));;
    }

    @Test
    public void testGetWords() throws Exception {
        Map<String, Set<String>> wordMap = strategy.getWords(pattern);
        assertThat(wordMap.keySet(), hasSize(4));
        assertThat(wordMap.keySet(), hasItems("a", "b", "c", "D"));
        assertThat(wordMap.get("a"), hasItems("a", "e"));
        assertThat(wordMap.get("b"), hasItems("b", "ba"));
        assertThat(wordMap.get("c"), hasItems("c", "c."));
    }

    @Test
    public void testGetFlags() throws Exception {
        assertThat(strategy.getFlags(), is(Pattern.CASE_INSENSITIVE));
        strategy.removeStrategy(caseInsensitiveStrategy);
        assertThat(strategy.getFlags(), is(0));
    }
}