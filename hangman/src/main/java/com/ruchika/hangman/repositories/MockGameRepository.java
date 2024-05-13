package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruchika.hangman.model.DatabaseRequestStatus;
import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.GameStatus;

@Repository
public class MockGameRepository implements IGameRepository {

    List<Game> games;

    public MockGameRepository() {
        games = new ArrayList<>();
    }

    @Override
    public Game createGame(Game newGame) {
        games.add(newGame);
        return newGame;
    }

    @Override
    public DatabaseRequestStatus saveGame(String gameId, Game updatedGame) {
        for (int i=0; i<games.size();i++) {
            if (games.get(i).getGameId().equals(gameId)) {
                games.set(i, updatedGame);
            }
        }
        return DatabaseRequestStatus.SUCCESS;
    }

    @Override
    public Game getGameByGameId(String gameId) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }
        return null;
    }

    @Override
    public List<Game> getAllGamesOfUser(String userId) {
        List<Game> userGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getUserId().equals(userId)) {
                userGames.add(game);
            }
        }
        return userGames;
    }

    @Override
    public Game saveGuessByUser(String guess, String gameId) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                game.addGuess(guess);
                return game;
            }
        }
        return null;
    }

    @Override
    public DatabaseRequestStatus quitGame(String userId, String gameId) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                if(game.getUserId().equals(userId)) {
                    game.setGameStatus(GameStatus.QUIT);
                    return DatabaseRequestStatus.SUCCESS;
                }
            }
        }
        return DatabaseRequestStatus.FAILURE;
    }

    @Override
    public boolean checkIfGuessAlreadyMade(String gameId, String lowerCase) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                if (game.getGuessedAlphabets().contains(lowerCase)) {
                    return true;
                }
            }
        }
        return false;

    }

}
