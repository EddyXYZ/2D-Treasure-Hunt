package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // --- SCREEN SETTINGS --- //
    final int FPS = 60;
    final int originalTileSize = 16;
    final int SCALE = 3;
    public final int tileSize = originalTileSize * SCALE; // 16 * 3 = 48 pixels or 48x48

    // Screen size will be 768x576 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // --- WORLD SETTINGS --- //
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // --- CLASSES --- //
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public SuperObject obj[] = new SuperObject[10];
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Set the size of this class
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Completes the drawing in an offscreen painting bugger, improving performance
        this.addKeyListener(keyHandler); // Add KeyHandler to GamePanel
        this.setFocusable(true); // Used to "focus" GamePanel to receive key input
    }

    public void setUpGame() {
        assetSetter.setObject();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Automatically calls run()
    }

    @Override
    public void run() { // From Runnable
        double drawInterval = 1000000000 / FPS;
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (deltaTime >= 1) {
                update();
                repaint(); // This calls paintComponent()
                deltaTime--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics; // Convert graphics to graphics2D
        // Tiles
        tileManager.draw(graphics2D);

        // Objects
        for(int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(graphics2D, this);
            }
        }

        // Player
        player.draw(graphics2D);

        // UI
        ui.draw(graphics2D);

        graphics2D.dispose(); // Free up space and save memory by disposing of drawing after its drawn
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) { // Sound effect
        se.setFile(i);
        se.play();
    }
}
