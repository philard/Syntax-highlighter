package com.epam.training.sentence.impl;

import com.epam.training.context.AppConfig;
import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.data.KeywordEffectPair;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * DynamicSyntaxHighlighter is configured with services and configured at invocation by a collection of 
 * Keyword-Effect pairs. 
 * 
 */
public class DynamicSyntaxHighlighterImpIntegrationTest {

    private DynamicSyntaxHighlighterImp dynamicSyntaxHighlighter;

    private DynamicSyntaxHighlighterImp noWordsSyntaxHighlighterImp;

    @Before
    public void setupAmToInWordsSyntaxHighlighterImp() {
        AbstractApplicationContext amToInContext = new AnnotationConfigApplicationContext(AppConfig.class);
        dynamicSyntaxHighlighter = (DynamicSyntaxHighlighterImp) amToInContext.getBean("dynamicSyntaxHighlighter");
        String serializedConfig = "am-bold,to-italic,in-underline,to-yellow,java-red";
        dynamicSyntaxHighlighter.setHighlightConfig(buildHighlightConfig(serializedConfig));

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        noWordsSyntaxHighlighterImp = (DynamicSyntaxHighlighterImp) context.getBean("dynamicSyntaxHighlighter");
        noWordsSyntaxHighlighterImp.setHighlightConfig(new ArrayList<>());
    }

    private static Collection<KeywordEffectPair> buildHighlightConfig(String serializedConfig) {
        ArrayList<KeywordEffectPair> config = new ArrayList<>();

        Iterator<String> words = Arrays.asList(serializedConfig.split("(-|,\\s?)")).iterator();
        while(words.hasNext()) {
            config.add(new KeywordEffectPair(words.next(), words.next()));
        }
        return config;
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
