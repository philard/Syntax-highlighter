package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.validation.SentenceValidator;
import com.epam.training.word.WordHighlighter;

import java.util.Collection;

public class SyntaxHighlighterImp implements SyntaxHighlighter {

    public SyntaxHighlighterImp(SentenceValidator sentenceValidatorImpl) {
        this.sentenceValidatorImpl = sentenceValidatorImpl;
    }
	
    private SentenceValidator sentenceValidatorImpl;

	public SentenceValidator getSentenceValidatorImpl() {
		return sentenceValidatorImpl;
	}

	private Collection<WordHighlighter> wordHighlighters;

	public Collection<WordHighlighter> getWordHighlighters() {
		return wordHighlighters;
	}

	public void setWordHighlighters(Collection<WordHighlighter> wordHighlighters) {
		this.wordHighlighters = wordHighlighters;
	}

	public String highlightThis(String sentence) throws SyntaxHighlightingException {

		sentenceValidatorImpl.validate(sentence);

		for(WordHighlighter wordHighlighter: wordHighlighters) {
			sentence = wordHighlighter.highlightSentence(sentence);
		}
		return sentence;
	}

}
