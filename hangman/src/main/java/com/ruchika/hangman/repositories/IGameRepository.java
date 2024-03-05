package com.ruchika.hangman.repositories;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Game;

@Service
public interface IGameRepository {
    
    Game saveGame(Game newGame);
    
    Game getGameByGameId(String gameId);

    List<Game> getAllGamesOfUser();

    Game saveGuessByUser(String guess, String gameId);

    Game quitGame(String gameId);

}
