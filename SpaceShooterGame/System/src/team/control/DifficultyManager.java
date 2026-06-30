package team.control;

import team.model.GameStats;

public class DifficultyManager {
    private final GameStats gameStats;
    private int thresholdScore = 100;

    public DifficultyManager(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    // ac: scales global game velocity modifiers when target performance indexes break boundaries
    public void updateDifficulty() {
        if (gameStats.getScore() >= thresholdScore) {
            gameStats.advanceLevel();
            thresholdScore += 100; // ac: sets up next progressive benchmark goal step
        }
    }
}