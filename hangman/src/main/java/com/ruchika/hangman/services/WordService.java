package com.ruchika.hangman.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.model.Word;
import com.ruchika.hangman.repositories.IWordRepository;
import com.ruchika.hangman.requests.AddWordRequest;

@Service
public class WordService implements IWordService {

    @Autowired
    private IWordRepository wordRepository;

    @Override
    public List<Word> getAllWords() {
        return wordRepository.getAllWords();
    }

    @Override
    public void addWord(AddWordRequest addWordRequest) {
          if(addWordRequest.getWord().isEmpty() || addWordRequest.getHint().isEmpty()){
            throw new BadRequestException("Invalid input. Please provide all the required fields.");
        }
        else if(addWordRequest.getWord().length()<5){
            throw new BadRequestException("Word should be atleast 5 characters long.");
        }
        else if(addWordRequest.getHint().length()<5){
            throw new BadRequestException("Hint should be atleast 5 characters long.");
        }
        else if(addWordRequest.getWord().matches("^[a-zA-Z ]+$")==false){
            throw new BadRequestException("Invalid word. Please provide a valid word containing only alphabets and spaces.");
        }
        else if(wordRepository.checkIfWordExists(addWordRequest.getWord())) {
            throw new BadRequestException("Word already exists. Please provide a different word.");
        }
        String wordId = UUID.randomUUID().toString();
        wordRepository.addWord(new Word(wordId, addWordRequest.getWord().toLowerCase(), addWordRequest.getHint()));
        
    }

    @Override
    public void deleteWord(String wordId) {
        if(wordId.isEmpty()){
            throw new BadRequestException("Invalid input. Please provide a valid word id.");
        }
        else if(wordRepository.checkIfWordIdExists(wordId)==false){
            throw new BadRequestException("Invalid word id. Please provide a valid word id.");
        }
        wordRepository.deleteWord(wordId);
        
    }
    
}
