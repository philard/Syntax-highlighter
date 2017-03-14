package com.epam.training;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SyntaxHighlighterTest {

	private SyntaxHighlighter syntaxHighlighter;
	
	@Before
	public void setup() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		syntaxHighlighter = (SyntaxHighlighter) context.getBean("syntaxHighlighter");
	}
	
	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnNull() {
		String sentence = null;
		syntaxHighlighter.highlightThis(sentence);
	}
	
	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnBlank() {
		String sentence = "";
		syntaxHighlighter.highlightThis(sentence);
	}

	@Test(expected=SyntaxHighlightingException.class)
	public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
		String sentence = "     ";
		syntaxHighlighter.highlightThis(sentence);
	}

	@Test
	public void shouldNotApplyStyleOnThisSentence() {
		String sentence = "I know everything, so it's waste of effort.";
		String expectedHighlightedSentence = "I know everything, so it's waste of effort.";

		String highlightSentence = syntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}

	@Test
	public void shouldApplyStyleOnThisSentence() {
		String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
		String expectedHighlightedSentence = "I [bold]am[/bold] going [italic]to[/italic] join java mentoring program [italic]to[/italic] learn cool stuff [underline]in[/underline] fun way.";

		String highlightSentence = syntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}

	@Test
	public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
		String sentence = "I am going in now.";
		String expectedHighlightedSentence = "I [bold]am[/bold] going [underline]in[/underline] now.";

		String highlightSentence = syntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}

	@Test
	public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
		String sentence = "I will go in.";
		String expectedHighlightedSentence = "I will go [underline]in[/underline].";

		String highlightSentence = syntaxHighlighter.highlightThis(sentence);

		assertEquals(expectedHighlightedSentence, highlightSentence);
	}
	
}
