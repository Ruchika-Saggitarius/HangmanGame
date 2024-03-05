package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.Game;

public class SaveGuessByUserResponse {

    private String wordState;
    private int remainingLives;
    private List<String> guessedAlphabets;
    private boolean isCorrectGuess;

    public SaveGuessByUserResponse(String wordState, int remainingLives, List<String> guessedAlphabets, boolean isCorrectGuess) {
        this.wordState = wordState;
        this.remainingLives = remainingLives;
        this.guessedAlphabets = guessedAlphabets;
        this.isCorrectGuess = isCorrectGuess;
    }

    public SaveGuessByUserResponse(Game game) {
        this.wordState = game.getWord().getObscuredWord(game.getGuessedAlphabets());
        this.guessedAlphabets = game.getGuessedAlphabets();
        this.isCorrectGuess = game.getWord().getObscuredWord(game.getGuessedAlphabets()).contains(game.getGuessedAlphabets().get(game.getGuessedAlphabets().size() - 1));
        if(this.isCorrectGuess) {
            this.remainingLives = game.getRemainingLives();
        } else {
            this.remainingLives = game.getRemainingLives() - 1;
        }
        game.setRemainingLives(this.remainingLives);
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
    
}
