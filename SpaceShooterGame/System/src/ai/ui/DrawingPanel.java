package ai.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import team.model.BonusItem;
import team.model.Bullet;
import team.model.Enemy;

public class DrawingPanel extends JPanel {
    private int shipX = 400;
    private int shipY = 400;
    private String currentWorld = "World 1";
    private final Random random = new Random();
    private java.util.List<team.model.Enemy> enemies = new ArrayList<>();
    private java.util.List<team.model.Bullet> bullets = new ArrayList<>();
    private java.util.List<team.model.BonusItem> bonuses = new ArrayList<>();
    private int score;
    private int health;
    private int ammo;

    public void updateShipPosition(int x, int y) {
        this.shipX = x;
        this.shipY = y;
        this.repaint();
    }

    public void setWorld(String worldName) {
        this.currentWorld = worldName;
        this.repaint();
    }

    public void updateGameObjects(java.util.List<team.model.Enemy> enemies, java.util.List<team.model.Bullet> bullets, java.util.List<team.model.BonusItem> bonuses) {
        this.enemies = enemies != null ? new ArrayList<>(enemies) : new ArrayList<>();
        this.bullets = bullets != null ? new ArrayList<>(bullets) : new ArrayList<>();
        this.bonuses = bonuses != null ? new ArrayList<>(bonuses) : new ArrayList<>();
    }

    public void updateScoreData(int score) {
        this.score = score;
    }

