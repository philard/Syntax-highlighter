package com.epam.training.validation.impl;


import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.validation.SentenceValidator;
import org.springframework.util.StringUtils;

public class SentenceValidatorImp implements SentenceValidator {

    @Override
    public boolean validate(String sentence){
        if(StringUtils.isEmpty(sentence)) {
            throw new SyntaxHighlightingException();
        }
        if(sentence.matches("\\s*")) {
            throw new SyntaxHighlightingException(); 
        }
        return true;
    }
}
