package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.GameStatus;

public class SaveGuessByUserResponse {

    private String wordState;
    private int remainingLives;
    private List<String> guessedAlphabets;
    private boolean isCorrectGuess;
    private GameStatus gameStatus;
    private int score;

    public SaveGuessByUserResponse(String wordState, int remainingLives, List<String> guessedAlphabets,
            boolean isCorrectGuess, GameStatus gameStatus, int score) {
        this.wordState = wordState;
        this.remainingLives = remainingLives;
        this.guessedAlphabets = guessedAlphabets;
        this.isCorrectGuess = isCorrectGuess;
        this.gameStatus = gameStatus;
        this.score = score;
    }

    public String getWordState() {
        return wordState;
    }

    public void setWordState(String wordState) {
        this.wordState = wordState;
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

    public boolean isCorrectGuess() {
        return isCorrectGuess;
    }

    public void setCorrectGuess(boolean isCorrectGuess) {
        this.isCorrectGuess = isCorrectGuess;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
