package com.epam.training.word.impl;

import com.epam.training.word.WordHighlighter;

public class ColorWordHighlighter implements WordHighlighter {

    private final String prefix;
    private final String word;
    private final String postfix;

    public ColorWordHighlighter(String prefix, String word, String postfix) {
        this.prefix = prefix;
        this.word = word;
        this.postfix = postfix;
    }
    
    @Override
    public String highlightSentence(String sentence) {
        String coloredWord = prefix + " " + word + " " + postfix;
        sentence = sentence.replaceAll("(?<=\\s)" + word + "(?=\\s|\\.)", coloredWord);
        
        return sentence;
    }
}
