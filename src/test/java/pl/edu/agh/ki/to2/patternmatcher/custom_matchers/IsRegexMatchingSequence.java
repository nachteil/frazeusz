package pl.edu.agh.ki.to2.patternmatcher.custom_matchers;

import org.hamcrest.*;
import org.hamcrest.Matcher;

import java.util.regex.*;

public class IsRegexMatchingSequence extends TypeSafeMatcher<Pattern> {
    private final CharSequence sequence;

    public IsRegexMatchingSequence(CharSequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void describeTo(Description description) {

    }

    @Override
    protected boolean matchesSafely(Pattern pattern) {
        java.util.regex.Matcher matcher = pattern.matcher(sequence);
        return matcher.matches();
    }

    @Factory
    public static Matcher<Pattern> matchesSequence(CharSequence sequence) {
        return new IsRegexMatchingSequence(sequence);
    }
}
