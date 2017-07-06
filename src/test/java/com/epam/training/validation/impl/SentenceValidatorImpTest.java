package com.epam.training.validation.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Philip on 15/03/2017.
 */
public class SentenceValidatorImpTest {

    private SentenceValidatorImp sentenceValidatorImp;

    @Before
    public void setup() {
        sentenceValidatorImp = new SentenceValidatorImp();
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        String sentence = null;
        sentenceValidatorImp.validate(sentence);
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        sentenceValidatorImp.validate(sentence);
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        sentenceValidatorImp.validate(sentence);
    }

}
