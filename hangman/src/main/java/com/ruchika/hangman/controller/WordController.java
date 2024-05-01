package com.ruchika.hangman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.requests.AddWordRequest;
import com.ruchika.hangman.responses.GetAllWordsResponse;
import com.ruchika.hangman.services.IWordService;
import com.ruchika.hangman.model.Word;

@RestController
public class WordController {

    @Autowired
    private IWordService wordService;

    @GetMapping("/words")
    public GetAllWordsResponse getAllWords() {
        List<Word> words = wordService.getAllWords();
        return new GetAllWordsResponse(words);
    }

    @PostMapping("/word")
    public void addWord(@RequestBody AddWordRequest addWordRequest) {
        wordService.addWord(addWordRequest);
    }

    @PostMapping("/word/{wordId}")
    public void deleteWord(@PathVariable String wordId) {
        wordService.deleteWord(wordId);
        
    }    
}