    public void updateHealthData(int health, int ammo) {
        this.health = health;
        this.ammo = ammo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            java.awt.Composite originalComposite = g2d.getComposite();

            if (currentWorld.equals("World 1")) {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.WHITE);
                for (int i = 0; i < 200; i++) {
                    int size = 1 + random.nextInt(2);
                    int sx = random.nextInt(width);
                    int sy = random.nextInt(height);
                    g2d.fillOval(sx, sy, size, size);
                }
                g2d.setColor(new Color(192, 192, 192));
                g2d.fillOval(width / 6, height / 5, 70, 70);
                g2d.fillOval(width * 2 / 3, height / 4, 90, 90);
                g2d.fillOval(width / 4, height * 3 / 5, 50, 50);
            } else if (currentWorld.equals("World 2")) {
                g2d.setPaint(new GradientPaint(0, 0, new Color(139, 0, 0), 0, height, new Color(255, 99, 71)));
                g2d.fillRect(0, 0, width, height);
                g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.25f));
                g2d.setColor(new Color(255, 140, 0));
                for (int i = 0; i < 100; i++) {
                    int w = 2 + random.nextInt(4);
                    int h = 1 + random.nextInt(3);
                    int sx = random.nextInt(width);
                    int sy = random.nextInt(height);
                    g2d.fillOval(sx, sy, w, h);
                }
                g2d.setComposite(originalComposite);
                g2d.setColor(new Color(80, 80, 80));
                for (int i = 0; i < 5; i++) {
                    int cx = random.nextInt(Math.max(1, width - 120));
                    int cy = random.nextInt(Math.max(1, height - 120));
                    int[] xs = {cx, cx + 40, cx + 70, cx + 90, cx + 60, cx + 20};
                    int[] ys = {cy + 20, cy + 10, cy + 30, cy + 70, cy + 90, cy + 60};
                    g2d.fillPolygon(new Polygon(xs, ys, xs.length));
                }
            } else {
                g2d.setPaint(new GradientPaint(0, 0, new Color(20, 0, 40), 0, height, new Color(0, 0, 70)));
                g2d.fillRect(0, 0, width, height);
                g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.1f));
                for (int i = 0; i < 30; i++) {
                    Color nebulaColor = random.nextBoolean() ? new Color(0, 255, 255, 80) : new Color(255, 0, 255, 80);
                    g2d.setColor(nebulaColor);
                    int ovalWidth = 80 + random.nextInt(120);
                    int ovalHeight = 60 + random.nextInt(100);
                    int sx = random.nextInt(width);
                    int sy = random.nextInt(height);
                    g2d.fillOval(sx, sy, ovalWidth, ovalHeight);
                }
                g2d.setComposite(originalComposite);
                for (int i = 0; i < 150; i++) {
                    int sx = random.nextInt(width);
                    int sy = random.nextInt(height);
                    int size = 1 + random.nextInt(3);
                    int colorIndex = random.nextInt(3);
                    if (colorIndex == 0) {
                        g2d.setColor(new Color(0, 255, 255));
                    } else if (colorIndex == 1) {
                        g2d.setColor(new Color(255, 0, 255));
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                    g2d.fillOval(sx, sy, size, size);
                }
            }

            int x = shipX;
            int y = shipY;

            Polygon fuselage = new Polygon(
                    new int[]{x + 30, x + 10, x + 20, x + 40, x + 50},
                    new int[]{y + 4, y + 50, y + 42, y + 42, y + 50},
                    5);
            g2d.setColor(new Color(80, 80, 80));
            g2d.fillPolygon(fuselage);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawPolygon(fuselage);

            g2d.setColor(new Color(130, 130, 130));
            g2d.drawLine(x + 30, y + 6, x + 30, y + 38);
            g2d.drawLine(x + 24, y + 20, x + 36, y + 20);

            Polygon leftWing = new Polygon(
                    new int[]{x + 10, x + 2, x + 18, x + 16},
                    new int[]{y + 20, y + 36, y + 26, y + 24},
                    4);
            Polygon rightWing = new Polygon(
                    new int[]{x + 50, x + 58, x + 42, x + 44},
                    new int[]{y + 20, y + 36, y + 26, y + 24},
                    4);
            g2d.setColor(new Color(100, 100, 100));
            g2d.fillPolygon(leftWing);
            g2d.fillPolygon(rightWing);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawPolygon(leftWing);
            g2d.drawPolygon(rightWing);

            Polygon cockpit = new Polygon(
                    new int[]{x + 26, x + 22, x + 30, x + 38, x + 34},
                    new int[]{y + 16, y + 28, y + 12, y + 28, y + 16},
                    5);
            g2d.setColor(new Color(72, 209, 204));
            g2d.fillPolygon(cockpit);
            g2d.setColor(Color.WHITE);
            g2d.drawPolygon(cockpit);

            Polygon exhaustOuter = new Polygon(
                    new int[]{x + 20, x + 40, x + 38, x + 22},
                    new int[]{y + 42, y + 42, y + 58, y + 58},
                    4);
            g2d.setColor(new Color(255, 140, 0));
            g2d.fillPolygon(exhaustOuter);
            g2d.setColor(new Color(255, 60, 0));
            g2d.fillOval(x + 24, y + 44, 12, 12);
            g2d.fillOval(x + 28, y + 48, 6, 10);
            g2d.setColor(Color.RED);
            g2d.drawPolygon(exhaustOuter);

            g2d.setColor(new Color(255, 200, 0, 180));
            g2d.fillOval(x + 20, y + 46, 20, 12);

            if (bullets != null) {
                g2d.setColor(Color.YELLOW);
                for (Bullet bullet : bullets) {
                    if (bullet != null) {
                        g2d.fillRect(bullet.getX(), bullet.getY(), 6, 16);
                    }
                }
            }

            if (enemies != null) {
                g2d.setColor(Color.RED);
                for (Enemy enemy : enemies) {
                    if (enemy != null) {
                        g2d.fillRect(enemy.getX(), enemy.getY(), 40, 40);
                    }
                }
            }

            if (bonuses != null) {
                g2d.setColor(Color.GREEN);
                for (BonusItem bonus : bonuses) {
                    if (bonus != null) {
                        g2d.fillRect(bonus.getX(), bonus.getY(), 30, 30);
                    }
                }
            }

            g2d.setColor(java.awt.Color.WHITE);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
            g2d.drawString("Score: " + score, 15, 25);
            g2d.drawString("Health: " + health, 15, 45);
            g2d.drawString("Ammo: " + ammo, 15, 65);
        } finally {
            g2d.dispose();
        }
    }
}
