package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.validation.impl.SentenceValidatorImpl;
import com.epam.training.word.IWordHighlighter;
import com.epam.training.word.impl.SimpleWordHighlighter;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;

import org.mockito.Matchers;

import static org.mockito.Mockito.*;

/**
 * Created by Philip on 16/03/2017.
 */
public class SyntaxHightlighterImplTest {

    private SyntaxHighlighterImpl syntaxHighlighter;

    private IWordHighlighter wordHighlighter;

    @Before
    public void beforeTest() {

        syntaxHighlighter = new SyntaxHighlighterImpl();

        syntaxHighlighter.setSentenceValidatorImpl(getSentenceValidator());


        wordHighlighter = mock(SimpleWordHighlighter.class);
        doReturn("I'm going ")
                .when(wordHighlighter).highlightThis("I'm going ");
        ArrayList<IWordHighlighter> wordHighlighters = new ArrayList<IWordHighlighter>();
        wordHighlighters.add(wordHighlighter);
        syntaxHighlighter.setWordHighlighters(wordHighlighters);

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

}
