package com.ruchika.hangman.model;

import java.util.List;

public class Game {

    private String gameId;
    private Word word;
    private int remainingLives;
    private List<String> guessedAlphabets;
    private String userId;
    private GameStatus gameStatus;
    

    public Game(String gameId, Word word, int remainingLives, List<String> guessedAlphabets, String userId, GameStatus gameStatus) {
        this.gameId = gameId;
        this.word = word;
        this.remainingLives = remainingLives;
        this.guessedAlphabets = guessedAlphabets;
        this.userId = userId;
        this.gameStatus = gameStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    public int getRemainingLives() {
        return remainingLives;
    }
    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }
    public List<String> getGuessedAlphabets() {
        return guessedAlphabets;
    }
    public void setGuessedAlphabets(List<String> guessedAlphabets) {
        this.guessedAlphabets = guessedAlphabets;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void addGuess(String guess) {
        guessedAlphabets.add(guess);
    }


    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     Game other = (Game) obj;
    //     if (gameId == null) {
    //         if (other.gameId != null)
    //             return false;
    //     } else if (!gameId.equals(other.gameId))
    //         return false;
    //     return true;
    // }

    
}
