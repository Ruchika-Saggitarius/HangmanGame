package com.ruchika.hangman.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.ruchika.hangman.exceptions.NoWordsAvailableException;
import com.ruchika.hangman.model.RequestStatus;
import com.ruchika.hangman.model.Word;

@Repository
@Primary
public class MySQLWordRepository implements IWordRepository{

    Connection connection;
    PreparedStatement statement;
    DataSource dataSource;
    
    private static final Logger logger = LogManager.getLogger(MySQLWordRepository.class);

    public MySQLWordRepository(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        connection = dataSource.getConnection();
    }


    @Override
    public Word getRandomWord() throws NoWordsAvailableException {
        try {
            statement = connection.prepareStatement("SELECT * FROM word ORDER BY RAND() LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                logger.info("Word found");
                return new Word(resultSet.getString("wordId"), resultSet.getString("word"), resultSet.getString("hint"));
            }
            else {
                logger.error("No words available");
                throw new NoWordsAvailableException("No words available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error in getting random word");
            throw new RuntimeException("Error in getting random word");
        }
    }

    @Override
    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM word");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                words.add(new Word(resultSet.getString("wordId"), resultSet.getString("word"), resultSet.getString("hint")));
            }
            logger.info("All words fetched");
            return words;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error in getting all words");
            throw new RuntimeException("Error in getting all words");
        }
        
    }

    @Override
    public RequestStatus addWord(Word newWord) {
        try {
            statement = connection.prepareStatement("INSERT INTO word (wordId, word, hint) VALUES (?, ?, ?)");
            statement.setString(1, newWord.getWordId());
            statement.setString(2, newWord.getWord());
            statement.setString(3, newWord.getHint());
            statement.executeUpdate();
            logger.info("Word added successfully");
            return RequestStatus.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error in adding word");
            throw new RuntimeException("Error in adding word");
        }
        
    }

    @Override
    public RequestStatus deleteWord(String wordId) {
        try {
            statement = connection.prepareStatement("DELETE FROM word WHERE wordId = ?");
            statement.setString(1, wordId);
            statement.executeUpdate();
            logger.info("Word deleted successfully");
            return RequestStatus.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error in deleting word");
            throw new RuntimeException("Error in deleting word");
        }
        
    }

    @Override
    public boolean checkIfWordExists(String word) {
        try {
            statement = connection.prepareStatement("SELECT * FROM word WHERE word = ?");
            statement.setString(1, word);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error in checking if word exists");
            throw new RuntimeException("Error in checking if word exists");
        }
    }

    @Override
    public boolean checkIfWordIdExists(String wordId) {
        try {
            statement = connection.prepareStatement("SELECT * FROM word WHERE wordId = ?");
            statement.setString(1, wordId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error in checking if wordId exists");
            throw new RuntimeException("Error in checking if wordId exists");
        }
    }
}
