package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
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
//    @Mock
//    private KeywordEffectPair amToBold;
//    @Mock
//    private KeywordEffectPair toToItalic;
//    @Mock
//    private KeywordEffectPair inToUnderline;
//    @Mock
//    private KeywordEffectPair toToYellow;
//    @Mock
//    private KeywordEffectPair javaToRed;
    private Collection<KeywordEffectPair> highlightConfigs;

    private SyntaxHighlighterImp noWordsSyntaxHighlighterImp;

    @Before
    public void setupSyntaxHighlighter() {
        dynamicSyntaxHighlighterImp = new DynamicSyntaxHighlighterImp(buildSentenceValidator());
        dynamicSyntaxHighlighterImp.setHighlightConfig(buildHighlightConfig());
    }

    private Collection<KeywordEffectPair> buildHighlightConfig() {
        String serializedConfig = "am-bold,to-italic,in-underline,to-yellow,java-red";
        String[] configArray = serializedConfig.split("(-|,)");
        for (int i = 0; i < configArray.length; i++) {
            KeywordEffectPair keywordEffectPair = Mockito.mock(KeywordEffectPair.class);
            when(keywordEffectPair).getKeyword(configArray.next()).thenReturn(configArray.next());
            highlightConfigs.add(keywordsEffectPair);
        }
        
//        when(amToBold).getKeyword("am").thenReturn("bold");
//        when(toToItalic).getKeyword("to").thenReturn("italic");
//        when(inToUnderline).getKeyword("in").thenReturn("underline");
//        when(toToYellow).getKeyword("to").thenReturn("yellow");
//        when(javaToRed).getKeyword("java").thenReturn("red");
//        ArrayList<KeywordEffectPair> pairs = Arrays.asList(amToBold, toToItalic, inToUnderline, toToYellow, javaToRed);
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
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expected = "I know everything, so it's waste of effort.";
        extendWordHighlighter(amWordHighlighter, sentence, expected);
        extendWordHighlighter(toWordHighlighter, expected, expected);
        extendWordHighlighter(inWordHighlighter, expected, expected);

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnThisSentence() {
        String sentence = "I am going to join,java mentoring program to learn cool stuff in fun way.";
        String sentenceHighlightedAm = "I [bold] am [/bold] going to join,java mentoring program " +
                "to learn cool stuff in fun way.";
        String sentenceHighlightedTo = "I [bold] am [/bold] going [italic] to [/italic] join,java mentoring program " +
                "[italic] to [/italic] learn cool stuff in fun way.";
        String expected  = "I [bold] am [/bold] going to join,java mentoring program " +
                "[italic] to [/italic] learn cool stuff [underline] in [/underline] fun way.";
        extendWordHighlighter(amWordHighlighter, sentence, sentenceHighlightedAm);
        extendWordHighlighter(toWordHighlighter, sentenceHighlightedAm, sentenceHighlightedTo);
        extendWordHighlighter(inWordHighlighter, sentenceHighlightedTo, expected);

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I am going in now.";
        String sentenceHighlightedAm = "I [bold] am [/bold] going in now.";
        String sentenceHighlightedTo = "I [bold] am [/bold] going in now.";
        String expected = "I [bold] am [/bold] going [underline] in [/underline] now.";
        extendWordHighlighter(amWordHighlighter, sentence, sentenceHighlightedAm);
        extendWordHighlighter(toWordHighlighter, sentenceHighlightedAm, sentenceHighlightedTo);
        extendWordHighlighter(inWordHighlighter, sentenceHighlightedTo, expected);

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String sentenceHighlightedAm = "I will go in.";
        String sentenceHighlightedTo = "I will go in.";
        String expected = "I will go [underline] in [/underline].";
        extendWordHighlighter(amWordHighlighter, sentence, sentenceHighlightedAm);
        extendWordHighlighter(toWordHighlighter, sentenceHighlightedAm, sentenceHighlightedTo);
        extendWordHighlighter(inWordHighlighter, sentenceHighlightedTo, expected);

        String highlightSentence = dynamicSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expected, highlightSentence);
    }

    @Before
    public void setupNoWordsSyntaxHighlighterImp() {
        noWordsSyntaxHighlighterImp = new SyntaxHighlighterImp(buildSentenceValidator());
        noWordsSyntaxHighlighterImp.setWordHighlighters(new ArrayList<WordHighlighter>());
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
