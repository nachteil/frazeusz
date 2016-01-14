package pl.edu.agh.ki.to2.patternmatcher.matcher;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.matcher.MatcherFactory.MatcherType;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.RegexMatcher;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.flag.CaseInsensitiveStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.DiminutiveStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.EmptyStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.MultiStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.SynonymStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.altword.VariantStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable.EmailStrategy;
import pl.edu.agh.ki.to2.patternmatcher.matcher.regex.matching_strategy.formattable.PhoneStrategy;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

public class MatcherFactoryTest {
	
	static IWordProvider wordProvider;
	MatcherFactory matcherFactory;
	SearchPattern searchPattern;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		wordProvider = mock(IWordProvider.class);
	}
	
//	C - CaseInsensitive
//	S - Synonym
//	V - Variant
//	D - Diminutive
	@Test
	public void testCFactory() {
		searchPattern = new SearchPattern("test", false, false, false, false);
		RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
		assertTrue(matcher.getMatchingStrategy() instanceof CaseInsensitiveStrategy);
	}
	
	@Test
	public void testSFactory() {
		searchPattern = new SearchPattern("test", true, true, false, false);
		RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
		assertTrue(matcher.getMatchingStrategy() instanceof SynonymStrategy);
	}
	
	@Test
	public void testVFactory() {
		searchPattern = new SearchPattern("test", true, false, true, false);
		RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
		assertTrue(matcher.getMatchingStrategy() instanceof VariantStrategy);
	}
	
	@Test
	public void testDFactory() {
		searchPattern = new SearchPattern("test", true, false, false, true);
		RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
		assertTrue(matcher.getMatchingStrategy() instanceof DiminutiveStrategy);
	}
	
	@Test
	public void testMultiStrategyFactory(){
		// Should include case insensitive strategy
		searchPattern = new SearchPattern("test", false, true, false, true);
		RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
		MultiStrategy ms = (MultiStrategy)(matcher.getMatchingStrategy());
		assertTrue(ms.getStrategies().size() == 3);
	}
	
	@Test
	public void testEmptyStrategyFactory() {
		searchPattern = new SearchPattern("test", true, false, false, false);
		RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
		assertTrue(matcher.getMatchingStrategy() instanceof EmptyStrategy);
	}

    @Test
    public void testPhoneStrategyFactory() {
        searchPattern = new SearchPattern("test * 607-275-794");
        RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
        assertThat(matcher.getMatchingStrategy(), is(instanceOf(PhoneStrategy.class)));
    }

    @Test
    public void testEmailStrategyFactory() {
        searchPattern = new SearchPattern("test * john.doe@gmail.com");
        RegexMatcher matcher = (RegexMatcher) MatcherFactory.getMatcher(MatcherType.REGEX, searchPattern, wordProvider);
        assertThat(matcher.getMatchingStrategy(), is(instanceOf(EmailStrategy.class)));
    }
}
