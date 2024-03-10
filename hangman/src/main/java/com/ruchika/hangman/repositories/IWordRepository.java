package com.ruchika.hangman.repositories;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Word;

@Service
public interface IWordRepository {

    Word getRandomWord();

    List<Word> getAllWords();

    void addWord(Word newWord);

    void deleteWord(String wordId);

    boolean checkIfWordExists(String word);

    boolean checkIfWordIdExists(String wordId); 
}
