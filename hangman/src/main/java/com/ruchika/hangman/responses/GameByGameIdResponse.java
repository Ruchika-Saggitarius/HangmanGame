package com.ruchika.hangman.responses;

import java.util.List;



public class GameByGameIdResponse {

    private String wordToDisplay;
    private String hint;
    private int remainingLives;
    private List<String> guessedAlphabets;
    private int totalScore;

    public GameByGameIdResponse(String wordToDisplay, String hint, int remainingLives,
            List<String> guessedAlphabets, int totalScore) {
        this.wordToDisplay = wordToDisplay;
        this.hint = hint;
        this.remainingLives = remainingLives;
        this.guessedAlphabets = guessedAlphabets;
        this.totalScore = totalScore;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public List<String> getGuessedAlphabets() {
        return guessedAlphabets;
    }

    public void setGuessedAlphabets(List<String> guessedAlphabets) {
        this.guessedAlphabets = guessedAlphabets;
    }

    public String getWordToDisplay() {
        return wordToDisplay;
    }

    public void setWordToDisplay(String wordToDisplay) {
        this.wordToDisplay = wordToDisplay;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

}
