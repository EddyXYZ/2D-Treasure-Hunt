package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public boolean collision = false;
    public int worldX, worldY;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public BufferedImage image;
    public String name;
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        // Subtract the tile's worldX/worldY and the player's worldX/worldY to get the tile's screenX/screenY position
        // Add the player's screenX/screenY to offset a difference that occurs
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Only spawn tiles that are close to the player/between +-screenX and +-screenY
        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}
