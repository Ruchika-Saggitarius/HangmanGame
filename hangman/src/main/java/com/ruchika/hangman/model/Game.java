package com.ruchika.hangman.model;

import java.util.List;

public class Game {

    private String gameId;
    private Word word;
    private int remainingLives;
    private List<String> guessedAlphabets;

    public Game(String gameId, Word word, int remainingLives, List<String> guessedAlphabets) {
        this.gameId = gameId;
        this.word = word;
        this.remainingLives = remainingLives;
        this.guessedAlphabets = guessedAlphabets;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public void addGuess(String guess) {
        guessedAlphabets.add(guess);
    }
}
