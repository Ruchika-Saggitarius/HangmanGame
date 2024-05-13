package com.ruchika.hangman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.exceptions.NoWordsAvailableException;
import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.GameStatistics;
import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GuessResponse;

@Service
public interface IGameService {

    Game getNewGame(String userId) throws NoWordsAvailableException;
    
    Game getGameByGameId(String userId, String gameId) throws InvalidInputException;

    List<Game> getAllGamesOfUser(String userId);

    GuessResponse saveGuessByUser(String userId, String gameId, GuessRequest guessRequest) throws InvalidInputException;

    Game quitGame(String userId, String gameId) throws InvalidInputException;

    List<GameStatistics> getGameStatistics();
    
}
