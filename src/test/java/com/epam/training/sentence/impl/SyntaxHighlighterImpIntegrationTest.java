package com.epam.training.sentence.impl;

import com.epam.training.context.AppConfig;
import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.word.WordHighlighter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * SyntaxHighlighterImp is now composed of services. This is an integration test class to test it.
 */
public class SyntaxHighlighterImpIntegrationTest {

    private SyntaxHighlighterImp amToInWordsSyntaxHighlighterImp;

    private SyntaxHighlighterImp noWordsSyntaxHighlighterImp;

    @Before
    public void setupAmToInWordsSyntaxHighlighterImp() {
        AbstractApplicationContext amToInContext = new AnnotationConfigApplicationContext(AppConfig.class);
        amToInWordsSyntaxHighlighterImp = (SyntaxHighlighterImp) amToInContext.getBean("syntaxHighlighter");


        AbstractApplicationContext noWordsContext = new AnnotationConfigApplicationContext(AppConfig.class);
        noWordsSyntaxHighlighterImp = (SyntaxHighlighterImp) noWordsContext.getBean("syntaxHighlighter");
        noWordsSyntaxHighlighterImp.setWordHighlighters(new ArrayList<WordHighlighter>());
    }


    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        amToInWordsSyntaxHighlighterImp.highlightThis(null);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        amToInWordsSyntaxHighlighterImp.highlightThis(sentence);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        amToInWordsSyntaxHighlighterImp.highlightThis(sentence);
    }

    @Test
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expectedHighlightedSentence = "I know everything, so it's waste of effort.";
        String highlightSentence = amToInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnThisSentence() {
        String sentence = "I am going to join,java mentoring program to learn cool stuff in fun way.";
        String expected = "I [bold] am [/bold] going [italic] to [/italic] join,java mentoring program " +
                "[italic] to [/italic] learn cool stuff [underline] in [/underline] fun way.";
        String highlightSentence = amToInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I am going in now.";
        String expected = "I [bold] am [/bold] going [underline] in [/underline] now.";
        String highlightSentence = amToInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expected = "I will go [underline] in [/underline].";
        String highlightSentence = amToInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldNotApplyStylesWhenNoWordsHighlighterAndThisSentence() {
        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        String expectedHighlightedSentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        String highlightSentence = noWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedWhenNoWordsHighlighterAndWhiteSpaces() {
        String sentence = "     ";
        noWordsSyntaxHighlighterImp.highlightThis(sentence);
    }

}
