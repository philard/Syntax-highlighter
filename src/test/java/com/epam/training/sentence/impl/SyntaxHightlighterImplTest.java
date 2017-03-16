package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.validation.impl.SentenceValidatorImpl;
import com.epam.training.word.IWordHighlighter;
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
public class SyntaxHightlighterImplTest {

    private ISyntaxHighlighterImpl syntaxHighlighter;

    private IWordHighlighter amHighlighter;
    private SimpleWordHighlighter inHighlighter;
    private ArrayList<IWordHighlighter> wordHighlighters;

    @Before
    public void buildMocksBeforeTests() {
        syntaxHighlighter = new ISyntaxHighlighterImpl();

        syntaxHighlighter.setSentenceValidatorImpl(buildSentenceValidator());

        wordHighlighters = buildWordHighlighters();
        syntaxHighlighter.setWordHighlighters(wordHighlighters);
    }

    private SentenceValidatorImpl buildSentenceValidator() {
        SentenceValidatorImpl sentenceValidatorImplMock = Mockito.mock(SentenceValidatorImpl.class);
        extendSentenceValidator(sentenceValidatorImplMock, null, new SyntaxHighlightingException(""));
        extendSentenceValidator(sentenceValidatorImplMock, "", new SyntaxHighlightingException(""));
        extendSentenceValidator(sentenceValidatorImplMock, "     ", new SyntaxHighlightingException(""));
        return sentenceValidatorImplMock;
    }

    private SentenceValidatorImpl extendSentenceValidator(SentenceValidatorImpl mock
            , String input, Exception throwable) {
        doThrow(throwable)
                .when(mock).validate(input);
        return mock;
    }

    private ArrayList<IWordHighlighter> buildWordHighlighters() {
        wordHighlighters = new ArrayList<IWordHighlighter>();
        amHighlighter = mock(SimpleWordHighlighter.class);
        inHighlighter = mock(SimpleWordHighlighter.class);
        wordHighlighters.add(amHighlighter);
        wordHighlighters.add(inHighlighter);
        return wordHighlighters;
    }

    private IWordHighlighter extendWordHighlighter(IWordHighlighter wordHighlighter,
                                                   String input, String output) {
        when(wordHighlighter.highlightSentence(input))
                .thenReturn(output);
        return wordHighlighter;
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        syntaxHighlighter.highlightThis(null);
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
        extendWordHighlighter(amHighlighter, sentence, expectedHighlightedSentence);
        extendWordHighlighter(inHighlighter, sentence, expectedHighlightedSentence);

        String highlightSentence = syntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnThisSentence() {
        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        String partialHighlightedSentence = "I [bold]am[/bold] going to join java mentoring program " +
                "to learn cool stuff in fun way.";
        String expectedHighlightedSentence = "I [bold]am[/bold] going to join java mentoring program " +
                "to learn cool stuff [underline]in[/underline] fun way.";
        extendWordHighlighter(amHighlighter, sentence, partialHighlightedSentence);
        extendWordHighlighter(inHighlighter, partialHighlightedSentence, expectedHighlightedSentence);

        String highlightSentence = syntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I am going in now.";
        String partialHighlightedSentence = "I [bold]am[/bold] going in now.";
        String expectedHighlightedSentence = "I [bold]am[/bold] going [underline]in[/underline] now.";
        extendWordHighlighter(amHighlighter, sentence, partialHighlightedSentence);
        extendWordHighlighter(inHighlighter, partialHighlightedSentence, expectedHighlightedSentence);

        String highlightSentence = syntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expectedHighlightedSentence = "I will go [underline]in[/underline].";

        extendWordHighlighter(amHighlighter, sentence, sentence);
        extendWordHighlighter(inHighlighter, sentence, expectedHighlightedSentence);

        String highlightSentence = syntaxHighlighter.highlightThis(sentence);
        assertEquals(expectedHighlightedSentence, highlightSentence);
    }
}
