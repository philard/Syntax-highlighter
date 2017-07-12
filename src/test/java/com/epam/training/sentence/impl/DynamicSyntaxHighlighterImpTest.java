package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.validation.impl.SentenceValidatorImp;
import com.epam.training.word.WordHighlighter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Philip on 16/03/2017.
 *
 * Similar tests for see integration tests.
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
        String[] configArray = serializedConfig.split("(-|,\\s?)");
        for (int i = 0; i < configArray.length; i++) {
            KeywordEffectPair keywordEffectPair = Mockito.mock(KeywordEffectPair.class);
            when(keywordEffectPair.getKeyword()).thenReturn(configArray[i]);
            when(keywordEffectPair.getEffect()).thenReturn(configArray[i+1]);

            highlightConfigs.add(keywordEffectPair);
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

    private WordHighlighter extendWordHighlighter(WordHighlighter wordHighlighter,
                                                  String input, String output) {
        when(wordHighlighter.highlightSentence(input))
                .thenReturn(output);
        return wordHighlighter;
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
