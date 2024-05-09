package com.ruchika.hangman.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.RequestStatus;

@Repository
public interface IGameRepository {
    
    Game createGame(Game newGame);

    RequestStatus saveGame(String gameId, Game game);
    
    Game getGameByGameId(String gameId);

    List<Game> getAllGamesOfUser(String userId);

    Game saveGuessByUser(String guess, String gameId);

    RequestStatus quitGame(String gameId);

    boolean checkIfGuessAlreadyMade(String gameId, String lowerCase);

}
