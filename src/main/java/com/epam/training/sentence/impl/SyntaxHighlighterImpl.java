package com.epam.training.sentence.impl;

import com.epam.training.exception.SyntaxHighlightingException;
import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.validation.impl.SentenceValidator;
import com.epam.training.word.IWordHighlighter;

import java.util.ArrayList;

public class SyntaxHighlighterImpl implements SyntaxHighlighter {

	private SentenceValidator sentenceValidator;

	public SentenceValidator getSentenceValidator() {
		return sentenceValidator;
	}

	public void setSentenceValidator(SentenceValidator sentenceValidator) {
		this.sentenceValidator = sentenceValidator;
	}

	private ArrayList<IWordHighlighter> wordHighlighters;

	public ArrayList<IWordHighlighter> getWordHighlighters() {
		return wordHighlighters;
	}

	public void setWordHighlighters(ArrayList<IWordHighlighter> wordHighlighters) {
		this.wordHighlighters = wordHighlighters;
	}

	public String highlightThis(String sentence) throws SyntaxHighlightingException {

		sentenceValidator.validate(sentence);

		for(IWordHighlighter wordHighlighter: wordHighlighters) {
			sentence = wordHighlighter.highlightThis(sentence);
		}
		return sentence;
	}

}
