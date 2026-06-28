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
    
    // ac: state machine controller
    private GameState currentState = GameState.INITIALIZING;

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

    // ac: transition from initializing to active game and purge old state
    public void startGame() {
        // ac: guard clause to prevent multiple timer instances if 'start' is triggered rapidly
        if (this.currentState == GameState.ACTIVE_GAME) return; 

        this.currentState = GameState.ACTIVE_GAME;
        
        // ac: 1. purge all dynamic entities from previous sessions to prevent memory leaks and ghost calculations
        gameWorld.clearAllEntities(); 
        
        // ac: 2. reset game statistics (score, difficulty thresholds)
        gameWorld.getGameStats().resetState();
        
        // ac: 3. reset player ship to default spawn coordinates
        gameWorld.getPlayerShip().setPosition(400, 400);
        
        // ac: Note - If PlayerShip has a resetHealth() or similar method, invoke it here 
        // ac: to ensure the player starts with 100% hull integrity and default ammo.
        
        this.gameLoop.startLoop();
    }

    // ac: toggles execution state between active and suspended
    public void pauseGame() {
        if (this.currentState == GameState.ACTIVE_GAME) {
            this.currentState = GameState.PAUSED;
        } else if (this.currentState == GameState.PAUSED) {
            this.currentState = GameState.ACTIVE_GAME;
        }
    }

   // ac: halt game execution, clear board, and transition to game over state
    public void stopGame() {
        this.currentState = GameState.GAME_OVER;
        gameWorld.getPlayerShip().setPosition(400, 400);     
        gameWorld.clearAllEntities(); 
        if (uiPort != null) {
            uiPort.updatePlayerShip(400, 400);
            
            uiPort.renderFrame(gameWorld.getEnemies(), gameWorld.getBullets(), gameWorld.getBonuses());
        }
        this.gameLoop.stopLoop();
    }

    // ac: handle player spatial movement
    public void movePlayer(String direction) {
        if (currentState == GameState.ACTIVE_GAME) {
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

    // ac: handle weapon firing mechanism
    public void fireWeapon() {
        if (currentState == GameState.ACTIVE_GAME && gameWorld.getPlayerShip().getAmmo() > 0) {
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

    // ac: transition back to initializing state
    public void restartGame() {
        this.currentState = GameState.INITIALIZING;
    }

    // ac: main engine cyclic callback driving object translations and boundary updates
    public void gameTick() {
        if (currentState != GameState.ACTIVE_GAME) return;

        // ac: run environment item managers
        spawnManager.spawnEnemyIfNeeded();
        spawnManager.spawnBonusIfNeeded();

        // ac: propagate active object coordinates
        gameWorld.getEnemies().forEach(Enemy::moveStep);
        gameWorld.getBullets().forEach(Bullet::moveStep);
        gameWorld.getBonuses().forEach(BonusItem::moveStep);

        // ac: process intersections and adjust attributes
        collisionManager.checkCollisions();
        difficultyManager.updateDifficulty();

        // ac: purge expired instances
        gameWorld.removeExpiredObjects();

        // ac: update UI layer via port delegation
        if (uiPort != null) {
            gameWorld.getGameStats().updateTime(0.016); 
            uiPort.updateScore(gameWorld.getGameStats().getScore());
            uiPort.updateHealth(gameWorld.getPlayerShip().getHealth(), gameWorld.getPlayerShip().getAmmo());
            
            // ac: send render data to display
            uiPort.renderFrame(gameWorld.getEnemies(), gameWorld.getBullets(), gameWorld.getBonuses());
            
            if (gameWorld.getPlayerShip().isDestroyed()) {
                currentState = GameState.GAME_OVER;
                gameLoop.stopLoop();
                uiPort.showGameOver(gameWorld.getGameStats().getScore());
            }
        }
    }
}