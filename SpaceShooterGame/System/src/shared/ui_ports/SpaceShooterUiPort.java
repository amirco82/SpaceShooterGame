package shared.ui_ports;

public interface SpaceShooterUiPort {
    // ac: abstract bridge methods strictly mapped to requirements document
    void updatePlayerShip(int x, int y); 
    void updateWorldBackground(String worldName); 
    void updateScore(int score); 
    void updateHealth(int health, int ammo); 
    void showGameOver(int finalScore); 
    void addBullet(); 
    // ac: passes current frame data to the view for rendering
    void renderFrame(java.util.List<team.model.Enemy> enemies, 
                     java.util.List<team.model.Bullet> bullets, 
                     java.util.List<team.model.BonusItem> bonuses);
}