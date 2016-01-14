package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.flag;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.flag.CaseInsensitiveStrategy;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.mock;
import static pl.edu.agh.ki.to2.patternmatcher.custom_matchers.IsRegexMatchingSequence.matchesSequence;

public class CaseInsensitiveStrategyTest {

    String pattern;
    IWordProvider wordProvider;
    CaseInsensitiveStrategy strategy;

    @Before
    public void setUp() throws Exception {
        pattern = "Matching strategy under test";
        wordProvider = mock(IWordProvider.class);
        strategy = new CaseInsensitiveStrategy(wordProvider);
    }

    @Test
    public void testFormat() throws Exception {
        String formatted = strategy.format(pattern);
        assertThat(formatted, is(equalTo(pattern)));
    }

    @Test
    public void testCompile() throws Exception {
        Pattern p = strategy.compile(pattern);
        assertThat(p, matchesSequence("Matching strategy under test"));
        assertThat(p, matchesSequence("matching strategy under test"));
    }

    @Test
    public void testGetFlags() throws Exception {
        assertThat(strategy.getFlags(), is(Pattern.CASE_INSENSITIVE));
    }

    @Test
    public void testGetWords() throws Exception {
        Map<String, Set<String>> wordMap = strategy.getWords(pattern);

        for (String word : wordMap.keySet())
            assertThat(wordMap.get(word), hasSize(1));
    }
}