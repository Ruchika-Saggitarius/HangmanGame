package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.Word;

public class AddWordResponse {

    private Word word;

    public AddWordResponse(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
    
}
