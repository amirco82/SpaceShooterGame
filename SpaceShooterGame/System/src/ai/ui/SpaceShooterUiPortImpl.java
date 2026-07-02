package ai.ui;

import shared.ui_ports.SpaceShooterUiPort;
//import team.model.BonusItem;
//import team.model.Bullet;
//import team.model.Enemy;

public class SpaceShooterUiPortImpl implements SpaceShooterUiPort {
    private final GameCanvasView drawingPanel;
    private final HUDView hudPanel;
    private int currentScore;
    private int currentHealth;
    private int currentAmmo;
    @SuppressWarnings("unused") 
    private boolean gameOverActive = false;
    @SuppressWarnings("unused") 
    private int finalScore = 0;

    public SpaceShooterUiPortImpl(GameCanvasView drawingPanel, HUDView hudPanel) {
        this.drawingPanel = drawingPanel;
        this.hudPanel = hudPanel;
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
        hudPanel.updateHUDData(score, currentHealth, currentAmmo, 1);
        hudPanel.repaint();
    }

    @Override
    public void updateHealth(int health, int ammo) {
        this.currentHealth = health;
        this.currentAmmo = ammo;
        hudPanel.updateHUDData(currentScore, health, ammo, 1);
        hudPanel.repaint();
    }

    @Override
    public void showGameOver(int finalScore) {
        this.gameOverActive = true;
        this.finalScore = finalScore;
        hudPanel.updateHUDData(finalScore, currentHealth, currentAmmo, 1);
        hudPanel.showGameOver();
        hudPanel.repaint();
    }

    @Override
    public void addBullet() {
        drawingPanel.repaint();
    }

   @Override
    public void hideGameOver() {
        this.gameOverActive = false; 
        this.hudPanel.hideGameOver(); 
    }

    public void renderFrame(java.util.List<team.model.Enemy> enemies, java.util.List<team.model.Bullet> bullets, java.util.List<team.model.BonusItem> bonuses) {
        drawingPanel.updateGameObjects(enemies, bullets, bonuses);
        drawingPanel.repaint();
    }
}
