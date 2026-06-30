package ai.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class HUDView extends JPanel {
    private int score;
    private int health = 100;
    private int ammo = 50;
    private int difficultyLevel = 1;
    private boolean isGameOver = false;

    public HUDView() {
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(0, 100));
    }

    public void updateHUDData(int score, int health, int ammo, int difficultyLevel) {
        this.score = score;
        this.health = health;
        this.ammo = ammo;
        this.difficultyLevel = difficultyLevel;
        repaint();
    }

    public void showGameOver() {
        this.isGameOver = true;
        repaint();
    }
    public void hideGameOver() {
        this.isGameOver = false;
        this.repaint();
    
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isGameOver) {
                g2d.setColor(new Color(200, 0, 0, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 36));
                String gameOverText = "GAME OVER";
                int textWidth = g2d.getFontMetrics().stringWidth(gameOverText);
                g2d.drawString(gameOverText, (getWidth() - textWidth) / 2, getHeight() / 2 - 10);

                g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
                String scoreText = "Final Score: " + score;
                int scoreTextWidth = g2d.getFontMetrics().stringWidth(scoreText);
                g2d.drawString(scoreText, (getWidth() - scoreTextWidth) / 2, getHeight() / 2 + 28);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2d.drawString("Score: " + score, 15, 25);
                g2d.drawString("Health: " + health, 15, 45);
                g2d.drawString("Ammo: " + ammo, 15, 65);
                g2d.drawString("Level: " + difficultyLevel, 15, 85);
            }
        } finally {
            g2d.dispose();
        }
    }
}
