package com.epam.training.word.impl;

import com.epam.training.word.WordHighlighter;

public class SimpleWordHighlighter implements WordHighlighter {

    private String prefix;

    private String word;

    private String postfix;

    public SimpleWordHighlighter(String prefix, String word, String postfix) {
        this.prefix = prefix;
        this.word = word;
        this.postfix = postfix;
    }

    @Override
    public String highlightSentence(String sentence) {

        String highlightedWord = prefix + " " + word + " " + postfix;
        return sentence.replaceAll("(?<=\\s)" + word + "(?=\\s|\\.)", highlightedWord);
    }
}
