package com.epam.training;

public interface SyntaxHighlighter {

	String highlightThis(String sentence) throws SyntaxHighlightingException;
	
}
