package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.Game;

public class NewGameResponse{

    private String gameId;

    public NewGameResponse(Game game) {
        this.gameId = game.getGameId();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
