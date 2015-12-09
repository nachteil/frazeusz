package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class EmptyStrategyTest {

    static IWordProvider wordProvider;
    String pattern;
    EmptyStrategy strategy;

    @Before
    public void setUp() {
        wordProvider = mock(IWordProvider.class);
        pattern = "matching strategy under test";
        strategy = new EmptyStrategy(wordProvider);
    }

    @Test
    public void testFormat() throws Exception {
        assertThat(strategy.format(pattern), is(pattern));
    }

    @Test
    public void testCompile() throws Exception {
        Pattern p = strategy.compile(pattern);
        Matcher m = p.matcher(pattern);
        assertThat(m.matches(), is(true));
    }

    @Test
    public void testGetWords() throws Exception {
        Map<String, Set<String>> wordMap = strategy.getWords(pattern);

        for (String word : wordMap.keySet())
            assertThat(wordMap.get(word).size(), is(1));
    }

    @Test
    public void testGetFlags() throws Exception {
        assertThat(strategy.getFlags(), is(0));
    }
}