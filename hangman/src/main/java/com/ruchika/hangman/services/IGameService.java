package com.ruchika.hangman.services;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GameByGameIdResponse;
import com.ruchika.hangman.responses.GetAllGamesOfUserResponse;
import com.ruchika.hangman.responses.GetGameStatisticsResponse;
import com.ruchika.hangman.responses.NewGameResponse;
import com.ruchika.hangman.responses.SaveGuessByUserResponse;

@Service
public interface IGameService {

    NewGameResponse getNewGame(String userId);
    
    GameByGameIdResponse getGameByGameId(String userId, String gameId);

    GetAllGamesOfUserResponse getAllGamesOfUser(String userId);

    SaveGuessByUserResponse saveGuessByUser(String userId, String gameId, GuessRequest guessRequest);

    void quitGame(String gameId);

    GetGameStatisticsResponse getGameStatistics();
    
}
