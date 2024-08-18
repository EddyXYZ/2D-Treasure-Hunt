package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void setObject() { // Instantiate new objects into the world map
        gamePanel.obj[0] = new OBJ_Key(gamePanel);
        gamePanel.obj[0].worldX = gamePanel.tileSize * 23;
        gamePanel.obj[0].worldY = gamePanel.tileSize * 7;

        gamePanel.obj[1] = new OBJ_Key(gamePanel);
        gamePanel.obj[1].worldX = gamePanel.tileSize * 23;
        gamePanel.obj[1].worldY = gamePanel.tileSize * 40;

        gamePanel.obj[2] = new OBJ_Key(gamePanel);
        gamePanel.obj[2].worldX = gamePanel.tileSize * 38;
        gamePanel.obj[2].worldY = gamePanel.tileSize * 8;

        gamePanel.obj[3] = new OBJ_Door(gamePanel);
        gamePanel.obj[3].worldX = gamePanel.tileSize * 10;
        gamePanel.obj[3].worldY = gamePanel.tileSize * 11;

        gamePanel.obj[4] = new OBJ_Door(gamePanel);
        gamePanel.obj[4].worldX = gamePanel.tileSize * 8;
        gamePanel.obj[4].worldY = gamePanel.tileSize * 28;

        gamePanel.obj[5] = new OBJ_Door(gamePanel);
        gamePanel.obj[5].worldX = gamePanel.tileSize * 12;
        gamePanel.obj[5].worldY = gamePanel.tileSize * 22;

        gamePanel.obj[6] = new OBJ_Chest(gamePanel);
        gamePanel.obj[6].worldX = gamePanel.tileSize * 10;
        gamePanel.obj[6].worldY = gamePanel.tileSize * 7;

        gamePanel.obj[7] = new OBJ_Boots(gamePanel);
        gamePanel.obj[7].worldX = gamePanel.tileSize * 37;
        gamePanel.obj[7].worldY = gamePanel.tileSize * 42;
    }
}
