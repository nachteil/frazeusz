package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class MatchingStrategyHelperTest {

    String pattern = "matching *** strategy ** under * test";
    String[] words = {"test", "check", "trial"};

    @Test
    public void testSplit() throws Exception {
        Set<String> words = MatchingStrategyHelper.split(pattern);
        assertThat(words.size(), is(4));
        assertThat(words, hasItems("matching", "strategy", "under", "test"));
    }

    @Test
    public void testJoin() throws Exception {
        String wordAlt = MatchingStrategyHelper.join(Arrays.asList(words));
        Pattern pattern = Pattern.compile(wordAlt);
        Matcher matcher = pattern.matcher("test check trial failure");
        int matches = 0;
        while (matcher.find())
            matches++;

        assertThat(matches, is(3));
    }
}