package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruchika.hangman.exceptions.NoWordsAvailableException;
import com.ruchika.hangman.model.DatabaseRequestStatus;
import com.ruchika.hangman.model.Word;

@Repository
public class MockWordRepository implements IWordRepository {

    List<Word> words = new ArrayList<Word>();

    MockWordRepository() {
        words.add(new Word("1","apple", "a fruit"));
        words.add(new Word("2","blue", "a color"));
    }

    @Override
    public Word getRandomWord() throws NoWordsAvailableException {
        if(words.isEmpty()) {
            throw new NoWordsAvailableException("No words available");
        }
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    @Override
    public List<Word> getAllWords() {
        return words;
    }

    @Override
    public DatabaseRequestStatus addWord(Word newWord) {
        words.add(newWord);
        return DatabaseRequestStatus.SUCCESS;
    }

    @Override
    public DatabaseRequestStatus deleteWord(String wordId) {
        for (Word word : words) {
            if (word.getWordId().equals(wordId)) {
                words.remove(word);
                break;
            }
        }
        return DatabaseRequestStatus.SUCCESS;
    }

    @Override
    public boolean checkIfWordExists(String word) {
        for (Word w : words) {
            if (w.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfWordIdExists(String wordId) {
        for (Word w : words) {
            if (w.getWordId().equals(wordId)) {
                return true;
            }
        }
        return false;
    }

    
    

}
