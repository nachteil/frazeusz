package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MatchingStrategyHelperTest.class,
        EmptyStrategyTest.class,
        CaseInsensitiveStrategyTest.class,
        AltWordStrategyTest.class
})

public class StrategyTestSuite {
}
