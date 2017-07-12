package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.validation.impl.SentenceValidatorImp;
import com.epam.training.word.impl.DynamicWordHighlighter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

/**
 * Created by Philip on 16/03/2017.
 *
 * Similar tests in integration tests.
 */
public class DynamicSyntaxHighlighterImpTest {

    private DynamicSyntaxHighlighterImp dynamicSyntaxHighlighterImp;
    private Collection<KeywordEffectPair> highlightConfigs = new ArrayList<>();

    private DynamicSyntaxHighlighterImp noWordsSyntaxHighlighterImp;

    @Before
    public void setupSyntaxHighlighter() {
        dynamicSyntaxHighlighterImp = new DynamicSyntaxHighlighterImp(buildSentenceValidator());
        dynamicSyntaxHighlighterImp.setHighlightConfig(buildHighlightConfig());
        dynamicSyntaxHighlighterImp.setDynamicWordHighlighter(buildWordHighlighter());


        noWordsSyntaxHighlighterImp = new DynamicSyntaxHighlighterImp(buildSentenceValidator());
        noWordsSyntaxHighlighterImp.setHighlightConfig(new ArrayList<>());
    }

    private SentenceValidatorImp buildSentenceValidator() {
        SentenceValidatorImp sentenceValidatorImpMock = Mockito.mock(SentenceValidatorImp.class);
        extendSentenceValidator(sentenceValidatorImpMock, null, new SyntaxHighlightingException(""));
        extendSentenceValidator(sentenceValidatorImpMock, "", new SyntaxHighlightingException(""));
        extendSentenceValidator(sentenceValidatorImpMock, "     ", new SyntaxHighlightingException(""));
        return sentenceValidatorImpMock;
    }

    private SentenceValidatorImp extendSentenceValidator(SentenceValidatorImp mock
            , String input, Exception throwable) {
        doThrow(throwable)
                .when(mock).validate(input);
        return mock;
    }

    private Collection<KeywordEffectPair> buildHighlightConfig() {
        String serializedConfig = "am-bold,to-italic,in-underline,to-yellow,java-red";
        ArrayList<KeywordEffectPair> config = new ArrayList<>();
        
        Iterator<String> words = Arrays.asList(serializedConfig.split("(-|,\\s?)")).iterator();
        while(words.hasNext()) {
            config.add(new KeywordEffectPair(words.next(), words.next()));
        }
        return config;
    }

    private DynamicWordHighlighter buildWordHighlighter() {
        return new DynamicWordHighlighter("in", "underline");
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        dynamicSyntaxHighlighterImp.highlightThis(null);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        dynamicSyntaxHighlighterImp.highlightThis(sentence);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        dynamicSyntaxHighlighterImp.highlightThis(sentence);
    }

    @Test
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expected = "I know everything, so it's waste of effort.";

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnThisSentence() {
        String sentence = "I am going to join,java mentoring program to learn cool stuff in fun way.";
        String expected  = "I [bold] am [/bold] going [italic] [yellow] to [/yellow] [/italic] join,java mentoring program " +
                "[italic] [yellow] to [/yellow] [/italic] learn cool stuff [underline] in [/underline] fun way.";

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I am going in now.";
        String expected = "I [bold] am [/bold] going [underline] in [/underline] now.";

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expected = "I will go [underline] in [/underline].";

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
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
