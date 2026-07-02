package team.model;

public class GameStats {
    private int score = 0;
    private double gameTime = 0.0;
    private int difficultyLevel = 1;
    @SuppressWarnings("unused")
    private int enemiesDestroyed = 0;

    // ac: core scoring mechanism incremented on dynamic object destructions
    public void scoreUpdate(int value) {
        this.score += value;
    }

    public void incrementEnemiesDestroyed() {
        this.enemiesDestroyed++;
    }

    public void advanceLevel() {
        this.difficultyLevel++;
    }

    // ac: tracks system delta increments through the central execution frame loop
    public void updateTime(double deltaTime) {
        this.gameTime += deltaTime;
    }

    // ac: system state wiper resetting performance parameters
    public void resetState() {
        this.score = 0;
        this.gameTime = 0.0;
        this.difficultyLevel = 1;
        this.enemiesDestroyed = 0;
    }

    public int getScore() { return this.score; }
    public int getDifficultyLevel() { return this.difficultyLevel; }
    public double getGameTime() { return this.gameTime; }
}