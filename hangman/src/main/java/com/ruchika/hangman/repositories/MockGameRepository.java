package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Game;

@Service
public class MockGameRepository implements IGameRepository {

    List<Game> games;
    public MockGameRepository() {
        games = new ArrayList<>();
    }

    @Override
    public Game saveGame(Game newGame) {
        games.add(newGame);
        return newGame;
    }

    @Override
    public Game getGameByGameId(String gameId) {
        for (Game game : games) {
            if(game.getGameId().equals(gameId)){
                return game;
            }
        }
        return null;
    }

    @Override
    public List<Game> getAllGamesOfUser(String userId) {
        List<Game> userGames = new ArrayList<>();
        for (Game game : games) {
            if(game.getUserId().equals(userId)){
                userGames.add(game);
            }
        }
        return userGames;
    }

    @Override
    public Game saveGuessByUser(String guess, String gameId) {
        for (Game game : games) {
            if(game.getGameId().equals(gameId)){
                game.addGuess(guess);
                return game;
            }
        }
        return null;
    }

    @Override
    public Game quitGame(String gameId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean checkIfGuessAlreadyMade(String gameId, String lowerCase) {
        for (Game game : games) {
            if(game.getGameId().equals(gameId)){
                if(game.getGuessedAlphabets().contains(lowerCase)){
                    return true;
                }
            }
        }
        return false;
    }
    
}
