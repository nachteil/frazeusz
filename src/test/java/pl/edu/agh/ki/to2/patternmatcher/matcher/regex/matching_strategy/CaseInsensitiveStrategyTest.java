package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

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
        Matcher m = p.matcher("matching strategy under test");
        assertThat(m.matches(), is(true));
        m = p.matcher("Matching strategy under test");
        assertThat(m.matches(), is(true));
    }

    @Test
    public void testGetFlags() throws Exception {
        assertThat(strategy.getFlags(), is(Pattern.CASE_INSENSITIVE));
    }

    @Test
    public void testGetWords() throws Exception {
        Map<String, Set<String>> wordMap = strategy.getWords(pattern);

        for (String word : wordMap.keySet())
            assertThat(wordMap.get(word).size(), is(1));
    }
}