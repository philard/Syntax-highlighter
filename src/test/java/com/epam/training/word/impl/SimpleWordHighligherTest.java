package com.epam.training.word.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Philip on 14/03/2017.
 */
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

        String highlightSentence = simpleWordHighlighter.highlightThis(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldNotApplyStyleIfAlreadyApplied() {
        String sentence = "I'm in ";
        String expectedHighlightedSentence = "I'm [underline]in[/underline] ";

        String highlightSentence = simpleWordHighlighter.highlightThis(sentence);
        highlightSentence = simpleWordHighlighter.highlightThis(highlightSentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);

    }

    @Test
    public void shouldApplyStyleOnThisEasySentence() {
        String sentence = "I'm going ";
        String expectedHighlightedSentence = "I'm going ";

        String highlightSentence = simpleWordHighlighter.highlightThis(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleOnlyWhenMatchedWholeWord() {
        String sentence = "I'm in.";
        String expectedHighlightedSentence = "I'm [underline]in[/underline].";

        String highlightSentence = simpleWordHighlighter.highlightThis(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }

    @Test
    public void shouldApplyStyleWhenMatchedWordIsNextToAFullStop() {
        String sentence = "I will go in.";
        String expectedHighlightedSentence = "I will go [underline]in[/underline].";

        String highlightSentence = simpleWordHighlighter.highlightThis(sentence);

        assertEquals(expectedHighlightedSentence, highlightSentence);
    }


}
