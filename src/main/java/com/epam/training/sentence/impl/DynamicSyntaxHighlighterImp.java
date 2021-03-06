package com.epam.training.sentence.impl;

import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.validation.SentenceValidator;
import com.epam.training.word.impl.DynamicWordHighlighter;

import java.util.Collection;

/**
 * Created by philip_john_ardley on 7/12/17.
 */
public class DynamicSyntaxHighlighterImp extends ValidatingSyntaxHighlighter {


    private Collection<KeywordEffectPair> highlightConfig;
    private DynamicWordHighlighter dynamicWordHighlighter;

    public DynamicSyntaxHighlighterImp(SentenceValidator sentenceValidator) {
        super(sentenceValidator);
    }

    public void setHighlightConfig(Collection<KeywordEffectPair> highlightConfig) {
        this.highlightConfig = highlightConfig;
    }

    public void setDynamicWordHighlighter(DynamicWordHighlighter dynamicWordHighlighter) {
        this.dynamicWordHighlighter = dynamicWordHighlighter;
    }
    
    @Override
    public String highlightThis(String sentence) {
        this.getSentenceValidator().validate(sentence);
        String highlighted = sentence;
        for(KeywordEffectPair keywordEffectPair: highlightConfig) {
            highlighted = this.dynamicWordHighlighter.highlightSentence(highlighted, keywordEffectPair);
        }
        return highlighted;
    }

}
