package com.epam.training.word.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleWordHighligherTest {

    private SimpleWordHighlighter simpleWordHighlighter;

    @Before
    public void setup() {
        simpleWordHighlighter = new SimpleWordHighlighter("[underline]", "in", "[/underline]");
    }

    @Test
    public void shouldNotApplyStyleOnThisSentence() {
        String sentence = "I know everything, so it's waste of effort.";
        String expectedHighlightedSentence = "I know everything, so it's waste of effort.";

        String highlightSentence = simpleWordHighlighter.highlightSentence(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleIfAlreadyApplied() {
        String sentence = "I'm [underline] in [/underline] ";
        String expectedHighlightedSentence = "I'm [underline] [underline] in [/underline] [/underline] ";

        String highlightSentence = simpleWordHighlighter.highlightSentence(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);

    }

    @Test
    public void shouldApplyStyleOnThisEasySentence() {
        String sentence = "I'm going in now";
        String expectedHighlightedSentence = "I'm going [underline] in [/underline] now";

        String highlightSentence = simpleWordHighlighter.highlightSentence(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I'm inn.";
        String expectedHighlightedSentence = "I'm inn.";

        String highlightSentence = simpleWordHighlighter.highlightSentence(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expectedHighlightedSentence = "I will go [underline] in [/underline].";

        String highlightSentence = simpleWordHighlighter.highlightSentence(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }


}
