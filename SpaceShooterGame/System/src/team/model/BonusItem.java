package team.model;

public class BonusItem {
    private int x;
    private int y;
    private final String bonusType; // ac: expects either "Health" or "Ammo"
    private final int effectValue;
    private final int speed = 4;
    private boolean consumed = false;

    public BonusItem(int x, int y, String bonusType, int effectValue) {
        this.x = x;
        this.y = y;
        this.bonusType = bonusType;
        this.effectValue = effectValue;
    }

    // ac: advances bonus items downward for player ingestion
    public void moveStep() {
        this.y += this.speed;
    }

    // ac: modifies target ship attributes depending on specific bonus container type
    public void applyEffect(PlayerShip playerShip) {
        if (bonusType.equalsIgnoreCase("Health")) {
            playerShip.modifyHealth(effectValue);
        } else if (bonusType.equalsIgnoreCase("Ammo")) {
            // ---> UPDATE THIS LINE <---
            playerShip.increaseAmmo(effectValue);
        }
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public boolean isOutOfBounds() { return this.y > 470; }

    // ac: marks the item for immediate deletion
    public void consume() { 
        this.consumed = true; 
    }
    
    public boolean isConsumed() { 
        return this.consumed; 
    }
}