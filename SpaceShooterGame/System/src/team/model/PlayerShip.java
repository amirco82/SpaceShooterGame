package team.model;

public class PlayerShip {
    private int x;
    private int y;
    // ac: core variables from design specifications
 //   private final int speed = 10;
    private final int size = 60; 
    private int health = 100;    
    private int ammo = 30;       
    
    // ac: exact boundaries to protect space shooter canvas layout
    private final int screenWidth = 784; 
    private final int screenHeight = 400; 

    public PlayerShip(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    // ac: auxiliary setter to center the ship on game stop
    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // ac: core position update method using calculated offsets
    public void updatePosition(int dx, int dy) {
        if (x + dx >= 0 && x + dx + size <= screenWidth) {
            x += dx;
        }
        if (y + dy >= 0 && y + dy + size <= screenHeight) {
            y += dy;
        }
    }

    // ac: core health modifier method
    public void modifyHealth(int amount) {
        this.health += amount;
        if (this.health < 0) this.health = 0;
    }

    // ac: core lifecycle check method
    public boolean isDestroyed() {
        return this.health <= 0;
    }

    
    public void resetHealth() {
        this.health = 100; 
    }

    public void resetAmmo() {
        this.ammo = 30; 
    }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getHealth() { return this.health; }
    public int getAmmo() { return this.ammo; }
    
    // ac: core ammunition cost decrement
    public void decreaseAmmo() { 
        if (this.ammo > 0) this.ammo--; 
    }
    // ac: increases ammunition pool when collecting an ammo bonus
    public void increaseAmmo(int amount) {
        this.ammo += amount;
    }
}