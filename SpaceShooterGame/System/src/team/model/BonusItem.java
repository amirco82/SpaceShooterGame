package team.model;

import java.awt.Rectangle;

public class BonusItem {
    // ac: strictly defined power-up categories to prevent runtime string comparison errors
    public enum BonusType { HEALTH, AMMO }

    private int x;
    private int y;
    private final BonusType type; // ac: replaced String with Enum
    private final int effectValue;
    private final int speed = 4;
    private boolean consumed = false;

    public BonusItem(int x, int y, BonusType type, int effectValue) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.effectValue = effectValue;
    }

    // ac: advances bonus items downward for player ingestion
    public void moveStep() {
        this.y += this.speed;
    }

    // ac: modifies target ship attributes depending on specific bonus container type
    public void applyEffect(PlayerShip playerShip) {
        if (this.type == BonusType.HEALTH) {
            playerShip.modifyHealth(effectValue); // Make sure PlayerShip has this method
        } else if (this.type == BonusType.AMMO) {
            playerShip.increaseAmmo(effectValue); // Make sure PlayerShip has this method
        }
    }

    // ac: constructs bounding box geometry for collision manager evaluation
    public Rectangle getHitbox() {
        return new Rectangle(this.x, this.y, 30, 30);
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public BonusType getType() { return this.type; }
    public boolean isOutOfBounds() { return this.y > 470; }

    // ac: marks the item for immediate deletion
    public void consume() { 
        this.consumed = true; 
    }
    
    public boolean isConsumed() { 
        return this.consumed; 
    }
}