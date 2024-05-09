package com.ruchika.hangman.requests;

public class GuessRequest {

    private String guess;

    public GuessRequest() {
    }

    public GuessRequest(String guess) {
        this.guess = guess;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
    
}
