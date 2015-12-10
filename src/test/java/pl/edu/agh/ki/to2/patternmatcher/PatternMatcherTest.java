package pl.edu.agh.ki.to2.patternmatcher;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;
import pl.edu.agh.ki.to2.monitor.contract.MonitorPubSub;
import pl.edu.agh.ki.to2.nlprocessor.IWordProvider;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.patternmatcher.ui.controllers.PatternController;

public class PatternMatcherTest {
	
	PatternMatcher patternMatcher;
	PatternController controller;
	static MonitorPubSub monitor;
	static IWordProvider wordProvider;
	List<SearchPattern> searchPatterns;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		wordProvider = mock(IWordProvider.class);
		monitor = mock(MonitorPubSub.class);
		when(wordProvider.getSynonyms("test")).thenReturn(new HashSet<>(Collections.singletonList("trial")));
		when(wordProvider.getSynonyms("Test")).thenReturn(new HashSet<>(Collections.singletonList("trial")));
		when(wordProvider.getDiminutives("test")).thenReturn(new HashSet<>(Collections.singletonList("tescik")));
		when(wordProvider.getVariants("test")).thenReturn(new HashSet<>(Collections.singletonList("testing")));
	}

	@Before
	public void setUp() throws Exception {
		patternMatcher = new PatternMatcher(wordProvider, monitor);
		searchPatterns = new ArrayList<>();
	}
	
	@Test
	public void testSinglePatternMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("test1");
		sentences.add("test test1");
		sentences.add("abc");
		sentences.add("def");
		searchPatterns.add(new SearchPattern("test"));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(2));
		assertTrue(results.contains("test"));
		assertTrue(results.contains("test test1"));
	}
	
	@Test
	public void testFreeWordMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("test1");
		sentences.add("test test1");
		sentences.add("test abc test1");
		sentences.add("def test ghi test1");
		searchPatterns.add(new SearchPattern("test * test1"));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
//		System.out.print(results.toString());
		assertThat(results, hasSize(2));
		assertTrue(results.contains("test abc test1"));
		assertTrue(results.contains("def test ghi test1"));
	}
	
	@Test 
	public void testSynonymMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("abc test");
		sentences.add("abc trial");
		sentences.add("something else");
		sentences.add("test1 abc");
		sentences.add("trial something");
		searchPatterns.add(new SearchPattern("test", true, true, false, false));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(3));
		assertTrue(results.contains("abc test"));
		assertTrue(results.contains("abc trial"));
		assertTrue(results.contains("trial something"));
	}
	
	@Test 
	public void testCaseSensitiveMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("TEST");
		sentences.add("Test");
		sentences.add("tesT");
		searchPatterns.add(new SearchPattern("test", false, false, false, false));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(4));
	}
	
	@Test 
	public void testDiminutiveMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("tescik");
		sentences.add("test tescik");
		sentences.add("tester");
		searchPatterns.add(new SearchPattern("test", true, false, false , true));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(3));
		assertFalse(results.contains("tester"));
	}
	
	@Test 
	public void testVariantMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("testing");
		sentences.add("test tescik");
		sentences.add("tester");
		searchPatterns.add(new SearchPattern("test", true, false, true , false));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(3));
		assertFalse(results.contains("tester"));
	}
	
	@Test 
	public void testCaseInsensitiveMatch(){
		List<String> sentences = new ArrayList<>();
		sentences.add("test");
		sentences.add("Test");
		sentences.add("tesT");
		searchPatterns.add(new SearchPattern("test", true, false, false , false));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> results = patternMatcher.match(sentences, "url");
		assertThat(results, hasSize(1));
		assertTrue(results.contains("test"));
	}
	
	@Test
	public void testMulitPatternMatch() {
		searchPatterns.add(new SearchPattern("Test something", false, true, false, false));
		searchPatterns.add(new SearchPattern("Dont test", true, false, true, true));
		patternMatcher.setPatterns(searchPatterns);
		
		List<String> sentences = new ArrayList<>();
		sentences.add("Test something");
		sentences.add("test something");
		sentences.add("Trial something");
		sentences.add("Dont test something");
		sentences.add("Dont trial something");
		sentences.add("dont test");
		sentences.add("Dont tescik");
		sentences.add("Dont testing");
		
		
		List<String> results = patternMatcher.match(sentences, "url");
		System.out.print(results.toString());
		assertThat(results, hasSize(7));
		assertFalse(results.contains("dont test"));
	}
}
