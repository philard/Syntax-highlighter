package com.epam.training.sentence.impl;

import static org.junit.Assert.assertEquals;

import com.epam.training.context.AppConfig;
import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.ISyntaxHighlighter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * ISyntaxHighlighterImpl is now composed of services. This is an integration test class to test it.
 */
public class ISyntaxHighlighterTest {

	private ISyntaxHighlighter ISyntaxHighlighter;
	
	@Before
	public void setup() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ISyntaxHighlighter = (ISyntaxHighlighter) context.getBean("ISyntaxHighlighter");
	}
	
	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnNull() {
		ISyntaxHighlighter.highlightThis(null);
	}
	
	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnBlank() {
		String sentence = "";
		ISyntaxHighlighter.highlightThis(sentence);
	}

	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
		String sentence = "     ";
		ISyntaxHighlighter.highlightThis(sentence);
	}

	@Test
	public void shouldNotApplyStyleOnThisSentence() {
		String sentence = "I know everything, so it's waste of effort.";
		String expectedHighlightedSentence = "I know everything, so it's waste of effort.";

		String highlightSentence = ISyntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}

	@Test
	public void shouldApplyStyleOnThisSentence() {
		String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
		String expectedHighlightedSentence = "I [bold]am[/bold] going [italic]to[/italic] join java mentoring program [italic]to[/italic] learn cool stuff [underline]in[/underline] fun way.";

		String highlightSentence = ISyntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}

	@Test
	public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
		String sentence = "I am going in now.";
		String expectedHighlightedSentence = "I [bold]am[/bold] going [underline]in[/underline] now.";

		String highlightSentence = ISyntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}

	@Test
	public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
		String sentence = "I will go in.";
		String expectedHighlightedSentence = "I will go [underline]in[/underline].";

		String highlightSentence = ISyntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}
	
}
