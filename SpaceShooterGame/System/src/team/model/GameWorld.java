package team.model;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    private final PlayerShip playerShip;
    private final List<Enemy> enemies;
    private final List<Bullet> bullets;
    private final List<BonusItem> bonuses;
    private final GameStats gameStats;

    public GameWorld() {
        this.playerShip = new PlayerShip(400, 350);
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.bonuses = new ArrayList<>();
        this.gameStats = new GameStats();
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void addBullet(Bullet bullet) {
        this.bullets.add(bullet);
    }

    public void addBonus(BonusItem item) {
        this.bonuses.add(item);
    }

    // ac: purges objects exceeding display resolution coordinates or flags
    public void removeExpiredObjects() {
        enemies.removeIf(e -> e.isOutOfBounds() || e.isDestroyed());
        bullets.removeIf(b -> b.isOutOfBounds());
        
        // ---> UPDATE THIS LINE <---
        bonuses.removeIf(item -> item.isOutOfBounds() || item.isConsumed());
    }
    // ac: purges all dynamic entities upon game termination
    public void clearAllEntities() {
        this.enemies.clear();
        this.bullets.clear();
        this.bonuses.clear();
    }
    public PlayerShip getPlayerShip() { return this.playerShip; }
    public List<Enemy> getEnemies() { return this.enemies; }
    public List<Bullet> getBullets() { return this.bullets; }
    public List<BonusItem> getBonuses() { return this.bonuses; }
    public GameStats getGameStats() { return this.gameStats; }
}