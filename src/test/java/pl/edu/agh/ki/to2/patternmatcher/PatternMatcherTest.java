package pl.edu.agh.ki.to2.patternmatcher;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;

public class PatternMatcherTest {
	
	PatternMatcher patternMatcher;
	static MonitorPubSub monitor;
	static IWordProvider wordProvider;
	List<SearchPattern> searchPatterns;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		wordProvider = mock(IWordProvider.class);
		monitor = mock(MonitorPubSub.class);
	}

	@Before
	public void setUp() throws Exception {
		patternMatcher = new PatternMatcher(wordProvider, monitor);
		searchPatterns = new ArrayList<>();
		searchPatterns.add(new SearchPattern("test"));
		searchPatterns.add(new SearchPattern("test1"));
	}

	@Test
	public void testMatch() {
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("test1");
		sentences.add("test test1");
		sentences.add("abc");
		sentences.add("def");
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(3));
		assertTrue(results.contains("test"));
		assertTrue(results.contains("test1"));
		assertTrue(results.contains("test test1"));
	}

}
