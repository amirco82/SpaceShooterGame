package shared.ui_ports;
import java.util.List;
import team.model.Enemy;
import team.model.Bullet;
import team.model.BonusItem;

public interface SpaceShooterUiPort {
    // ac: abstract bridge methods strictly mapped to requirements document
    void updatePlayerShip(int x, int y); 
    void updateWorldBackground(String worldName); 
    void updateScore(int score); 
    void updateHealth(int health, int ammo); 
    void showGameOver(int finalScore); 
    void addBullet(); 
    // ac: passes current frame data to the view for rendering
    void renderFrame(List<Enemy> enemies, List<Bullet> bullets, List<BonusItem> bonuses);
}