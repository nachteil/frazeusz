package pl.edu.agh.ki.to2.patternmatcher.matcher.regex;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.CaseInsensitiveStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.IMatchingStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.MultiStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.SynonymStrategy;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegexMatcherTest {

    static IWordProvider wordProvider;
    SearchPattern pattern;
    RegexMatcher matcher;

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
    }

    @Test
    public void testMatch() throws Exception {
        String[] sentences = new String[] {
                "Matcher under test.",
                "matcher under test!",
                "matcher is under test",
                "matcher is not under test",
                "matcher on trial",
                "matcher test",
                "A matcher inside a test suite"
        };
        String[] matching = new String[] {
                "Matcher under test.",
                "matcher under test!",
                "matcher is under test",
                "matcher on trial",
                "matcher test",
                "A matcher inside a test suite"
        };

        List<String> matches = matcher.match(Arrays.asList(sentences));
        assertThat(matches, hasItems(matching));
        assertThat(matches, hasSize(6));
    }

    @Test
    public void testOnMatchCompleted() throws Exception {

    }

    @Test
    public void testAddListener() throws Exception {

    }
}