package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // BufferedImage is used to describe an accessible buffer of image data
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;
    public Rectangle solidArea; // Create an invisible rectangle with properties like width/height

    public int worldX, worldY; // Not the position on screen, but the position on the world map
    public int speed;
    public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public int spriteCounter = 0;
    public int spriteNumber = 1;
}
