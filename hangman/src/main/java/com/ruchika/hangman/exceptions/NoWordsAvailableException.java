package com.ruchika.hangman.exceptions;

public class NoWordsAvailableException extends RuntimeException {

    public NoWordsAvailableException(String message) {
        super(message);
    }

}

