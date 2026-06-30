package team.control;

import team.model.GameWorld;
import team.model.Enemy;
import team.model.BonusItem;
import java.util.Random;

public class SpawnManager {
    private final GameWorld gameWorld;
    private final Random random = new Random();
    private int enemyTickCounter = 0;
    private int bonusTickCounter = 0;

    public SpawnManager(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    // ac: manages cyclic hostile insertion coordinates driven by ongoing game difficulty levels
    public void spawnEnemyIfNeeded() {
        enemyTickCounter++;
        int threshold = Math.max(15, 40 - gameWorld.getGameStats().getDifficultyLevel() * 3);
        if (enemyTickCounter >= threshold) {
            enemyTickCounter = 0;
            int rx = random.nextInt(700);
            int speed = 3 + random.nextInt(gameWorld.getGameStats().getDifficultyLevel() + 2);
            gameWorld.addEnemy(new Enemy(rx, -40, speed, 10, 100));
        }
    }

    // ac: drops consumable modifiers based on explicit interval metrics
    public void spawnBonusIfNeeded() {
        bonusTickCounter++;
        if (bonusTickCounter >= 150) {
            bonusTickCounter = 0;
            int rx = random.nextInt(700);
            
            // ac: randomly assigns bonus type via Enum and scales effect value accordingly
            BonusItem.BonusType type = random.nextBoolean() ? BonusItem.BonusType.HEALTH : BonusItem.BonusType.AMMO;
            int effectValue = (type == BonusItem.BonusType.HEALTH) ? 20 : 15;
            
            gameWorld.addBonus(new BonusItem(rx, -30, type, effectValue));
        }
    }
}