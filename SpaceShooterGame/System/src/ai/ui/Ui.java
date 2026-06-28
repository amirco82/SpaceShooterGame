package ai.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

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
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 10));
        controlPanel.setBackground(new Color(8, 12, 24));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(4, 10, 8, 10));

        JButton startButton = createStyledButton("START");
        startButton.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/start", "");
            frame.requestFocusInWindow();
        });
        controlPanel.add(startButton);

        JButton pauseButton = createStyledButton("PAUSE");
        pauseButton.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/pause", "");
            frame.requestFocusInWindow();
        });
        controlPanel.add(pauseButton);

        JButton stopButton = createStyledButton("STOP");
        stopButton.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/stop", "");
            frame.requestFocusInWindow();
        });
        controlPanel.add(stopButton);

        JComboBox<String> worldSelector = new JComboBox<>(new String[]{"World 1", "World 2", "World 3"});
        worldSelector.setFont(new Font("Segoe UI", Font.BOLD, 12));
        worldSelector.setForeground(new Color(220, 246, 255));
        worldSelector.setBackground(new Color(15, 24, 38));
        worldSelector.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 210, 255), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        worldSelector.setFocusable(false);
        worldSelector.addActionListener(e -> {
            my_base.App.spaceRouter().route("/game/world", (String) worldSelector.getSelectedItem());
            frame.requestFocusInWindow();
        });
        controlPanel.add(worldSelector);

        frame.add(controlPanel, java.awt.BorderLayout.SOUTH);
        frame.add(drawingPanel, java.awt.BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(4, 8, 16));
        frame.addKeyListener(new KeyboardInputHandler());
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }

    private JButton createStyledButton(String label) {
        JButton button = new HoloButton(label);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(new Color(232, 248, 255));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(108, 34));
        return button;
    }

    private static class HoloButton extends JButton {
        HoloButton(String label) {
            super(label);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setBorderPainted(false);
            setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            try {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                RoundRectangle2D.Float body = new RoundRectangle2D.Float(1, 1, width - 2, height - 2, 16, 16);

                g2d.setPaint(new GradientPaint(0, 0, new Color(18, 34, 52), 0, height, new Color(8, 16, 32)));
                g2d.fill(body);
                g2d.setColor(new Color(68, 215, 255, 140));
                g2d.draw(body);
                g2d.setColor(new Color(118, 235, 255, 70));
                g2d.drawRoundRect(3, 3, width - 6, height - 6, 14, 14);
            } finally {
                g2d.dispose();
            }
            super.paintComponent(g);
        }
    }
}
