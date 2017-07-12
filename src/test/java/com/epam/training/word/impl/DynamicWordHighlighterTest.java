package com.epam.training.word.impl;

import com.epam.training.sentence.data.KeywordEffectPair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DynamicWordHighlighterTest {

    private DynamicWordHighlighter dynamicWordHighlighter;

    @Before
    public void setup() {
        dynamicWordHighlighter = new DynamicWordHighlighter(new KeywordEffectPair("in", "underline"));
    }

    @Test
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expectedHighlightedSentence = "I know everything, so it's waste of effort.";

        KeywordEffectPair pair = new KeywordEffectPair("in", "underline");
        String highlightSentence = dynamicWordHighlighter.highlightSentence(sentence, pair);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldNotApplyStyleIfAlreadyApplied() {
        String sentence = "I'm [underline] in [/underline] ";
        String expectedHighlightedSentence = "I'm [underline] [underline] in [/underline] [/underline] ";

        KeywordEffectPair pair = new KeywordEffectPair("in", "underline");
        String highlightSentence = dynamicWordHighlighter.highlightSentence(sentence, pair);

        assertEquals(expectedHighlightedSentence, highlightSentence);

    }

    @Test
    public void shouldApplyStyleOnThisEasySentence() {
        String sentence = "I'm going in now";
        String expectedHighlightedSentence = "I'm going [underline] in [/underline] now";

        KeywordEffectPair pair = new KeywordEffectPair("in", "underline");
        String highlightSentence = dynamicWordHighlighter.highlightSentence(sentence, pair);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I'm inn.";
        String expectedHighlightedSentence = "I'm inn.";

        KeywordEffectPair pair = new KeywordEffectPair("in", "underline");
        String highlightSentence = dynamicWordHighlighter.highlightSentence(sentence, pair);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expectedHighlightedSentence = "I will go [underline] in [/underline].";

        KeywordEffectPair pair = new KeywordEffectPair("in", "underline");
        String highlightSentence = dynamicWordHighlighter.highlightSentence(sentence, pair);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }


}
