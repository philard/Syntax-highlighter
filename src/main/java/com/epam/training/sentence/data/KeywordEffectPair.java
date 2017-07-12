package com.epam.training.sentence.data;

/**
 * Created by philip_john_ardley on 7/12/17.
 */
public class KeywordEffectPair {

    private String keyword;
    private String effect;

    public KeywordEffectPair(String keyword, String effect) {
        this.keyword = keyword;
        this.effect = effect;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getEffect() {
        return effect;
    }
}
