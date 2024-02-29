package com.ruchika.hangman.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.Word;
import com.ruchika.hangman.repositories.IGameRepository;
import com.ruchika.hangman.repositories.IWordRepository;
import com.ruchika.hangman.responses.GameByGameIdResponse;
import com.ruchika.hangman.responses.NewGameResponse;

import jakarta.websocket.server.PathParam;

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
        Game game = new Game(gameId, word, 6, List.of());
        mockGameRepository.saveGame(game);
        return new NewGameResponse(game);
    }

    @GetMapping("/game/{gameId}")
    public GameByGameIdResponse getGameByGameId(@PathVariable String gameId) {
        Game game = mockGameRepository.getGameByGameId(gameId);
        return new GameByGameIdResponse(game);
    }

}
