package shared.routers;

import team.control.SpaceShooterBackend;

public class SpaceShooterRouter {
    private SpaceShooterBackend backend;

    public SpaceShooterRouter(SpaceShooterBackend backend) {
        this.backend = backend;
    }

    // ac: resolves application routing paths to corresponding logical backend procedures
    public void route(String path, String queryParam) {
        if (path.equals("/player/move")) {
            backend.movePlayer(queryParam);
        } 
        else if (path.equals("/game/start")) {
            backend.startGame();
        } 
        else if (path.equals("/game/pause")) {
            backend.pauseGame();
        } 
        else if (path.equals("/game/stop")) {
            backend.stopGame();
        } 
        else if (path.equals("/game/world")) {
            backend.changeWorld(queryParam);
        }
        else if (path.equals("/player/fire")) {
            backend.fireWeapon();
        }
    }
}