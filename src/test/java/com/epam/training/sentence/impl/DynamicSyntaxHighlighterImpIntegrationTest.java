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
 * DynamicSyntaxHighlighter is configured with services and configured at invocation by a collection of 
 * Keyword-Effect pairs. 
 * 
 */
public class DynamicSyntaxHighlighterImpIntegrationTest {

    private SyntaxHighlighterImp dynamicSyntaxHighlighter;

    private SyntaxHighlighterImp noWordsSyntaxHighlighterImp;

    @Before
    public void setupAmToInWordsSyntaxHighlighterImp() {
        AbstractApplicationContext amToInContext = new AnnotationConfigApplicationContext(AppConfig.class);
        dynamicSyntaxHighlighter = (SyntaxHighlighterImp) amToInContext.getBean("dynamicSyntaxHighlighter");
        dynamicSyntaxHighlighter.setHighlightConfig(buildHighlightConfig());

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        noWordsSyntaxHighlighterImp = (SyntaxHighlighterImp) context.getBean("syntaxHighlighterWithColor");
        noWordsSyntaxHighlighterImp.setWordHighlighters(new ArrayList<WordHighlighter>());
    }

    private Collection<KeywordEffectPair> buildHighlightConfig() {
        ArrayList<KeywordEffectPair> pairs = new ArrayList<>();
        pairs.add(new KeywordEffectPair("am", "bold"));
        pairs.add(new KeywordEffectPair("to", "italic"));
        pairs.add(new KeywordEffectPair("in", "underline"));
        pairs.add(new KeywordEffectPair("to", "yellow"));
        pairs.add(new KeywordEffectPair("java", "red"));
    }


    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        dynamicSyntaxHighlighter.highlightThis(null);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        dynamicSyntaxHighlighter.highlightThis(sentence);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        dynamicSyntaxHighlighter.highlightThis(sentence);
    }

    @Test
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expectedHighlightedSentence = "I know everything, so it's waste of effort.";
        String highlightSentence = dynamicSyntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnThisSentence() {
        String sentence = "I am going to join,java mentoring program to learn cool stuff in fun way.";
        String expected = "I [bold] am [/bold] going [italic] [yellow] to [/yellow] [/italic] join,java mentoring program " +
                "[italic] [yellow] to [/yellow] [/italic] learn cool stuff [underline] in [/underline] fun way.";
        String highlightSentence = dynamicSyntaxHighlighter.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I am going in now.";
        String expected = "I [bold] am [/bold] going [underline] in [/underline] now.";
        String highlightSentence = dynamicSyntaxHighlighter.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expected = "I will go [underline] in [/underline].";
        String highlightSentence = dynamicSyntaxHighlighter.highlightThis(sentence);
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

    @Test
    public void shouldApplyRedToTheWordJava() {
        String sentence = "I going join java mentoring program.";
        String expectedHighlightedSentence = "I going join [red] java [/red] mentoring program.";
        String highlightSentence = dynamicSyntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyYellowToTheWordInItalic() {
        String sentence = "I going to join";
        String expectedHighlightedSentence = "I going [italic] [yellow] to [/yellow] [/italic] join";
        String highlightSentence = dynamicSyntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

}
