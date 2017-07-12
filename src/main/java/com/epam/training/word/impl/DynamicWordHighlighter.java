package com.epam.training.word.impl;

import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.word.WordHighlighter;

/**
 * Created by philip_john_ardley on 7/12/17.
 */
public class DynamicWordHighlighter implements WordHighlighter {


    private KeywordEffectPair keywordEffectPair;

    public DynamicWordHighlighter(KeywordEffectPair keywordEffectPair) {
        this.keywordEffectPair = keywordEffectPair;
    }

    @Override
    public String highlightSentence(String sentence) {
        String highlightedWord = "[" + keywordEffectPair.getEffect() + "] " 
                + keywordEffectPair.getKeyword() + " [/" + keywordEffectPair.getEffect() + "]";
        sentence = sentence.replaceAll("(?<=\\s)" + keywordEffectPair.getKeyword() + "(?=\\s|\\.)", highlightedWord);
        return sentence;
    }

    public String highlightSentence(String sentence, KeywordEffectPair config) {
        this.keywordEffectPair = config;
        return this.highlightSentence(sentence);
    }
    
}