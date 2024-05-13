package com.ruchika.hangman.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.exceptions.NoWordsAvailableException;
import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.GameStatistics;
import com.ruchika.hangman.model.GameStatus;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.model.Word;
import com.ruchika.hangman.repositories.IGameRepository;
import com.ruchika.hangman.repositories.IUserRepository;
import com.ruchika.hangman.repositories.IWordRepository;
import com.ruchika.hangman.requests.GuessRequest;
import com.ruchika.hangman.responses.GuessResponse;

@Service
public class GameService implements IGameService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IWordRepository wordRepository;

    @Autowired
    private IGameRepository gameRepository;

    @Override
    public Game getNewGame(String userId) throws NoWordsAvailableException{
        Word word;
        word = wordRepository.getRandomWord();
        String gameId = UUID.randomUUID().toString();
        int score = 0;
        Game game = new Game(gameId, word, 6, new ArrayList<String>(), userId, GameStatus.IN_PROGRESS, score);
        game = gameRepository.createGame(game);
        return game;
    }

    @Override
    public Game getGameByGameId(String userId, String gameId) throws InvalidInputException{
        if (gameId.isEmpty()) {
            throw new InvalidInputException("Invalid input. Please provide a valid game id.");
        }
            Game game = gameRepository.getGameByGameId(gameId);
            if (game == null) {
                throw new InvalidInputException("Invalid game id. Please provide a valid game id.");
            }
            return game;
    }

    @Override
    public List<Game> getAllGamesOfUser(String userId) {
        List<Game> games = gameRepository.getAllGamesOfUser(userId);
        return games;
    }

    @Override
    public GuessResponse saveGuessByUser(String userId, String gameId, GuessRequest guessRequest) throws InvalidInputException{
        
        if (gameId.isEmpty() || guessRequest.getGuess().isEmpty()) {
            throw new InvalidInputException("Invalid input. Please provide a valid game id and guess.");
        }
        Game requestedGame = gameRepository.getGameByGameId(gameId);
        if (requestedGame == null) {
            throw new InvalidInputException("Invalid game id. Please provide a valid game id.");
        } else if (requestedGame.getGameStatus() != GameStatus.IN_PROGRESS) {
            throw new InvalidInputException("Game already over. Please start a new game.");
        } else if (guessRequest.getGuess().length() != 1 || !Character.isLetter(guessRequest.getGuess().charAt(0))) {
            throw new InvalidInputException("Only single alphabet is allowed as guess");
        } else if (gameRepository.checkIfGuessAlreadyMade(gameId, guessRequest.getGuess().toLowerCase())) {
            throw new InvalidInputException("Guess already made. Please provide a different guess.");
        } else {
            String guess = guessRequest.getGuess().toLowerCase();
            Game game = gameRepository.saveGuessByUser(guess, gameId);
            String wordState = game.getWord().getObscuredWord(game.getGuessedAlphabets());
            boolean isCorrectGuess = wordState.contains(guess);
            int remainingLives = game.getRemainingLives();
            int score = game.getScore();
            GameStatus gameStatus;
            if (isCorrectGuess) {
                score += 10;
            }
            if (!isCorrectGuess) {
                remainingLives--;
                score -= 10;
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
            game.setScore(score);
            gameRepository.saveGame(gameId, game);
            return new GuessResponse(game, isCorrectGuess);
        }
    }

    @Override
    public Game quitGame(String userId, String gameId) throws InvalidInputException{
        if (gameId.isEmpty()) {
            throw new InvalidInputException("Invalid input. Please provide a valid game id and guess.");
        }
        Game game = gameRepository.getGameByGameId(gameId);
        if (game == null) {
            throw new InvalidInputException("Invalid game id. Please provide a valid game id.");
        }
        if (game.getGameStatus() != GameStatus.IN_PROGRESS) {
            throw new InvalidInputException("Game is already over!!");
        }
        gameRepository.quitGame(userId, gameId);
        return game;
    }

    @Override
    public List<GameStatistics> getGameStatistics() {
        List<User> users = userRepository.getAllUsers();
        List<GameStatistics> gameStatisticsList = new ArrayList<>();
        for (User user : users) {
            int totalGames = 0;
            int totalWins = 0;
            int totalLosses = 0;
            int totalScore = 0;
            List<Game> games = gameRepository.getAllGamesOfUser(user.getUserId());
            for (Game game : games) {
                totalGames++;
                if (game.getGameStatus() == GameStatus.WON) {
                    totalWins++;
                } else if (game.getGameStatus() == GameStatus.LOST) {
                    totalLosses++;
                }
                totalScore += game.getScore();
            }
            GameStatistics gameStatistics = new GameStatistics(totalGames, totalWins, totalLosses,
                    user.getDisplayName(), totalScore);
            gameStatisticsList.add(gameStatistics);
        }
        return gameStatisticsList;

    }

}
