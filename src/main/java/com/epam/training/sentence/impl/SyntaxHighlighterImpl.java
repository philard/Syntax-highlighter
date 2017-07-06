package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.ISyntaxHighlighter;
import com.epam.training.validation.ISentenceValidator;
import com.epam.training.word.IWordHighlighter;

import java.util.ArrayList;

public class SyntaxHighlighterImpl implements ISyntaxHighlighter {

	private ISentenceValidator sentenceValidatorImpl;

	public ISentenceValidator getSentenceValidatorImpl() {
		return sentenceValidatorImpl;
	}

	public void setSentenceValidatorImpl(ISentenceValidator sentenceValidatorImpl) {
		this.sentenceValidatorImpl = sentenceValidatorImpl;
	}

	private ArrayList<IWordHighlighter> wordHighlighters;

	public ArrayList<IWordHighlighter> getWordHighlighters() {
		return wordHighlighters;
	}

	public void setWordHighlighters(ArrayList<IWordHighlighter> wordHighlighters) {
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
