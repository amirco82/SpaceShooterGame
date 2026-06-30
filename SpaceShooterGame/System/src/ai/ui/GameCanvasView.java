package ai.ui;

import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
//import java.util.List;
import java.util.Random;
import team.model.BonusItem;
import team.model.Bullet;
import team.model.Enemy;

public class GameCanvasView extends JPanel {
    private int shipX = 400;
    private int shipY = 400;
    private String currentWorld = "World 1";
 //   private final Random random = new Random();
 //   private BufferedImage backgroundImage;
 //   private int backgroundWidth = -1;
 //   private int backgroundHeight = -1;
    private BufferedImage backgroundImageWorld1;
    private BufferedImage backgroundImageWorld2;
    private BufferedImage backgroundImageWorld3;
    private int bgScrollOffset = 0;
    private java.util.List<team.model.Enemy> enemies = new ArrayList<>();
    private java.util.List<team.model.Bullet> bullets = new ArrayList<>();
    private java.util.List<team.model.BonusItem> bonuses = new ArrayList<>();

    public void updateShipPosition(int x, int y) {
        this.shipX = x;
        this.shipY = y;
        this.repaint();
    }

    private BufferedImage getCachedBackground(String theme, int width, int height) {
        try {
            BufferedImage target = null;
            if ("World 1".equals(theme)) target = backgroundImageWorld1;
            else if ("World 2".equals(theme)) target = backgroundImageWorld2;
            else target = backgroundImageWorld3;

            if (target == null || target.getWidth() != width || target.getHeight() != height) {
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = img.createGraphics();
                try {
                    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    drawDeepSpaceBackground(g, width, height, theme);
                } finally {
                    g.dispose();
                }
                if ("World 1".equals(theme)) backgroundImageWorld1 = img;
                else if ("World 2".equals(theme)) backgroundImageWorld2 = img;
                else backgroundImageWorld3 = img;
                return img;
            }
            return target;
        } catch (Exception e) {
            return null;
        }
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

    private void drawDeepSpaceBackground(Graphics2D g2d, int width, int height, String theme) {
        // Base gradient per theme
        if ("World 1".equals(theme)) {
            g2d.setPaint(new GradientPaint(0, 0, new Color(2, 8, 24), 0, height, new Color(12, 20, 48)));
        } else if ("World 2".equals(theme)) {
            g2d.setPaint(new GradientPaint(0, 0, new Color(40, 6, 6), 0, height, new Color(120, 30, 10)));
        } else { // World 3
            g2d.setPaint(new GradientPaint(0, 0, new Color(8, 0, 20), 0, height, new Color(36, 4, 64)));
        }
        g2d.fillRect(0, 0, width, height);

        long seed = 1337L + width * 31 + height * 17 + (theme == null ? 0 : theme.hashCode());
        Random backgroundRandom = new Random(seed);

        // nebulae variations per theme
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.32f));
        for (int i = 0; i < 4; i++) {
            int x = backgroundRandom.nextInt(width);
            int y = backgroundRandom.nextInt(height / 2);
            int nebulaWidth = 160 + backgroundRandom.nextInt(260);
            int nebulaHeight = 100 + backgroundRandom.nextInt(180);
            Color nebulaColor;
            if ("World 1".equals(theme)) nebulaColor = new Color(56, 184, 255, 48);
            else if ("World 2".equals(theme)) nebulaColor = new Color(255, 120, 64, 48);
            else nebulaColor = new Color(150, 80, 200, 48);
            g2d.setColor(nebulaColor);
            g2d.fillOval(x, y, nebulaWidth, nebulaHeight);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
        for (int i = 0; i < 6; i++) {
            int x = backgroundRandom.nextInt(width);
            int y = backgroundRandom.nextInt(height);
            int radius = 70 + backgroundRandom.nextInt(160);
            Color rimColor;
            if ("World 1".equals(theme)) rimColor = new Color(122, 92, 255, 70);
            else if ("World 2".equals(theme)) rimColor = new Color(255, 160, 60, 70);
            else rimColor = new Color(168, 120, 255, 70);
            g2d.setPaint(new RadialGradientPaint(
                    new Point2D.Float(x, y),
                    radius,
                    new float[]{0f, 1f},
                    new Color[]{rimColor, new Color(0, 0, 0, 0)}));
            g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        for (int i = 0; i < 220; i++) {
            int sx = backgroundRandom.nextInt(width);
            int sy = backgroundRandom.nextInt(height);
            int size = 1 + backgroundRandom.nextInt(3);
            Color starColor;
            if ("World 1".equals(theme)) starColor = new Color(164, 212, 255, 180);
            else if ("World 2".equals(theme)) starColor = new Color(255, 220, 190, 180);
            else starColor = new Color(220, 180, 255, 180);
            g2d.setColor(starColor);
            g2d.fillOval(sx, sy, size, size);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
         //   java.awt.Composite originalComposite = g2d.getComposite();

            BufferedImage bg = getCachedBackground(currentWorld, width, height);
            if (bg != null) {
                int y1 = bgScrollOffset;
                int y2 = bgScrollOffset - height;
                g2d.drawImage(bg, 0, y1, null);
                g2d.drawImage(bg, 0, y2, null);
                bgScrollOffset += 2;
                if (bgScrollOffset >= height) bgScrollOffset = 0;
            } else {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, width, height);
            }

            int x = shipX;
            int y = shipY;

            java.awt.geom.Path2D.Double hull = new java.awt.geom.Path2D.Double();
            hull.moveTo(x + 24, y + 10);
            hull.lineTo(x + 44, y + 22);
            hull.lineTo(x + 34, y + 34);
            hull.lineTo(x + 14, y + 34);
            hull.lineTo(x + 6, y + 22);
            hull.closePath();
            g2d.setPaint(new GradientPaint(x + 8, y + 10, new Color(232, 236, 242), x + 44, y + 34, new Color(90, 104, 120)));
            g2d.fill(hull);
            g2d.setColor(new Color(182, 198, 214));
            g2d.draw(hull);

            Polygon leftWing = new Polygon(
                    new int[]{x + 16, x + 8, x + 8, x + 14},
                    new int[]{y + 22, y + 14, y + 18, y + 26},
                    4);
            Polygon rightWing = new Polygon(
                    new int[]{x + 34, x + 42, x + 42, x + 36},
                    new int[]{y + 22, y + 14, y + 18, y + 26},
                    4);
            g2d.setColor(new Color(78, 90, 106));
            g2d.fillPolygon(leftWing);
            g2d.fillPolygon(rightWing);
            g2d.setColor(new Color(150, 165, 180));
            g2d.drawPolygon(leftWing);
            g2d.drawPolygon(rightWing);

            java.awt.geom.Path2D.Double canopy = new java.awt.geom.Path2D.Double();
            canopy.moveTo(x + 22, y + 16);
            canopy.lineTo(x + 34, y + 22);
            canopy.lineTo(x + 22, y + 28);
            canopy.lineTo(x + 12, y + 22);
            canopy.closePath();
            g2d.setPaint(new GradientPaint(x + 12, y + 16, new Color(54, 222, 255), x + 28, y + 28, new Color(10, 56, 110)));
            g2d.fill(canopy);
            g2d.setColor(new Color(198, 242, 255));
            g2d.draw(canopy);

            g2d.setColor(new Color(0, 135, 255));
            g2d.fillRect(x + 12, y + 36, 10, 8);
            g2d.fillRect(x + 30, y + 36, 10, 8);
            g2d.setColor(new Color(130, 230, 255));
            g2d.fillRect(x + 14, y + 38, 6, 6);
            g2d.fillRect(x + 32, y + 38, 6, 6);
            g2d.setColor(new Color(255, 255, 255, 90));
            g2d.drawLine(x + 20, y + 18, x + 24, y + 22);
            g2d.drawLine(x + 24, y + 22, x + 20, y + 26);

            if (bullets != null) {
                g2d.setColor(Color.YELLOW);
                for (Bullet bullet : bullets) {
                    if (bullet != null) {
                        g2d.fillRect(bullet.getX(), bullet.getY(), 6, 16);
                    }
                }
            }

            if (enemies != null) {
                for (Enemy enemy : enemies) {
                    if (enemy != null) {
                        int ex = enemy.getX();
                        int ey = enemy.getY();

                        java.awt.geom.Path2D.Double body = new java.awt.geom.Path2D.Double();
                        body.moveTo(ex + 18, ey + 8);
                        body.lineTo(ex + 32, ey + 8);
                        body.lineTo(ex + 40, ey + 16);
                        body.lineTo(ex + 32, ey + 28);
                        body.lineTo(ex + 18, ey + 28);
                        body.lineTo(ex + 10, ey + 16);
                        body.closePath();
                        g2d.setPaint(new GradientPaint(ex + 10, ey + 8, new Color(70, 70, 80), ex + 36, ey + 28, new Color(180, 40, 24)));
                        g2d.fill(body);
                        g2d.setColor(new Color(110, 24, 18));
                        g2d.draw(body);

                        Polygon enemyLeftWing = new Polygon(
                                new int[]{ex + 16, ex + 8, ex + 10, ex + 18},
                                new int[]{ey + 16, ey + 12, ey + 20, ey + 24},
                                4);
                        Polygon enemyRightWing = new Polygon(
                                new int[]{ex + 32, ex + 40, ex + 38, ex + 30},
                                new int[]{ey + 16, ey + 12, ey + 20, ey + 24},
                                4);
                        g2d.setColor(new Color(90, 30, 24));
                        g2d.fillPolygon(enemyLeftWing);
                        g2d.fillPolygon(enemyRightWing);

                        g2d.setColor(new Color(255, 110, 40));
                        g2d.fillOval(ex + 18, ey + 12, 10, 10);
                        g2d.setColor(new Color(255, 200, 80));
                        g2d.fillOval(ex + 20, ey + 14, 6, 6);
                        g2d.setColor(new Color(255, 80, 0, 120));
                        g2d.drawOval(ex + 17, ey + 11, 12, 12);
                    }
                }
            }

            if (bonuses != null) {
                for (BonusItem bonus : bonuses) {
                    if (bonus != null) {
                        int bx = bonus.getX();
                        int by = bonus.getY();

                        g2d.setColor(new Color(80, 210, 255, 70));
                        g2d.fillOval(bx + 2, by + 2, 26, 26);

                        g2d.setColor(new Color(170, 240, 255));
                        g2d.fillRoundRect(bx + 6, by + 5, 18, 20, 6, 6);
                        g2d.setColor(new Color(60, 170, 255));
                        g2d.fillRect(bx + 11, by + 8, 8, 14);
                        g2d.fillRect(bx + 8, by + 11, 14, 8);
                        g2d.setColor(new Color(255, 255, 255));
                        g2d.fillRect(bx + 13, by + 9, 4, 14);
                        g2d.fillRect(bx + 8, by + 13, 14, 4);
                        g2d.setColor(new Color(30, 90, 180));
                        g2d.drawRoundRect(bx + 6, by + 5, 18, 20, 6, 6);
                        g2d.setColor(new Color(255, 255, 255, 130));
                        g2d.drawRoundRect(bx + 7, by + 6, 16, 18, 6, 6);
                    }
                }
            }

        } finally {
            g2d.dispose();
        }
    }
}
