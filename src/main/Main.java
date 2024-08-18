package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");
        window.add(gamePanel); // Add GamePanel to this window
        window.pack(); // Sizes the window to fit the preferred sizes in GamePanel
        window.setLocationRelativeTo(null); // Has to be below pack() in order to appear in center
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}