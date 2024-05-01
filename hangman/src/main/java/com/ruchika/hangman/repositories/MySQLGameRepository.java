package com.ruchika.hangman.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.ruchika.hangman.model.Game;
import com.ruchika.hangman.model.GameStatus;
import com.ruchika.hangman.model.Word;

@Repository
@Primary

public class MySQLGameRepository implements IGameRepository{

    Connection connection;
    PreparedStatement statement;
    DataSource dataSource;  

    public MySQLGameRepository(DataSource dataSource) throws SQLException {

        this.dataSource = dataSource;
        connection = dataSource.getConnection();
    }

    @Override
    public Game createGame(Game newGame) {
        try {
            statement = connection.prepareStatement("INSERT INTO game (gameId, wordId, remainingLives, userId, gameStatus, score) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, newGame.getGameId());
            statement.setString(2, newGame.getWord().getWordId());
            statement.setInt(3, newGame.getRemainingLives());
            statement.setString(4, newGame.getUserId());
            statement.setString(5, newGame.getGameStatus().toString());
            statement.setInt(6, newGame.getScore());
            statement.executeUpdate();
            return newGame;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
        return null;
      
    }

    @Override
    public void saveGame(String gameId, Game game) {
        try{
            statement = connection.prepareStatement("UPDATE game SET remainingLives = ?, gameStatus = ?, score = ? WHERE gameId = ?");
            statement.setInt(1, game.getRemainingLives());
            statement.setString(2, game.getGameStatus().toString());
            statement.setInt(3, game.getScore());
            statement.setString(4, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
    }

    @Override
    public Game getGameByGameId(String gameId) {
        try{

            statement = connection.prepareStatement("SELECT W.word, W.hint, W.wordId, G.remainingLives, G.userId, G.gameStatus, G.score, GROUP_CONCAT(GU.guess SEPARATOR ',') AS Guesses from game G LEFT JOIN word W ON G.wordId = W.wordId LEFT JOIN Guess GU ON G.gameId = GU.gameId WHERE G.gameId = ? GROUP BY G.gameId");
            statement.setString(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                    String word = resultSet.getString("word");
                    String hint = resultSet.getString("hint");
                    String wordId = resultSet.getString("wordId");
                    Word wordObject = new Word(wordId, word, hint);
                    String guesses = resultSet.getString("Guesses");
                    List<String> guessedAlphabetsList = new ArrayList<>();
                    if(guesses != null){
                        String[] guessedAlphabets = guesses.split(",");
                        for(String guess: guessedAlphabets){
                            guessedAlphabetsList.add(guess);
                        }
                    }
                return new Game(resultSet.getString("gameId"), wordObject, resultSet.getInt("remainingLives"), 
                guessedAlphabetsList, resultSet.getString("userId"), GameStatus.valueOf(resultSet.getString("gameStatus")),
             resultSet.getInt("score"));
                }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
        return null;
    }

    @Override
    public List<Game> getAllGamesOfUser(String userId) {
        try{

            statement = connection.prepareStatement("SELECT W.wordId, W.word, W.hint, G.remainingLives, G.gameId, G.gameStatus, G.score, GROUP_CONCAT(GU.guess SEPARATOR ',') AS Guesses from game G LEFT JOIN word W ON G.wordId = W.wordId LEFT JOIN Guess GU ON G.gameId = GU.gameId WHERE G.userId = ? GROUP BY GU.gameId");
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Game> games = new ArrayList<>();
            while(resultSet.next()){
                String word = resultSet.getString("word");
                String hint = resultSet.getString("hint");
                String wordId = resultSet.getString("wordId");
                Word wordObject = new Word(wordId, word, hint);
                String guesses = resultSet.getString("Guesses");
                List<String> guessedAlphabetsList = new ArrayList<>();
                if(guesses != null){
                    String[] guessedAlphabets = guesses.split(",");
                    for(String guess: guessedAlphabets){
                        guessedAlphabetsList.add(guess);
                    }
                }
                Game game = new Game(resultSet.getString("gameId"), wordObject, resultSet.getInt("remainingLives"), 
                guessedAlphabetsList, userId, GameStatus.valueOf(resultSet.getString("gameStatus")), 
                resultSet.getInt("score"));
                games.add(game);
            }
            return games;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
        return null;
    }

    @Override
    public Game saveGuessByUser(String guess, String gameId) {
        try{
            statement = connection.prepareStatement("SELECT * FROM game WHERE gameId = ?");
            statement.setString(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                statement = connection.prepareStatement("Insert into guess (gameId, guess) VALUES (?, ?)");
                statement.setString(1, gameId);
                statement.setString(2, guess);
                statement.executeUpdate();
                return getGameByGameId(gameId);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
        return null;
    }

    @Override
    public void quitGame(String gameId) {
        try{
            statement = connection.prepareStatement("UPDATE game SET gameStatus = ? WHERE gameId = ?");
            statement.setString(1, GameStatus.QUIT.toString());
            statement.setString(2, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    }
    }

    @Override
    public boolean checkIfGuessAlreadyMade(String gameId, String lowerCase) {
        try{
            statement = connection.prepareStatement("SELECT * FROM guess WHERE gameId = ? AND guess = ?");
            statement.setString(1, gameId);
            statement.setString(2, lowerCase);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
    }
    finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
}
        return false;
    
}
}
