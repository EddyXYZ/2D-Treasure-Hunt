package main;

import entity.Player;
import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Font arial40, arial80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00"); // Use this to convert playTime to 0.00 format

    public UI (GamePanel gp) {
        this.gamePanel = gp;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        if(gameFinished == true) {
            String text;
            int textLength;
            int x; // Represents x position on the screen
            int y; // Represents y position on the screen

            // Create a "treasure message"
            graphics2D.setFont(arial40);
            graphics2D.setColor(Color.WHITE);

            // Get the length of the treasure message
            text = "You found the treasure!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

            // Draw the treasure message at the center of the screen
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 - (gamePanel.tileSize * 3);
            graphics2D.drawString(text, x, y);

            // Create a congratulations message
            graphics2D.setFont(arial80B);
            graphics2D.setColor(Color.YELLOW);

            // Get the length of the congratulations message
            text = "Congratulations!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

            // Draw the congratulations message at the center of the screen
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);
            graphics2D.drawString(text, x, y);

            // Stop the game/end the thread
            gamePanel.gameThread = null;
        }
        else {
            graphics2D.setFont(arial40);
            graphics2D.setColor(Color.WHITE);

            // Draw the current game time
            playTime += (double)1/60; // Add 1/60 seconds every 60 frames
            graphics2D.drawString("Time: " + decimalFormat.format(playTime), gamePanel.tileSize * 11, 65);

            // Draw the key image on screen
            graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
            graphics2D.drawString("x " + gamePanel.player.hasKey, 74, 65);

            // Draw the message
            if(messageOn == true) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(30f)); // Change the current font size without instancing a new one
                graphics2D.drawString(message, gamePanel.tileSize /2, gamePanel.tileSize * 5);
                messageCounter++;
                // Disable message after 2 seconds
                if(messageCounter > 120) { // 120 frames or 60 frames for 2 seconds
                    messageCounter = 0;
                    messageOn = false;
                }
        }
        }
    }
}
