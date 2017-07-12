package com.epam.training.word.impl;

import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.word.WordHighlighter;

/**
 * Created by philip_john_ardley on 7/12/17.
 */
public class DynamicWordHighlighter implements WordHighlighter {


    private KeywordEffectPair config;

    public DynamicWordHighlighter(KeywordEffectPair config) {
        this.config = config;
    }

    @Override
    public String highlightSentence(String sentence) {
        String highlightedWord = "[" + config.getEffect() + "] " + config.getKeyword() + " [/" + config.getEffect() + "]";
        sentence = sentence.replaceAll("(?<=\\s)" + config.getKeyword() + "(?=\\s|\\.)", highlightedWord);
        return sentence;
    }

    public String highlightSentence(String sentence, KeywordEffectPair config) {
        this.config = config;
        return this.highlightSentence(sentence);
    }
    
}
