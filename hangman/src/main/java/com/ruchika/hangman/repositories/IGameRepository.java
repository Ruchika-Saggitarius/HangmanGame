package com.ruchika.hangman.repositories;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Game;

@Service
public interface IGameRepository {
    
    Game createGame(Game newGame);

    void saveGame(String gameId, Game game);
    
    Game getGameByGameId(String gameId);

    List<Game> getAllGamesOfUser(String userId);

    Game saveGuessByUser(String guess, String gameId);

    void quitGame(String gameId);

    boolean checkIfGuessAlreadyMade(String gameId, String lowerCase);

}
