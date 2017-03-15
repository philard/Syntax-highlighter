package com.epam.training;

import java.util.ArrayList;

public class SyntaxHighlighterImpl implements SyntaxHighlighter{

	public SentenceValidator getSentenceValidator() {
		return sentenceValidator;
	}

	public void setSentenceValidator(SentenceValidator sentenceValidator) {
		this.sentenceValidator = sentenceValidator;
	}

	private SentenceValidator sentenceValidator;

	public ArrayList<IWordHighlighter> getWordHighlighters() {
		return wordHighlighters;
	}

	public void setWordHighlighters(ArrayList<IWordHighlighter> wordHighlighters) {
		this.wordHighlighters = wordHighlighters;
	}

	private ArrayList<IWordHighlighter> wordHighlighters;

	public String highlightThis(String sentence) throws SyntaxHighlightingException {

		//Validation
		sentenceValidator.validate(sentence);

		for(IWordHighlighter wordHighlighter: wordHighlighters) {
			sentence = wordHighlighter.highlightThis(sentence);
		}

		String highlighted = sentence;
		return highlighted;
	}

}
