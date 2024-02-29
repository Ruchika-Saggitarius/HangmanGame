package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.Word;

public class GameByGameIdResponse {

    private String wordToDisplay;
    private String hint;
    private int remainingLives;
    private List<String> guessedAlphabets;

    public GameByGameIdResponse(String wordToGuess, String hint, int remainingLives,
            List<String> guessedAlphabets) {
        this.hint = hint;
        this.remainingLives = remainingLives;
        this.guessedAlphabets = guessedAlphabets;
    }

    public GameByGameIdResponse(Game game) {
        List<String> guessedAlphabets = game.getGuessedAlphabets();
        Word word = game.getWord();

        this.guessedAlphabets = game.getGuessedAlphabets();
        this.remainingLives = game.getRemainingLives();
        this.hint = word.getHint();
        this.wordToDisplay = word.getObscuredWord(guessedAlphabets);
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
}
