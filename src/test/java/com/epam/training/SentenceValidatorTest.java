package com.epam.training;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Philip on 15/03/2017.
 */
public class SentenceValidatorTest {

    private SentenceValidator sentenceValidator;

    @Before
    public void setup() {
        sentenceValidator = new SentenceValidator();
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnNull() {
        String sentence = null;
        sentenceValidator.validate(sentence);
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnBlank() {
        String sentence = "";
        sentenceValidator.validate(sentence);
    }

    @Test(expected=SyntaxHighlightingException.class)
    public void shouldReportThatStyleCannotBeAppliedOnWhiteSpaces() {
        String sentence = "     ";
        sentenceValidator.validate(sentence);
    }

}
