package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.GameStatistics;

// This class is used to get the game statistics of the user which gives how many games per user played, how many games won and how many games lost.
public class GetGameStatisticsResponse {

    private List<GameStatistics> gameStatistics;


    public GetGameStatisticsResponse(List<GameStatistics> gameStatistics) {
        this.gameStatistics = gameStatistics;
    }

    public List<GameStatistics> getGameStatistics() {
        return gameStatistics;
    }

    public void setGameStatistics(List<GameStatistics> gameStatistics) {
        this.gameStatistics = gameStatistics;
    }
}
