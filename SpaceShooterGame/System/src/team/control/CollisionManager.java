package team.control;

import team.model.GameWorld;
import team.model.Enemy;
import team.model.Bullet;
import team.model.BonusItem;
import java.awt.Rectangle;

public class CollisionManager {
    private final GameWorld gameWorld;

    public CollisionManager(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    // ac: core engine procedure resolving physical entity intersection vectors
    public void checkCollisions() {
        Rectangle shipBox = new Rectangle(gameWorld.getPlayerShip().getX(), gameWorld.getPlayerShip().getY(), 60, 60);

        // 1. Ammunition against Hostile Targets
        for (Bullet b : gameWorld.getBullets()) {
            Rectangle bulletBox = b.getHitbox();
            for (Enemy e : gameWorld.getEnemies()) {
                Rectangle enemyBox = new Rectangle(e.getX(), e.getY(), 40, 40);
                if (bulletBox.intersects(enemyBox) && !e.isDestroyed()) {
                    e.takeDamage(b.getDamage());
                    b.moveStep(); // ac: forces quick offscreen out of bounds clip
                    if (e.isDestroyed()) {
                        gameWorld.getGameStats().scoreUpdate(e.getScoreValue());
                        gameWorld.getGameStats().incrementEnemiesDestroyed();
                    }
                }
            }
        }

        // 2. Hostile Targets against Player Ship Hull
        for (Enemy e : gameWorld.getEnemies()) {
            Rectangle enemyBox = new Rectangle(e.getX(), e.getY(), 40, 40);
            if (shipBox.intersects(enemyBox) && !e.isDestroyed()) {
                e.takeDamage(999); // ac: immediate destruction of hostile on impact
                gameWorld.getPlayerShip().modifyHealth(-20);
            }
        }

        // 3. Modifier Entities against Player Ship Ingestion
        for (BonusItem bonus : gameWorld.getBonuses()) {
            Rectangle bonusBox = new Rectangle(bonus.getX(), bonus.getY(), 30, 30);
            if (shipBox.intersects(bonusBox)) {
                bonus.applyEffect(gameWorld.getPlayerShip());
                
                // ---> ADD THIS LINE TO FIX THE BUG <---
                bonus.consume(); 
            }
        }
    }
}