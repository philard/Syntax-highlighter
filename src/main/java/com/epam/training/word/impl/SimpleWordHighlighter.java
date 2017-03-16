package com.epam.training.word.impl;

import com.epam.training.word.IWordHighlighter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Philip_John_Ardley on 13-Mar-17.
 */
public class SimpleWordHighlighter implements IWordHighlighter {

    private String prefix;

    private String word;

    private String postfix;

    public SimpleWordHighlighter(String prefix, String word, String postfix) {
        this.prefix = prefix;
        this.word = word;
        this.postfix = postfix;
    }

    @Override
    public String highlightThis(String sentence) {

        String highlightedWord = prefix + word + postfix;
        sentence = sentence.replaceAll("(?<=\\s)" + word + "(?=\\s|\\.)", highlightedWord);

        return sentence;
    }
}