package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.validation.impl.SentenceValidatorImp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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


        noWordsSyntaxHighlighterImp = new DynamicSyntaxHighlighterImp(buildSentenceValidator());
        noWordsSyntaxHighlighterImp.setHighlightConfig(new ArrayList<>());
    }

    private Collection<KeywordEffectPair> buildHighlightConfig() {
        String serializedConfig = "am-bold, to-italic, in-underline, to-yellow, java-red";
        Iterator<String> words = Arrays.asList(serializedConfig.split("(-|,\\s?)")).iterator();
        while(words.hasNext()) {
            KeywordEffectPair thisMock = Mockito.mock(KeywordEffectPair.class);
            when(thisMock.getKeyword()).thenReturn(words.next());
            when(thisMock.getEffect()).thenReturn(words.next());
            highlightConfigs.add(thisMock);
        }
        return highlightConfigs;
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
