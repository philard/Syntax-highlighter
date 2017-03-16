package com.epam.training.sentence;

import com.epam.training.exception.SyntaxHighlightingException;

public interface ISyntaxHighlighter {

	String highlightThis(String sentence) throws SyntaxHighlightingException;
	
}
