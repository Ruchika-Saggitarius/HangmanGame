package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.Game;

public class GetAllGamesOfUserResponse {
    
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
