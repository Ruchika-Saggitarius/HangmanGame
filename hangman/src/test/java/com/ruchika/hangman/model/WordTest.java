package com.ruchika.hangman.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class WordTest {

    @Test
    void testGetObscuredWord_Pass() {
        Word word = new Word("123", "orange", "fruit");
        List<String> guessedAlphabetsList = new ArrayList<>();
        guessedAlphabetsList.add("r");
        String wordToDisplay = word.getObscuredWord(guessedAlphabetsList);
        assertEquals("_r____", wordToDisplay);
    }

    @Test
    void testGetObscuredWord_Fail() {
        Word word = new Word("123", "orange", "fruit");
        List<String> guessedAlphabetsList = new ArrayList<>();
        guessedAlphabetsList.add("r");
        String wordToDisplay = word.getObscuredWord(guessedAlphabetsList);
        assertNotEquals("r_____", wordToDisplay);
    }
}
