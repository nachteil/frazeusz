package pl.edu.agh.ki.to2.patternmatcher;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pl.edu.agh.ki.to2.patternmatcher.matcher.MatcherFactoryTest;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.RegexMatcherTest;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.StrategyTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegexMatcherTest.class,
        MatcherFactoryTest.class,
        PatternMatcherTest.class,
        StrategyTestSuite.class
})

public class PatternMatcherTestSuite {
}
