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
 */
public class SyntaxHightlighterImplTest {

    private SyntaxHighlighterImpl syntaxHighlighter;

    private IWordHighlighter amHighlighter;

    private ArrayList<IWordHighlighter> wordHighlighters;

    @Before
    public void beforeTest() {

        syntaxHighlighter = new SyntaxHighlighterImpl();
        syntaxHighlighter.setSentenceValidatorImpl(getSentenceValidator());

        wordHighlighters = buildWordHighlighters();
        syntaxHighlighter.setWordHighlighters(wordHighlighters);
    }

    private ArrayList<IWordHighlighter> buildWordHighlighters() {
        wordHighlighters = new ArrayList<IWordHighlighter>();
        amHighlighter = mock(SimpleWordHighlighter.class);
        when(amHighlighter.highlightThis("I am going to join java mentoring program " +
                "to learn cool stuff in fun way."))
                .thenReturn("I [bold]am[/bold] going to join java mentoring program " +
                        "to learn cool stuff in fun way.");
        wordHighlighters.add(amHighlighter);
        SimpleWordHighlighter toHighlighter = mock(SimpleWordHighlighter.class);
        when(toHighlighter.highlightThis("I [bold]am[/bold] going to join java mentoring program " +
                "to learn cool stuff in fun way."))
                .thenReturn("I [bold]am[/bold] going [italic]to[/italic] join java mentoring program " +
                        "[italic]to[/italic] learn cool stuff in fun way.");
        wordHighlighters.add(toHighlighter);
        return wordHighlighters;
    }

    private SentenceValidatorImpl getSentenceValidator() {
        SentenceValidatorImpl sentenceValidatorImplMock;

        sentenceValidatorImplMock = Mockito.mock(SentenceValidatorImpl.class);
        doThrow(new SyntaxHighlightingException(""))
                .when(sentenceValidatorImplMock).validate(null);
        doThrow(new SyntaxHighlightingException(""))
                .when(sentenceValidatorImplMock).validate("");
        doThrow(new SyntaxHighlightingException(""))
                .when(sentenceValidatorImplMock).validate("     ");
        return sentenceValidatorImplMock;
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
        String expectedHighlightedSentence = "I [bold]am[/bold] going [italic]to[/italic] join java mentoring program " +
                "[italic]to[/italic] learn cool stuff in fun way.";

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
