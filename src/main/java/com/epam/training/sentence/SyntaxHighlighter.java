package com.epam.training.sentence;

import com.epam.training.exception.SyntaxHighlightingException;

public interface SyntaxHighlighter {

	String highlightThis(String sentence) throws SyntaxHighlightingException;
	
}
