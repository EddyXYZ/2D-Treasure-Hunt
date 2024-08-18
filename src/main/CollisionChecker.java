package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void checkTile(Entity entity) {
        // Check the entity's collision box in world coordinates
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Determine the col and row numbers for the entity's collision box
        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        // Only check two tiles depending on the direction
        int tileNum1, tileNum2;
        switch(entity.direction) {
            case "up":
                // Try to determine which row the entity will end up at
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                // Check the two tiles that the entity is trying to "enter"
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                // If one of those tiles has collision on, turn the entity's collision on as well
                if (gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                // Try to determine which column the entity will end up at
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                // Check the two tiles that the entity is trying to "enter"
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                // If one of those tiles has collision on, turn the entity's collision on as well
                if (gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for(int i = 0; i < gamePanel.obj.length; i++) { // Scan all the objects in GamePanel's obj[]
            if(gamePanel.obj[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                // Get object's solid area position
                gamePanel.obj[i].solidArea.x += gamePanel.obj[i].worldX;
                gamePanel.obj[i].solidArea.y += gamePanel.obj[i].worldY;

                switch(entity.direction) {
                    case "up":
                        // Simulating the entity's movement and checking where it will be after it moved
                        entity.solidArea.y -= entity.speed;
                        // Check for collision with .intersects
                        if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                // Reset the entity's solidArea to prevent its x and y values from increasing too much
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
