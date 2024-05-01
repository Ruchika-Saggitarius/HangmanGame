package com.ruchika.hangman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GetGameStatisticsResponse;
import com.ruchika.hangman.responses.SaveGuessByUserResponse;

@Service
public interface IGameService {

    Game getNewGame(String userId);
    
    Game getGameByGameId(String userId, String gameId);

    List<Game> getAllGamesOfUser(String userId);

    SaveGuessByUserResponse saveGuessByUser(String userId, String gameId, GuessRequest guessRequest);

    void quitGame(String gameId);

    GetGameStatisticsResponse getGameStatistics();
    
}
