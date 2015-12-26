package pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.AltWordStrategyTest;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.flag.CaseInsensitiveStrategyTest;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable.EmailStrategyTest;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable.PhoneStrategyTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MatchingStrategyHelperTest.class,
        EmptyStrategyTest.class,
        CaseInsensitiveStrategyTest.class,
        AltWordStrategyTest.class,
        MultiStrategyTest.class,
        PhoneStrategyTest.class,
        EmailStrategyTest.class
})

public class StrategyTestSuite {
}
