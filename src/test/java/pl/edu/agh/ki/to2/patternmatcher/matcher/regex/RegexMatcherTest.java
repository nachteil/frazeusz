package pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.IMatchListener;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.flag.CaseInsensitiveStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.IMatchingStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.MultiStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.SynonymStrategy;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class RegexMatcherTest {

    static IWordProvider wordProvider;
    SearchPattern pattern;
    RegexMatcher matcher;
    IMatchListener listener;

    @BeforeClass
    public static void setUpClass() {
        wordProvider = mock(IWordProvider.class);
        when(wordProvider.getSynonyms("test")).thenReturn(new HashSet<>(Collections.singletonList("trial")));
    }

    @Before
    public void setUp() throws Exception {
        pattern = new SearchPattern("Matcher ** test", true, true, false, false);
        IMatchingStrategy strategy = new MultiStrategy(Arrays.asList(
                new CaseInsensitiveStrategy(wordProvider),
                new SynonymStrategy(wordProvider)),
            wordProvider);
        matcher = new RegexMatcher(pattern, strategy);

        listener = mock(IMatchListener.class);
    }

    @Test
    public void testDefault() {
        assertThat(matcher.match(Collections.singletonList("Matcher test!")), is(not(empty())));
    }

    @Test
    public void testCase() {
        assertThat(matcher.match(Collections.singletonList("matcher test")), is(not(empty())));
    }

    @Test
    public void testStarGroup() {
        assertThat(matcher.match(Collections.singletonList("matcher under test")), is(not(empty())));
        assertThat(matcher.match(Collections.singletonList("matcher is under test")), is(not(empty())));
        assertThat(matcher.match(Collections.singletonList("matcher is not under test")), is(empty()));
    }

    @Test
    public void testAltWords() {
        assertThat(matcher.match(Collections.singletonList("matcher on trial")), is(not(empty())));
    }

    @Test
    public void testSentence() {
        assertThat(matcher.match(Collections.singletonList("A matcher inside a test sentence.")), is(not(empty())));
    }

    @Test
    public void testOnMatchCompleted() throws Exception {
        matcher.addListener(listener);
        matcher.match(Collections.singletonList("matcher under test"));
        verify(listener, times(1)).addMatches(eq(pattern), eq(Collections.singletonList("matcher under test")), anyString());
    }
}