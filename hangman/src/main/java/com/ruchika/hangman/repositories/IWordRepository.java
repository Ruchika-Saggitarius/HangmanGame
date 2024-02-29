package com.ruchika.hangman.repositories;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.Word;

@Service
public interface IWordRepository {

    Word getRandomWord();
    
}
