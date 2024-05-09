package com.ruchika.hangman.model;

public class GameStatistics {

    private int totalGamesPlayed;
    private int totalGamesWon;
    private int totalGamesLost;
    private String displayName;
    private int totalScore;

    public GameStatistics(int totalGamesPlayed, int totalGamesWon, int totalGamesLost, String displayName, int totalScore) {
        this.totalGamesPlayed = totalGamesPlayed;
        this.totalGamesWon = totalGamesWon;
        this.totalGamesLost = totalGamesLost;
        this.displayName = displayName;
        this.totalScore = totalScore;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getTotalGamesWon() {
        return totalGamesWon;
    }

    public void setTotalGamesWon(int totalGamesWon) {
        this.totalGamesWon = totalGamesWon;
    }

    public int getTotalGamesLost() {
        return totalGamesLost;
    }

    public void setTotalGamesLost(int totalGamesLost) {
        this.totalGamesLost = totalGamesLost;
    }

    public String getDisplayName() {
        return displayName;
    }       

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    
}
