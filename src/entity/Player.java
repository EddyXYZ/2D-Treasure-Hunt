package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    public final int screenX, screenY; // Determines where to draw player on the screen
    public int hasKey = 0;

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gamePanel = gp;
        this.keyHandler = kh;

        // Draw the player at the middle of the screen
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        // Reference image for player collision box in /references
        solidArea = new Rectangle(8, 16, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down"; // Must set a default direction or error occurs
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { // This is called in the main game loop in GamePanel/update()
        if (keyHandler.upPressed == true || keyHandler.downPressed == true ||
            keyHandler.rightPressed == true || keyHandler.leftPressed == true) {
            if (keyHandler.upPressed == true) {
                direction = "up";
            }
            else if (keyHandler.downPressed == true) {
                direction = "down";
            }
            else if (keyHandler.rightPressed == true) {
                direction = "right";
            }
            else if (keyHandler.leftPressed == true) {
                direction = "left";
            }

            // Check for tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // Check for object collision
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if(collisionOn == false) { // Enable movement if collision is false
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) { // spriteCounter controls the speed of the animation
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                }
                else if (spriteNumber == 2) {
                    spriteNumber =1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) { // if index still equals 999, this means no object has been picked up yet
            String objName = gamePanel.obj[index].name;
            switch(objName) {
                case "Key":
                    gamePanel.playSE(1);
                    hasKey++;
                    gamePanel.obj[index] = null;
                    gamePanel.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gamePanel.playSE(3);
                        gamePanel.obj[index] = null;
                        hasKey--;
                        gamePanel.ui.showMessage("You opened a door!");
                    }
                    else {
                        gamePanel.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gamePanel.playSE(2);
                    speed += 2;
                    gamePanel.obj[index] = null;
                    gamePanel.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D) { // This is called in the main game loop in GamePanel/paintComponent()
        BufferedImage image = null;
        switch(direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                }
                if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                }
                if (spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
                break;
        }
        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}