package com.epam.training.validation.impl;


import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.validation.ISentenceValidator;

/**
 * Created by Philip_John_Ardley on 13-Mar-17.
 */
public class SentenceValidatorImpl implements ISentenceValidator {

    @Override
    public boolean validate(String sentence) {
        if(sentence == null || sentence == "")
            throw new SyntaxHighlightingException();
        if(sentence.matches("\\s*"))
            throw new SyntaxHighlightingException();
        return true;
    }
}
