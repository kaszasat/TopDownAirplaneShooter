package topdownairplaneshooter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class Sky extends JPanel implements ActionListener {

    private final Timer timer;
    private boolean ingame;
    private final byte DELAY = 4;
    private final byte NUMBER_OF_ENEMIES = 12;
    private final List<Enemy> enemies;
    private final short WIDTH, HEIGHT;
    private final short PLANE_X, PLANE_Y;
    private final AirPlaneModel airplanemodel;
    private short numberOfEnemiesKilled = 0;
    private final Random random = new Random();

    public Sky(short width, short height) throws IOException {
        WIDTH = width;
        HEIGHT = height;
        PLANE_X = (short)(WIDTH / 2);
        PLANE_Y = (short)(HEIGHT / 2);
        //setSize(WIDTH, HEIGHT);
        setBackground(Color.decode("#87CEEB"));
        ingame = true;
        setFocusable(true);
        //setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //setPreferredSize();
        airplanemodel = new AirPlaneModel(PLANE_X, PLANE_Y, WIDTH, HEIGHT);
        enemies = new ArrayList<>();
        for (int i = 0; i < random.nextInt(NUMBER_OF_ENEMIES)+NUMBER_OF_ENEMIES/2; i++)
            enemies.add(new Enemy(
                    (short)random.nextInt((int)(WIDTH * 0.8)), 
                    (short)random.nextInt((int)(HEIGHT * 0.1)),
                    WIDTH,
                    HEIGHT));
        (timer = new Timer(DELAY, this)).start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (ingame) drawObjects(g);
        else {
            if (!enemies.isEmpty()) drawGameOver(g);
            else drawWon(g);            
        }
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawWon(Graphics g) {
        String message = "You've Won";
        Font huge = new Font("Arial", Font.BOLD, 250);
        FontMetrics fm = getFontMetrics(huge);
        g.setColor(Color.white);
        g.setFont(huge);
        g.drawString(message, (WIDTH - fm.stringWidth(message)) / 2, HEIGHT / 2);
    }

    private void drawObjects(Graphics g) {
        if (airplanemodel.isVisible()) g.drawImage(airplanemodel.getImage(), airplanemodel.getX(), airplanemodel.getY(), this);
        List<Missile> ms = airplanemodel.getMissiles();
        for (Missile missile : ms)  if (missile.isVisible()) g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
        for (Enemy enemy : enemies) if (enemy.isVisible()) g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
        g.setColor(Color.WHITE);
        Font medium = new Font("Arial", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(medium);
        g.setColor(Color.white);
        g.setFont(medium);
        g.drawString("Enemies left: " + enemies.size(), 20, 35);
    }

    private void drawGameOver(Graphics g) {
        String message = "Game Over";
        Font huge = new Font("Arial", Font.BOLD, 250);
        FontMetrics fm = getFontMetrics(huge);
        g.setColor(Color.white);
        g.setFont(huge);
        g.drawString(message, (WIDTH - fm.stringWidth(message)) / 2, HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updateAirPlane();
        updateMissiles();
        updateEnemies();
        checkCollisions();
        if (numberOfEnemiesKilled == 4) {
            numberOfEnemiesKilled = 0;
            for (int i = 0; i < 3; i++)
                try {
                    enemies.add(new Enemy(
                            (short)0, 
                            (short)random.nextInt(1920),
                            WIDTH,
                            HEIGHT));
                } catch (IOException ex) {
                    Logger.getLogger(Sky.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        repaint();
    }

    private void inGame() {
        if (!ingame) timer.stop();
    }

    private void updateMissiles() {
        List<Missile> missiles = airplanemodel.getMissiles();
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if (missile.isVisible()) missile.move();
            else missiles.remove(i);
        }
    }

    private void updateAirPlane() {
        if (airplanemodel.isVisible()) airplanemodel.move();
    }

    private void updateEnemies() {
        if (enemies.isEmpty()) {
            ingame = false;
            return;
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isVisible()) enemy.move();
            else {
                enemies.remove(i);
                numberOfEnemiesKilled += 1;
            }
        }
    }

    public void checkCollisions() {
        Rectangle airplane = airplanemodel.getBounds();
        for (Enemy enemy : enemies) {
            Rectangle enemyRectangle = enemy.getBounds();
            if (airplane.intersects(enemyRectangle)) {
                airplanemodel.setVisible(false);
                enemy.setVisible(false);
                ingame = false;
            }
        }
        List<Missile> missiles = airplanemodel.getMissiles();
        for (Missile missile : missiles) {
            Rectangle missileRectangle = missile.getBounds();
            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (missileRectangle.intersects(enemyRectangle)) {
                    missile.setVisible(false);
                    enemy.setVisible(false);
                }
            }
        }
    }
}
