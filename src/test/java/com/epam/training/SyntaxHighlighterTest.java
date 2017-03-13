package com.epam.training;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SyntaxHighlighterTest {

	private SyntaxHighlighter syntaxHighlighter;
	
	@Before
	public void setup() {
		
	}
	
	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnNull() {
		String sentense = null;
		syntaxHighlighter.highlightThis(sentense);
	}
	
	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnBlank() {
		String sentense = "";
		syntaxHighlighter.highlightThis(sentense);
	}

	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
		String sentense = "     ";
		syntaxHighlighter.highlightThis(sentense);
	}
	
	@Test
	public void shouldApplyStyleOnThisSentense() {
		String sentense = "I am going to join java mentoring program to learn cool stuff in fun way.";
		String expectedHighlightedSentense = "I [bold] am [/bold] going [italic] to [/italic] join java mentoring program [italic] to [/italic] learn cool stuff [underline] in [/underline] fun way.";
		
		String highlightSentense = syntaxHighlighter.highlightThis(sentense);
		
		assertEquals(expectedHighlightedSentense, highlightSentense);
	}
	
	@Test
	public void shouldNotApplyStyleOnThisSentense() {
		String sentense = "I know everything, so it's waste of effort.";
		String expectedHighlightedSentense = "I know everything, so it's waste of effort.";
		
		String highlightSentense = syntaxHighlighter.highlightThis(sentense);
		
		assertEquals(expectedHighlightedSentense, highlightSentense);
	}
	
}
