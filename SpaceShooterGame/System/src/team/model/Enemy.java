package team.model;

public class Enemy {
    private int x;
    private int y;
    private final int vy;
    private int health;
    private final int scoreValue;
    
    public java.awt.Rectangle getHitbox() {
        return new java.awt.Rectangle(this.x, this.y, 40, 40);
    }
    
    public Enemy(int x, int y, int vy, int health, int scoreValue) {
        this.x = x;
        this.y = y;
        this.vy = vy;
        this.health = health;
        this.scoreValue = scoreValue;
    }

    // ac: core execution method updating vertical coordinate position downward
    public void moveStep() {
        this.y += this.vy;
    }

    // ac: core damage tracking handler decrements health pool
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) this.health = 0;
    }

    // ac: checks if entity slipped past screen lower threshold (470px)
    public boolean isOutOfBounds() {
        return this.y > 470;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getHealth() { return this.health; }
    public int getScoreValue() { return this.scoreValue; }
    public boolean isDestroyed() { return this.health <= 0; }
}