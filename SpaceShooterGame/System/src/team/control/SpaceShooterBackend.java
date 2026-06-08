package team.control;

import team.model.GameWorld;
import team.model.Bullet;
import team.model.Enemy;
import team.model.BonusItem;
import shared.ui_ports.SpaceShooterUiPort;


public class SpaceShooterBackend {
    private final GameWorld gameWorld;
    private SpaceShooterUiPort uiPort;
    
    private final CollisionManager collisionManager;
    private final SpawnManager spawnManager;
    private final DifficultyManager difficultyManager;
    
    private boolean isRunning = false;
    private boolean isPaused = false;

    private final PeriodicLoop gameLoop;

    public SpaceShooterBackend() {
        this.gameWorld = new GameWorld();
        this.collisionManager = new CollisionManager(gameWorld);
        this.spawnManager = new SpawnManager(gameWorld);
        this.difficultyManager = new DifficultyManager(gameWorld.getGameStats());
        this.gameLoop = new PeriodicLoop(this);
    }

    public void setUiPort(SpaceShooterUiPort port) {
        this.uiPort = port;
    }

    public void startGame() {
        isRunning = true;
        isPaused = false;
        gameWorld.getGameStats().resetState();
        this.gameLoop.startLoop();
    }

    public void pauseGame() {
        if (isRunning) isPaused = !isPaused;
    }

    public void stopGame() {
        isRunning = false;
        isPaused = false;
        gameWorld.getPlayerShip().setPosition(400, 400);
        if (uiPort != null) {
            uiPort.updatePlayerShip(400, 400);
        }
        this.gameLoop.stopLoop();
    }

    public void movePlayer(String direction) {
        if (isRunning && !isPaused) {
            int dx = 0, dy = 0;
            if (direction.equalsIgnoreCase("left")) dx = -10;
            else if (direction.equalsIgnoreCase("right")) dx = 10;
            else if (direction.equalsIgnoreCase("up")) dy = -10;
            else if (direction.equalsIgnoreCase("down")) dy = 10;

            gameWorld.getPlayerShip().updatePosition(dx, dy);

            if (uiPort != null) {
                uiPort.updatePlayerShip(gameWorld.getPlayerShip().getX(), gameWorld.getPlayerShip().getY());
                uiPort.updateHealth(gameWorld.getPlayerShip().getHealth(), gameWorld.getPlayerShip().getAmmo());
            }
        }
    }

    public void fireWeapon() {
        if (isRunning && !isPaused && gameWorld.getPlayerShip().getAmmo() > 0) {
            gameWorld.getPlayerShip().decreaseAmmo();
            gameWorld.addBullet(new Bullet(gameWorld.getPlayerShip().getX() + 27, gameWorld.getPlayerShip().getY(), 10));
            if (uiPort != null) {
                uiPort.addBullet();
                uiPort.updateHealth(gameWorld.getPlayerShip().getHealth(), gameWorld.getPlayerShip().getAmmo());
            }
        }
    }

    public void changeWorld(String worldName) {
        if (uiPort != null) {
            uiPort.updateWorldBackground(worldName);
        }
    }

    // ac: main engine cyclic callback driving object translations and boundary updates
    public void gameTick() {
        if (!isRunning || isPaused) return;

        // 1. Run environment item managers
        spawnManager.spawnEnemyIfNeeded();
        spawnManager.spawnBonusIfNeeded();

        // 2. Propagate active object coordinates
        gameWorld.getEnemies().forEach(Enemy::moveStep);
        gameWorld.getBullets().forEach(Bullet::moveStep);
        gameWorld.getBonuses().forEach(BonusItem::moveStep);

        // 3. Process intersections and adjust attributes
        collisionManager.checkCollisions();
        difficultyManager.updateDifficulty();

        // 4. Purge expired instances
        gameWorld.removeExpiredObjects();

        // 5. Update UI layer via port delegation
        if (uiPort != null) {
            gameWorld.getGameStats().updateTime(0.016); 
            uiPort.updateScore(gameWorld.getGameStats().getScore());
            uiPort.updateHealth(gameWorld.getPlayerShip().getHealth(), gameWorld.getPlayerShip().getAmmo());
            
            // ---> ADD THIS LINE TO SEND OBJECTS TO THE SCREEN <---
            uiPort.renderFrame(gameWorld.getEnemies(), gameWorld.getBullets(), gameWorld.getBonuses());
            
            if (gameWorld.getPlayerShip().isDestroyed()) {
                isRunning = false;
                gameLoop.stopLoop(); // Stop the loop on death
                uiPort.showGameOver(gameWorld.getGameStats().getScore());
            }
        }
    }
}