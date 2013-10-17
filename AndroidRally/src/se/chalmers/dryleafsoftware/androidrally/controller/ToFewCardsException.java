package se.chalmers.dryleafsoftware.androidrally.controller;

public class ToFewCardsException extends RuntimeException {
	public ToFewCardsException() {
		super();
	}

	public ToFewCardsException(String message) {
		super(message);
	}
}