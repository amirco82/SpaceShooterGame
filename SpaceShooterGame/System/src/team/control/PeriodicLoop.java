package team.control;

import javax.swing.Timer;

public class PeriodicLoop {
    private final Timer timer;

    public PeriodicLoop(SpaceShooterBackend backend) {
        // ac: Triggers the backend gameTick method every 16 milliseconds
        this.timer = new Timer(16, e -> {
            backend.gameTick();
        });
    }

    public void startLoop() {
        this.timer.start();
    }

    public void stopLoop() {
        this.timer.stop();
    }
}