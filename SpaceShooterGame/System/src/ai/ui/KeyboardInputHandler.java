package ai.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class KeyboardInputHandler extends KeyAdapter {
    // ac: track which arrow keys are currently held down for smooth movement
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public KeyboardInputHandler() {
        // ac: timer fires frequently so held keys continue to route movement commands
        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leftPressed) {
                    my_base.App.spaceRouter().route("/player/move", "left");
                }
                if (rightPressed) {
                    my_base.App.spaceRouter().route("/player/move", "right");
                }
                if (upPressed) {
                    my_base.App.spaceRouter().route("/player/move", "up");
                }
                if (downPressed) {
                    my_base.App.spaceRouter().route("/player/move", "down");
                }
            }
        });
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            my_base.App.spaceRouter().route("/player/fire", "");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ac: release the arrow key so movement commands stop for that direction
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }
}
