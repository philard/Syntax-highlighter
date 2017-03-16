package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.validation.impl.SentenceValidatorImpl;
import com.epam.training.word.IWordHighlighter;

import java.util.ArrayList;

public class SyntaxHighlighterImpl implements SyntaxHighlighter {

	private SentenceValidatorImpl sentenceValidatorImpl;

	public SentenceValidatorImpl getSentenceValidatorImpl() {
		return sentenceValidatorImpl;
	}

	public void setSentenceValidatorImpl(SentenceValidatorImpl sentenceValidatorImpl) {
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
			sentence = wordHighlighter.highlightThis(sentence);
		}
		return sentence;
	}

}
