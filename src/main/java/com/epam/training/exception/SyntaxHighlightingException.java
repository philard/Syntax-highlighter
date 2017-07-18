package com.epam.training.exception;

public class SyntaxHighlightingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SyntaxHighlightingException() {
		super();
	}

	public SyntaxHighlightingException(String message) {
		super(message);
	}

}
