package com.epam.training;

public class SyntaxHighlightingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SyntaxHighlightingException() {
		super();
	}

	public SyntaxHighlightingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SyntaxHighlightingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxHighlightingException(String message) {
		super(message);
	}

	public SyntaxHighlightingException(Throwable cause) {
		super(cause);
	}

}
