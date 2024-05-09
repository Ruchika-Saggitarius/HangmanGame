package com.ruchika.hangman.responses;

import java.util.List;

import com.ruchika.hangman.model.GameStatistics;

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
