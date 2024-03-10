package com.ruchika.hangman.model;

import java.util.List;

public class Word {

    private String wordId;
    private String word;
    private String hint;

    public Word(String wordId, String word, String hint) {
        this.wordId = wordId;
        this.word = word;
        this.hint = hint;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    

    public String getObscuredWord(List<String> guessedAlphabets) {

        String wordToDisplay = "";
        for (int i = 0; i < this.word.length(); i++) {
            String currentLetter = this.word.substring(i, i + 1);
            if (guessedAlphabets.contains(currentLetter)) {
                wordToDisplay += currentLetter;
            } else {
                wordToDisplay += "_";
            }
        }

        return wordToDisplay;
    }
}
