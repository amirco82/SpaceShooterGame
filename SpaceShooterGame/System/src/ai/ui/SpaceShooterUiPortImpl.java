package ai.ui;

import shared.ui_ports.SpaceShooterUiPort;
//import team.model.BonusItem;
//import team.model.Bullet;
//import team.model.Enemy;

public class SpaceShooterUiPortImpl implements SpaceShooterUiPort {
    private final DrawingPanel drawingPanel;
    private int currentScore;
    private int currentHealth;
    private int currentAmmo;
    private boolean gameOverActive;
    private int finalScore;

    public SpaceShooterUiPortImpl(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void updatePlayerShip(int x, int y) {
        drawingPanel.updateShipPosition(x, y);
    }

    @Override
    public void updateWorldBackground(String worldName) {
        drawingPanel.setWorld(worldName);
    }

    @Override
    public void updateScore(int score) {
        this.currentScore = score;
        drawingPanel.updateScoreData(score);
        drawingPanel.repaint();
    }

    @Override
    public void updateHealth(int health, int ammo) {
        this.currentHealth = health;
        this.currentAmmo = ammo;
        drawingPanel.updateHealthData(health, ammo);
        drawingPanel.repaint();
    }

    @Override
    public void showGameOver(int finalScore) {
        this.gameOverActive = true;
        this.finalScore = finalScore;
        drawingPanel.repaint();
    }

    @Override
    public void addBullet() {
        drawingPanel.repaint();
    }

    public void renderFrame(java.util.List<team.model.Enemy> enemies, java.util.List<team.model.Bullet> bullets, java.util.List<team.model.BonusItem> bonuses) {
        drawingPanel.updateGameObjects(enemies, bullets, bonuses);
        drawingPanel.repaint();
    }
}
