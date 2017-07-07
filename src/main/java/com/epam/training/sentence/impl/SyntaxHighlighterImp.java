package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.validation.SentenceValidator;
import com.epam.training.word.WordHighlighter;

import java.util.Collection;

public class SyntaxHighlighterImp implements SyntaxHighlighter {

    public SyntaxHighlighterImp(SentenceValidator sentenceValidator) {
        this.sentenceValidator = sentenceValidator;
    }
	
    private SentenceValidator sentenceValidator;

	public SentenceValidator getSentenceValidator() {
		return sentenceValidator;
	}

	private Collection<WordHighlighter> wordHighlighters;

	public Collection<WordHighlighter> getWordHighlighters() {
		return wordHighlighters;
	}

	public void setWordHighlighters(Collection<WordHighlighter> wordHighlighters) {
		this.wordHighlighters = wordHighlighters;
	}

	public String highlightThis(String sentence) throws SyntaxHighlightingException {

		sentenceValidator.validate(sentence);

		for(WordHighlighter wordHighlighter: wordHighlighters) {
			sentence = wordHighlighter.highlightSentence(sentence);
		}
		return sentence;
	}

}
