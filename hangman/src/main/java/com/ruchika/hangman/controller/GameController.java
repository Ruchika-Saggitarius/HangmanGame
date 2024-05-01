package com.ruchika.hangman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GameByGameIdResponse;
import com.ruchika.hangman.responses.GetAllGamesOfUserResponse;
import com.ruchika.hangman.responses.GetGameStatisticsResponse;
import com.ruchika.hangman.responses.NewGameResponse;
import com.ruchika.hangman.responses.SaveGuessByUserResponse;
import com.ruchika.hangman.services.IGameService;
import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class GameController {

    @Autowired
    private IGameService gameService;

    @GetMapping("/game")
    public ResponseEntity<NewGameResponse> getNewGame(HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        Game newGame = gameService.getNewGame(userId);
        return new ResponseEntity<NewGameResponse>(new NewGameResponse(newGame), HttpStatus.OK);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<GameByGameIdResponse> getGameByGameId(@PathVariable String gameId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        Game game = gameService.getGameByGameId(userId, gameId);
        return new ResponseEntity<GameByGameIdResponse>(new GameByGameIdResponse(game.getWord().getObscuredWord(game.getGuessedAlphabets()),
                 game.getWord().getHint(),game.getRemainingLives(), game.getGuessedAlphabets(), game.getScore()), HttpStatus.OK);
        
    }

    @GetMapping("/games")
    public ResponseEntity<GetAllGamesOfUserResponse> getAllGamesOfUser(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        List<Game> userGames = gameService.getAllGamesOfUser(userId);
        return new ResponseEntity<GetAllGamesOfUserResponse>(new GetAllGamesOfUserResponse(userGames), HttpStatus.OK);
    }

    @PostMapping("/game/{gameId}/guess")
    public ResponseEntity<SaveGuessByUserResponse> saveGuessByUser(@PathVariable String gameId,
            @RequestBody GuessRequest guessRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        SaveGuessByUserResponse gameResponse = gameService.saveGuessByUser(userId, gameId, guessRequest);
        return new ResponseEntity<SaveGuessByUserResponse>(gameResponse, HttpStatus.OK);
    }

    @PostMapping("/game/{gameId}/quit")
    public void quitGame(@PathVariable String gameId) {
        gameService.quitGame(gameId);
    }

    @GetMapping("/game-statistics")
    public ResponseEntity<GetGameStatisticsResponse> getGameStatistics() {
        GetGameStatisticsResponse gameStatisticsResponse = gameService.getGameStatistics();
        return new ResponseEntity<GetGameStatisticsResponse>(gameStatisticsResponse, HttpStatus.OK);
    }
}
