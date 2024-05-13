package com.ruchika.hangman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.model.Word;
import com.ruchika.hangman.requests.AddWordRequest;

@Service
public interface IWordService {

    List<Word> getAllWords();

    Word addWord(AddWordRequest addWordRequest) throws InvalidInputException;

    String deleteWord(String wordId) throws InvalidInputException;    
}
