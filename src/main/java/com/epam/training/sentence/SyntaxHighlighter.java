package com.epam.training.sentence;

import com.epam.training.SyntaxHighlightingException;

public interface SyntaxHighlighter {

	String highlightThis(String sentence) throws SyntaxHighlightingException;
	
}
