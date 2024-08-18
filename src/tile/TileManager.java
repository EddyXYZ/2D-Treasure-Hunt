package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNumber[][]; // [row][column], picture in /maps
    UtilityTool uTool = new UtilityTool();

    public TileManager(GamePanel gp) {
        this.gamePanel = gp;
        tile = new Tile[10]; // Create # amount of tiles
        mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        setUp(0, "grass", false);
        setUp(1, "wall", true);
        setUp(2, "water", true);
        setUp(3, "earth", false);
        setUp(4, "tree", true);
        setUp(5, "sand", false);
    }

    public void setUp(int index, String imageName, boolean collision) { // Used to increase performance through BufferedImage and pre-scaling
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void loadMap(String mapFile) {
        try {
            InputStream is = getClass().getResourceAsStream(mapFile); // InputStream is used to import map.txt
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // BufferedReader is used to read map.txt

            int col = 0;
            int row = 0;
            int num; // Represents the actual tile type/type[#] in a line/row

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine(); // BufferedReader reads a single line/row

                while(col < gamePanel.maxWorldCol) {
                    String numbers[] = line.split(" "); // Input the strings in a single line/row and append them to numbers[]
                    num = Integer.parseInt(numbers[col]); // Use col as an index reference and change each string to an integer
                    mapTileNumber[col][row] = num; // Replace the current mapTileNumber[col][row] with the extracted integer
                    col++;
                }
                // Move to the next row once all the numbers in the current row are read and inputted
                if(col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close(); // Don't forget to close BufferedReader
        }
        catch(Exception e) {

        }
    }

    public void draw(Graphics2D graphics2D) { // Requires Graphics2D to draw
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNumber[worldCol][worldRow]; // Represents the actual tile type/type[#] in a line/row

            // Get the tile's world position in pixels
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;

            // Subtract the tile's worldX/worldY and the player's worldX/worldY to get the tile's screenX/screenY position
            // Add the player's screenX/screenY to offset a difference that occurs
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            // Only spawn tiles that are close to the player/between +-screenX and +-screenY
            if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
               worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
               worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
               worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
               graphics2D.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if(worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
