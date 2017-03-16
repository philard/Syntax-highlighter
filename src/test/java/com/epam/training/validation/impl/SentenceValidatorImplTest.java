package com.epam.training.validation.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Philip on 15/03/2017.
 */
public class SentenceValidatorImplTest {

    private SentenceValidatorImpl sentenceValidatorImpl;

    @Before
    public void setup() {
        sentenceValidatorImpl = new SentenceValidatorImpl();
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        String sentence = null;
        sentenceValidatorImpl.validate(sentence);
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        sentenceValidatorImpl.validate(sentence);
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        sentenceValidatorImpl.validate(sentence);
    }

}
