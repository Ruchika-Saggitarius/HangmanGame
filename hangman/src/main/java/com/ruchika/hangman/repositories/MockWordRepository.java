package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Word;

@Service
public class MockWordRepository implements IWordRepository {

    List<Word> words = new ArrayList<Word>();

    MockWordRepository() {
        words.add(new Word("1","apple", "a fruit"));
        words.add(new Word("2","blue", "a color"));
    }

    @Override
    public Word getRandomWord() {
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    @Override
    public List<Word> getAllWords() {
        return words;
    }

    @Override
    public void addWord(Word newWord) {
        words.add(newWord);
    }

    @Override
    public void deleteWord(String wordId) {
        for (Word word : words) {
            if (word.getWordId().equals(wordId)) {
                words.remove(word);
                break;
            }
        }
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
