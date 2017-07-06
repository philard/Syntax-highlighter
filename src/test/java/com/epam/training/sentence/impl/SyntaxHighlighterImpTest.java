package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.validation.impl.SentenceValidatorImp;
import com.epam.training.word.WordHighlighter;
import com.epam.training.word.impl.SimpleWordHighlighter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Philip on 16/03/2017.
 *
 * I could have mocked with all 3 word highlighters but didn't think it necessary - see integration test.
 */
public class SyntaxHighlighterImpTest {

    private SyntaxHighlighterImp amInWordsSyntaxHighlighterImp;
    private WordHighlighter amWordHighlighter;
    private SimpleWordHighlighter inWordHighlighter;
    
    private SyntaxHighlighterImp noWordsSyntaxHighlighterImp;

    @Before
    public void setupAmToInWordsSyntaxHighlighterImp() {
        amInWordsSyntaxHighlighterImp = new SyntaxHighlighterImp(buildSentenceValidator());
        amInWordsSyntaxHighlighterImp.setWordHighlighters(buildWordHighlighters());
    }

    private SentenceValidatorImp buildSentenceValidator() {
        SentenceValidatorImp sentenceValidatorImpMock = Mockito.mock(SentenceValidatorImp.class);
        extendSentenceValidator(sentenceValidatorImpMock, null, new SyntaxHighlightingException(""));
        extendSentenceValidator(sentenceValidatorImpMock, "", new SyntaxHighlightingException(""));
        extendSentenceValidator(sentenceValidatorImpMock, "     ", new SyntaxHighlightingException(""));
        return sentenceValidatorImpMock;
    }

    private ArrayList<WordHighlighter> buildWordHighlighters() {
        ArrayList<WordHighlighter> wordHighlighters = new ArrayList<WordHighlighter>();
        amWordHighlighter = mock(SimpleWordHighlighter.class);
        inWordHighlighter = mock(SimpleWordHighlighter.class);
        wordHighlighters.add(amWordHighlighter);
        wordHighlighters.add(inWordHighlighter);
        return wordHighlighters;
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
        amInWordsSyntaxHighlighterImp.highlightThis(null);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        amInWordsSyntaxHighlighterImp.highlightThis(sentence);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        amInWordsSyntaxHighlighterImp.highlightThis(sentence);
    }

    @Test
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expectedHighlightedSentence = "I know everything, so it's waste of effort.";
        extendWordHighlighter(amWordHighlighter, sentence, expectedHighlightedSentence);
        extendWordHighlighter(inWordHighlighter, sentence, expectedHighlightedSentence);

        String highlightSentence = amInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnThisSentence() {
        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        String partialHighlightedSentence = "I [bold]am[/bold] going to join java mentoring program " +
                "to learn cool stuff in fun way.";
        String expectedHighlightedSentence = "I [bold]am[/bold] going to join java mentoring program " +
                "to learn cool stuff [underline]in[/underline] fun way.";
        extendWordHighlighter(amWordHighlighter, sentence, partialHighlightedSentence);
        extendWordHighlighter(inWordHighlighter, partialHighlightedSentence, expectedHighlightedSentence);

        String highlightSentence = amInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I am going in now.";
        String partialHighlightedSentence = "I [bold]am[/bold] going in now.";
        String expectedHighlightedSentence = "I [bold]am[/bold] going [underline]in[/underline] now.";
        extendWordHighlighter(amWordHighlighter, sentence, partialHighlightedSentence);
        extendWordHighlighter(inWordHighlighter, partialHighlightedSentence, expectedHighlightedSentence);

        String highlightSentence = amInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expectedHighlightedSentence = "I will go [underline]in[/underline].";

        extendWordHighlighter(amWordHighlighter, sentence, sentence);
        extendWordHighlighter(inWordHighlighter, sentence, expectedHighlightedSentence);

        String highlightSentence = amInWordsSyntaxHighlighterImp.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Before
    public void setupNoWordsSyntaxHighlighter() {
        noWordsSyntaxHighlighterImp = new SyntaxHighlighterImp(buildSentenceValidator());
        noWordsSyntaxHighlighterImp.setWordHighlighters(new ArrayList<WordHighlighter>());
    }

    @Test
    public void shouldNotApplyStylesWhenNoWordHighlighterAndThisSentence() {
        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        String expectedHighlightedSentence = "I am going to join java mentoring program to learn cool stuff in fun way.";

        String highlightSentence = noWordsSyntaxHighlighterImp.highlightThis(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test(expected = SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedWhenNoWordsHighlighterAndWhiteSpaces() {
        String sentence = "     ";
        amInWordsSyntaxHighlighterImp.highlightThis(sentence);
    }
}