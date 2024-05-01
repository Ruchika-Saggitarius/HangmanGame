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

import com.ruchika.hangman.model.Word;

@Repository
@Primary
public class MySQLWordRepository implements IWordRepository{

    Connection connection;
    PreparedStatement statement;
    DataSource dataSource;

    public MySQLWordRepository(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        connection = dataSource.getConnection();
    }


    @Override
    public Word getRandomWord() {
        try {
            statement = connection.prepareStatement("SELECT * FROM word ORDER BY RAND() LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Word(resultSet.getString("wordId"), resultSet.getString("word"), resultSet.getString("hint"));
            }
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
    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM word");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                words.add(new Word(resultSet.getString("wordId"), resultSet.getString("word"), resultSet.getString("hint")));
            }
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
        return words;
    }

    @Override
    public void addWord(Word newWord) {
        try {
            statement = connection.prepareStatement("INSERT INTO word (wordId, word, hint) VALUES (?, ?, ?)");
            statement.setString(1, newWord.getWordId());
            statement.setString(2, newWord.getWord());
            statement.setString(3, newWord.getHint());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWord(String wordId) {
        try {
            statement = connection.prepareStatement("DELETE FROM word WHERE wordId = ?");
            statement.setString(1, wordId);
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
    public boolean checkIfWordExists(String word) {
        try {
            statement = connection.prepareStatement("SELECT * FROM word WHERE word = ?");
            statement.setString(1, word);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
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

    @Override
    public boolean checkIfWordIdExists(String wordId) {
        try {
            statement = connection.prepareStatement("SELECT * FROM word WHERE wordId = ?");
            statement.setString(1, wordId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
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
