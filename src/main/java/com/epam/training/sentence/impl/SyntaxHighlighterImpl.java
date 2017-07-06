package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.ISyntaxHighlighter;
import com.epam.training.validation.ISentenceValidator;
import com.epam.training.word.IWordHighlighter;

import java.util.Collection;

public class SyntaxHighlighterImpl implements ISyntaxHighlighter {

    public SyntaxHighlighterImpl(ISentenceValidator sentenceValidatorImpl) {
        this.sentenceValidatorImpl = sentenceValidatorImpl;
    }
	
    private ISentenceValidator sentenceValidatorImpl;

	public ISentenceValidator getSentenceValidatorImpl() {
		return sentenceValidatorImpl;
	}

	private Collection<IWordHighlighter> wordHighlighters;

	public Collection<IWordHighlighter> getWordHighlighters() {
		return wordHighlighters;
	}

	public void setWordHighlighters(Collection<IWordHighlighter> wordHighlighters) {
		this.wordHighlighters = wordHighlighters;
	}

	public String highlightThis(String sentence) throws SyntaxHighlightingException {

		sentenceValidatorImpl.validate(sentence);

		for(IWordHighlighter wordHighlighter: wordHighlighters) {
			sentence = wordHighlighter.highlightSentence(sentence);
		}
		return sentence;
	}

}
