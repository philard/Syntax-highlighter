package com.epam.training;

import java.util.ArrayList;

public class SyntaxHighlighterImpl implements SyntaxHighlighter{

	private SentenceValidator sentenceValidator;

	private ArrayList<IWordHighlighter> wordHighlighters;

	public String highlightThis(String sentence) throws SyntaxHighlightingException {

		//Context setup
		ArrayList<IWordHighlighter> wordHighlighters = new ArrayList();
		wordHighlighters.add(new SimpleWordHighlighter("[bold]", " am ", "[/bold]"));
		sentenceValidator = new SentenceValidator();


		//Validation
		sentenceValidator.validate(sentence);

		for(IWordHighlighter wordHighlighter: wordHighlighters) {
			wordHighlighter.highlight(sentence);
		}

		String highlighted = sentence;
		return highlighted;
	}

}
