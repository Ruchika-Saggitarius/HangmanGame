package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Word;

@Service
public class MockWordRepository implements IWordRepository {

    List<Word> words = new ArrayList<Word>();

    MockWordRepository() {
        words.add(new Word("apple", "a fruit"));
        words.add(new Word("blue", "a color"));
    }

    @Override
    public Word getRandomWord() {
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }
    

}
