package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.Game;

public class GuessResponse {

    private Game game;
    private boolean isCorrectGuess;

    public GuessResponse(Game game, boolean isCorrectGuess) {
        this.game = game;
        this.isCorrectGuess = isCorrectGuess;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isCorrectGuess() {
        return isCorrectGuess;
    }

    public void setCorrectGuess(boolean isCorrectGuess) {
        this.isCorrectGuess = isCorrectGuess;
    }
    
}
