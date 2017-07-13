package com.epam.training.sentence.impl;

import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.validation.SentenceValidator;

/**
 * Created by philip_john_ardley on 7/12/17.
 */
public abstract class ValidatingSyntaxHighlighter implements SyntaxHighlighter {
    protected SentenceValidator sentenceValidator;

    public ValidatingSyntaxHighlighter(SentenceValidator sentenceValidator) {
        this.sentenceValidator = sentenceValidator;
    }

}
