package com.ruchika.hangman.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.GameStatistics;
import com.ruchika.hangman.model.GameStatus;
import com.ruchika.hangman.model.Word;
import com.ruchika.hangman.repositories.IGameRepository;
import com.ruchika.hangman.repositories.IUserRepository;
import com.ruchika.hangman.repositories.IWordRepository;
import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GameByGameIdResponse;
import com.ruchika.hangman.responses.GetAllGamesOfUserResponse;
import com.ruchika.hangman.responses.GetGameStatisticsResponse;
import com.ruchika.hangman.responses.NewGameResponse;
import com.ruchika.hangman.responses.SaveGuessByUserResponse;
import com.ruchika.hangman.model.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class GameController {

    @Autowired
    private IGameRepository mockGameRepository;
    @Autowired
    private IWordRepository mockWordRepository;
    @Autowired
    private IUserRepository mockUserRepository;

    @GetMapping("/game")
    public NewGameResponse getNewGame(HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        Word word;
        try {
            word = mockWordRepository.getRandomWord();
        } catch (IndexOutOfBoundsException e) {
            throw new BadRequestException("No words available. Admin needs to add words to play the game.");
        }
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId, word, 6, new ArrayList<String>(), userId, GameStatus.IN_PROGRESS);
        mockGameRepository.createGame(game);
        return new NewGameResponse(game);
    }

    @GetMapping("/game/{gameId}")
    public GameByGameIdResponse getGameByGameId(@PathVariable String gameId) {
        if (gameId.isEmpty()) {
            throw new BadRequestException("Invalid input. Please provide a valid game id.");
        }
        else {
            Game game = mockGameRepository.getGameByGameId(gameId);
            if (game == null) {
                throw new BadRequestException("Invalid game id. Please provide a valid game id.");
            }
            if (game.getGameStatus()!=GameStatus.IN_PROGRESS) {
                throw new BadRequestException("Game already over. Please start a new game.");
            }
            Word word = game.getWord();
            String wordToDisplay = word.getObscuredWord(game.getGuessedAlphabets());
            return new GameByGameIdResponse(wordToDisplay, game.getWord().getHint(), game.getRemainingLives(),
                    game.getGuessedAlphabets());
        }
    }

    @GetMapping("/games")
    public GetAllGamesOfUserResponse getAllGamesOfUser(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        List<Game> games = mockGameRepository.getAllGamesOfUser(userId);
        return new GetAllGamesOfUserResponse(games);
    }

    @PostMapping("/game/{gameId}/guess")
    public ResponseEntity<SaveGuessByUserResponse> saveGuessByUser(@PathVariable String gameId,
            @RequestBody GuessRequest guessRequest) {
        if (gameId.isEmpty() || guessRequest.getGuess().isEmpty()) {
            throw new BadRequestException("Invalid input. Please provide a valid game id and guess.");
        }
        Game requestedGame = mockGameRepository.getGameByGameId(gameId);
        if (requestedGame == null) {
            throw new BadRequestException("Invalid game id. Please provide a valid game id.");
        } else if (requestedGame.getGameStatus() != GameStatus.IN_PROGRESS){
            throw new BadRequestException("Game already over. Please start a new game.");
        } else if (guessRequest.getGuess().length() != 1 || !Character.isLetter(guessRequest.getGuess().charAt(0))) {
            throw new BadRequestException("Only single alphabet is allowed as guess");

        } else if (mockGameRepository.checkIfGuessAlreadyMade(gameId, guessRequest.getGuess().toLowerCase())) {
            throw new BadRequestException("Guess already made. Please provide a different guess.");
        } else {
            String guess = guessRequest.getGuess().toLowerCase();
            Game game = mockGameRepository.saveGuessByUser(guess, gameId);
            String wordState = game.getWord().getObscuredWord(game.getGuessedAlphabets());
            List<String> guessedAlphabets = game.getGuessedAlphabets();
            boolean isCorrectGuess = wordState.contains(guess);
            int remainingLives = game.getRemainingLives();
            GameStatus gameStatus;
            if (!isCorrectGuess) {
                remainingLives--;
            }
            if (wordState.equals(game.getWord().getWord())) {
                gameStatus = GameStatus.WON;
            } else if (remainingLives == 0) {
                gameStatus = GameStatus.LOST;
            } else {
                gameStatus = GameStatus.IN_PROGRESS;
            }
            game.setRemainingLives(remainingLives);
            game.setGameStatus(gameStatus);
            mockGameRepository.saveGame(gameId, game);
            return new ResponseEntity<SaveGuessByUserResponse>(new SaveGuessByUserResponse(wordState, remainingLives,
                    guessedAlphabets, isCorrectGuess, gameStatus), HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/game/{gameId}/quit")
    public void quitGame(@PathVariable String gameId) {
        if(gameId.isEmpty()) {
            throw new BadRequestException("Invalid input. Please provide a valid game id and guess.");
        }
        Game game = mockGameRepository.getGameByGameId(gameId);
        if(game == null) {
            throw new BadRequestException("Invalid game id. Please provide a valid game id.");
        }
        if(game.getGameStatus() != GameStatus.IN_PROGRESS) {
            throw new BadRequestException("Game is already over!!");
        }
        mockGameRepository.quitGame(gameId);
    }

    @GetMapping("/game-statistics")
    public ResponseEntity<GetGameStatisticsResponse> getGameStatistics() {

        List<User> users = mockUserRepository.getAllUsers();
        int totalGames = 0;
        int totalWins = 0;
        int totalLosses = 0;
        List<GameStatistics> gameStatisticsList = new ArrayList<>();
        for (User user : users) {
            List<Game> games = mockGameRepository.getAllGamesOfUser(user.getUserId());
            for (Game game : games) {
                totalGames++;
                if (game.getGameStatus() == GameStatus.WON) {
                    totalWins++;
                } else if (game.getGameStatus() == GameStatus.LOST) {
                    totalLosses++;
                }
            }
            GameStatistics gameStatistics = new GameStatistics(totalGames, totalWins, totalLosses, user.getDisplayName());
            gameStatisticsList.add(gameStatistics);
        }
        return new ResponseEntity<GetGameStatisticsResponse>(
                new GetGameStatisticsResponse(gameStatisticsList), HttpStatus.OK);
        
    }
}
