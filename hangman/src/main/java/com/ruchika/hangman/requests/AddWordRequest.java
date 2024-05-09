package com.ruchika.hangman.requests;

public class AddWordRequest {

    private String word;

    private String hint;

    public AddWordRequest(String word, String hint) {
        this.word = word;
        this.hint = hint;
    }

    public String getWord() {
        return word;
    }

    public String getHint() {
        return hint;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    
}
