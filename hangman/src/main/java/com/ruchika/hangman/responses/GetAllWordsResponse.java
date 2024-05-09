package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.Word;

public class GetAllWordsResponse {

    private List<Word> words;

    public GetAllWordsResponse(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
    
}
