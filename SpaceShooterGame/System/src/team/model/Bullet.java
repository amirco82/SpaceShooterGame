package team.model;

import java.awt.Rectangle;

public class Bullet {
    private int x;
    private int y;
    private final int vy = 12; // ac: fixed upward ammunition velocity vector
    private final int damage;
    
    public Bullet(int x, int y, int damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
    }

    // ac: moves bullet upward along the y axis
    public void moveStep() {
        this.y -= this.vy;
    }

    // ac: constructs bounding box geometry for collision manager evaluation
    public Rectangle getHitbox() {
        return new Rectangle(this.x, this.y, 6, 16);
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getDamage() { return this.damage; }
    public boolean isOutOfBounds() { return this.y < 0; }
}