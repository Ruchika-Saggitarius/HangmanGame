package com.ruchika.hangman.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.Word;
import com.ruchika.hangman.repositories.IGameRepository;
import com.ruchika.hangman.repositories.IWordRepository;
import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GameByGameIdResponse;
import com.ruchika.hangman.responses.GetAllGamesOfUserResponse;
import com.ruchika.hangman.responses.NewGameResponse;
import com.ruchika.hangman.responses.SaveGuessByUserResponse;

@RestController
public class GameController {

    @Autowired
    private IGameRepository mockGameRepository;
    @Autowired
    private IWordRepository mockWordRepository;

    @GetMapping("/game")
    public NewGameResponse getNewGame() {
        Word word = mockWordRepository.getRandomWord();
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId, word, 6, new ArrayList<String>());
        mockGameRepository.saveGame(game);
        return new NewGameResponse(game);
    }

    @GetMapping("/game/{gameId}")
    public GameByGameIdResponse getGameByGameId(@PathVariable String gameId) {
        Game game = mockGameRepository.getGameByGameId(gameId);
        return new GameByGameIdResponse(game);
    }

    @GetMapping("/games")
    public GetAllGamesOfUserResponse getAllGamesOfUser() {
        List<Game> games = mockGameRepository.getAllGamesOfUser();
        return new GetAllGamesOfUserResponse(games);
    }

    @PostMapping("/game/{gameId}/guess")
    public ResponseEntity<SaveGuessByUserResponse> saveGuessByUser(@PathVariable String gameId,
            @RequestBody GuessRequest guessRequest) {
        if (guessRequest.getGuess().length() != 1 || !Character.isLetter(guessRequest.getGuess().charAt(0))) {
            throw new BadRequestException("Only single alphabet is allowed as guess");
            
        }

        Game game = mockGameRepository.saveGuessByUser(guessRequest.getGuess(), gameId);
        return new ResponseEntity<SaveGuessByUserResponse>(new SaveGuessByUserResponse(game),HttpStatus.ACCEPTED);

    }
}
