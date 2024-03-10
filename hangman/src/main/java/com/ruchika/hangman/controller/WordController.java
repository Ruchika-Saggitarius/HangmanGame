package com.ruchika.hangman.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.repositories.IWordRepository;
import com.ruchika.hangman.requests.AddWordRequest;
import com.ruchika.hangman.responses.GetAllWordsResponse;
import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.model.Word;

@RestController
public class WordController {

    @Autowired
    private IWordRepository mockWordRepository;

    @GetMapping("/words")
    public GetAllWordsResponse getAllWords() {
        List<Word> words = mockWordRepository.getAllWords();
        return new GetAllWordsResponse(words);
    }

    @PostMapping("/word")
    public void addWord(@RequestBody AddWordRequest addWordRequest) {
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
        else if(mockWordRepository.checkIfWordExists(addWordRequest.getWord())) {
            throw new BadRequestException("Word already exists. Please provide a different word.");
        }
        String wordId = UUID.randomUUID().toString();
        mockWordRepository.addWord(new Word(wordId, addWordRequest.getWord().toLowerCase(), addWordRequest.getHint()));
    }

    @PostMapping("/word/{wordId}")
    public void deleteWord(@PathVariable String wordId) {
        if(wordId.isEmpty()){
            throw new BadRequestException("Invalid input. Please provide a valid word id.");
        }
        else if(mockWordRepository.checkIfWordIdExists(wordId)==false){
            throw new BadRequestException("Invalid word id. Please provide a valid word id.");
        }
        mockWordRepository.deleteWord(wordId);
    }



    
    
}
