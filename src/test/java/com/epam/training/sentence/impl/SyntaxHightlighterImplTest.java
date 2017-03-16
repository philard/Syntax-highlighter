package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.validation.impl.SentenceValidatorImpl;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Philip on 16/03/2017.
 */
public class SyntaxHightlighterImplTest {

    private SyntaxHighlighterImpl syntaxHighlighter;

    private SentenceValidatorImpl sentenceValidatorImpl;

    @Before
    public void beforeTest() {

        syntaxHighlighter = new SyntaxHighlighterImpl();
        syntaxHighlighter.setSentenceValidatorImpl(sentenceValidatorImpl);

        sentenceValidatorImpl = mock(SentenceValidatorImpl.class);

        OngoingStubbing noValue;
        noValue = when(sentenceValidatorImpl.validate(null))
                .thenThrow(SyntaxException.class);
        when(sentenceValidatorImpl.validate(""))
                .thenThrow(SyntaxException.class));
        when(sentenceValidatorImpl.validate("    "))
                .thenThrow(SyntaxException.class));
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
