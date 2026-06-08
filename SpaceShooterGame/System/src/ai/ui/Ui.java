package ai.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class Ui {
    // ac: main window for the space shooter UI
    private JFrame frame;
    // ac: panel that renders the player ship
    private DrawingPanel drawingPanel;
    // ac: UI port implementation used by backend to update ship position
    private SpaceShooterUiPortImpl uiPortImpl;

    public Ui() {
        // ac: initialize the drawing panel before UI ports are set
        drawingPanel = new DrawingPanel();
    }

    public void setUiPorts() {
        // ac: create the UI port and register it with the backend
        uiPortImpl = new SpaceShooterUiPortImpl(drawingPanel);
        my_base.App.content().getBackend().setUiPort(uiPortImpl);
    }

    public void start(shared.MainRouter router) {
        // ac: build the main window and add the drawing surface
        frame = new JFrame("Space Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new java.awt.BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/start", "");
            frame.requestFocusInWindow();
        });
        controlPanel.add(startButton);

        JButton pauseButton = new JButton("Pause Game");
        pauseButton.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/pause", "");
            frame.requestFocusInWindow();
        });
        controlPanel.add(pauseButton);

        JButton stopButton = new JButton("Stop Game");
        stopButton.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/stop", "");
            frame.requestFocusInWindow();
        });
        controlPanel.add(stopButton);

        JComboBox<String> worldSelector = new JComboBox<>(new String[]{"World 1", "World 2", "World 3"});
        worldSelector.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/world", (String) worldSelector.getSelectedItem());
            frame.requestFocusInWindow();
        });
        controlPanel.add(worldSelector);

        frame.add(controlPanel, java.awt.BorderLayout.SOUTH);
        frame.add(drawingPanel, java.awt.BorderLayout.CENTER);
        frame.addKeyListener(new KeyboardInputHandler());
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
}
