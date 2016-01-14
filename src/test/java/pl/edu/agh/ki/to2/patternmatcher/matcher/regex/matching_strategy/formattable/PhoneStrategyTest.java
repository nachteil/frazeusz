package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable.PhoneStrategy;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static pl.edu.agh.ki.to2.patternmatcher.custom_matchers.IsRegexMatchingSequence.matchesSequence;

public class PhoneStrategyTest {

    static IWordProvider wordProvider;
    static String[] validPhoneFormats = {
            "+48 607 275 794",
            "0048 607 275 794",
            "607-275-794",
            "607275794",
            "0048607275794",
            "+48 607275794",
            "32 623 98 20",
            "32 6239820",
            "32 623-98-20"
    };
    static String[] invalidPhoneFormats = {
            "1234567",
            "607-275 794",
            "32 62 398 20"
    };
    PhoneStrategy strategy;

    @BeforeClass
    public static void setUpClass() {
        wordProvider = mock(IWordProvider.class);
    }

    @Before
    public void setUp() throws Exception {
        strategy = new PhoneStrategy(wordProvider);
    }

    @Test
    public void testContainsFormattableText() throws Exception {
        for (String format : validPhoneFormats)
            assertThat(format + " should match", strategy.containsFormattableText("pattern * " + format), is(true));

        for (String format : invalidPhoneFormats)
            assertThat(format + " should not match", strategy.containsFormattableText("pattern * " + format), is(false));
    }

    @Test
    public void testFormat() {
        Pattern p = strategy.compile(strategy.format("32 623 98 20"));
        assertThat(p, matchesSequence("32 6239820"));
        assertThat(p, not(matchesSequence("32 623 12 32")));

        p = strategy.compile(strategy.format("703 402 503"));
        assertThat(p, matchesSequence("703 402 503"));
        assertThat(p, matchesSequence("703402503"));
        assertThat(p, matchesSequence("+48 703 402 503"));
        assertThat(p, matchesSequence("0048703402503"));
        assertThat(p, matchesSequence("703-402-503"));
        assertThat(p, not(matchesSequence("703 402503")));
        assertThat(p, not(matchesSequence("703 402 502")));
    }
}