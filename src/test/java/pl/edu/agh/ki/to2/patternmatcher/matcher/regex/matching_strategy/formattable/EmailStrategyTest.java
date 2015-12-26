package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class EmailStrategyTest {

    static IWordProvider wordProvider;
    static String[] validEmailFormats = {
            "john.doe@gmail.com",
            "john[at]gmail.com",
            "john [at] gmail.com",
            "john at gmail.com",
            "prettyandsimple@example.com",
            "very.common@example.com",
            "disposable.style.email.with+symbol@example.com",
            "other.email-with-dash@example.com",
            "#!$%&'*+-/=?^_`{}|~@example.org"
    };
    static String[] invalidEmailFormats = {
            "abc.example.com",
            "a@b@c@example.com",
            "john..doe@gmail.com",
            "john.doe@gmail..com"
    };
    EmailStrategy strategy;

    @BeforeClass
    public static void setUpClass() {
        wordProvider = mock(IWordProvider.class);
    }

    @Before
    public void setUp() throws Exception {
        strategy = new EmailStrategy(wordProvider);
    }

    @Test
    public void testContainsFormattableText() throws Exception {
        for (String format : validEmailFormats)
            assertThat(format + " should match", strategy.containsFormattableText("pattern * " + format), is(true));

        for (String format: invalidEmailFormats)
            assertThat(format + " should not match", strategy.containsFormattableText("pattern * " + format), is(false));
    }
}