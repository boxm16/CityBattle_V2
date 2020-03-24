/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Michail Sitmalidis
 */
public class Tank {

    private final String TYPE;
    private static int serialNumber = 0;
    private final int TANK_NUMBER;
    private final int TANK_LENGTH;
    private final int TANK_WIDTH;
    private final int directionSwitchPeriod;
    private int armour;
    private int speed;
    private ArrayList<Particle> hull;
    private int X, Y;//positions on BattleFiled(matrix)
    private File file;
    private Scanner sc = null;
    private BufferedImage image;//this is image painted on BattleField
    private BufferedImage imageNorth, imageSouth, imageEast, imageWest;
    private HashMap<String, BufferedImage> directionSwitchGear;//this is directions switching gear-switcher
    private Timer directionSwitchTimer;
    protected Timer engine;
    private int shellRechargeTime;
    private Timer shellRechargeGear;
    private Random randomGenerator;
    protected Direction direction;

    protected enum Direction {
        SOUTH, NORTH, EAST, WEST
    }

    public Tank(String type, int X, int Y) {
        this.TYPE = type;
        TANK_NUMBER = serialNumber++;
        TANK_LENGTH = 36;
        TANK_WIDTH = 36;
        this.X = X;//those are starting positions on battleField
        this.Y = Y;
        randomGenerator = new Random();
        hull = createHull();
        directionSwitchGear = new HashMap();
        directionSwitchPeriod = 3 * 1000;//3 seconds
        if (type.equals("T1")) {
            createT1();
        }
        if (type.equals("T2")) {
            createT2();
        }
        if (type.equals("T3")) {
            createT3();
        }

        placeTankOnBattleField();
        engine = new Timer(speed, new TankEngine());
        startEngine();
    }

    //Setters getters
    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getTANK_NUMBER() {
        return TANK_NUMBER;
    }

    public int getTANK_LENGTH() {
        return TANK_LENGTH;
    }

    public int getTANK_WIDTH() {
        return TANK_WIDTH;
    }

    public ArrayList<Particle> getHull() {

        return hull;
    }
    //end of setters getters

    private ArrayList<Particle> createHull() {
        hull = new ArrayList();

        for (int x = 0; x < TANK_LENGTH; x++) {
            for (int y = 0; y < TANK_WIDTH; y++) {
                if (y == 0 || y == TANK_WIDTH - 1) {
                    Particle particle = new Particle("Tank", TANK_NUMBER);
                    particle.setX(x);
                    particle.setY(y);
                    hull.add(particle);
                } else {
                    if (x == 0 || x == TANK_LENGTH - 1) {
                        Particle particle = new Particle("Tank", TANK_NUMBER);
                        particle.setX(x);
                        particle.setY(y);
                        hull.add(particle);
                    }
                }

            }
        }
        return hull;
    }

    private void createT1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createT2() {
        speed = 10;
        armour = 3;
        shellRechargeTime = 2000;
        try {
            // Imege of tank load.
            URL imageN = this.getClass().getResource("/citybattle_v2/resources/images/T2_NORTH.png");
            imageNorth = ImageIO.read(imageN);
            URL imageS = this.getClass().getResource("/citybattle_v2/resources/images/T2_SOUTH.png");
            imageSouth = ImageIO.read(imageS);
            URL imageE = this.getClass().getResource("/citybattle_v2/resources/images/T2_EAST.png");
            imageEast = ImageIO.read(imageE);
            URL imageW = this.getClass().getResource("/citybattle_v2/resources/images/T2_WEST.png");
            imageWest = ImageIO.read(imageW);
            directionSwitchGear.put("North", imageNorth);
            directionSwitchGear.put("South", imageSouth);
            directionSwitchGear.put("East", imageEast);
            directionSwitchGear.put("West", imageWest);
        } catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createT3() {
        speed = 20;
        armour = 5;
        shellRechargeTime = 3000;

    }

    private void placeTankOnBattleField() {
        image = directionSwitchGear.get("South");//turning tank to south

        for (Particle particle : hull) {
            BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
        }
    }

    protected void startEngine() {
        engine.start();
        startDirectionSwitcher();
    }

    protected void stopEngine() {
        engine.stop();
        directionSwitchTimer.stop();
    }

    private void startDirectionSwitcher() {
        directionSwitchTimer = new Timer(directionSwitchPeriod, new DirectionSwitchDispatcher());
        directionSwitchTimer.start();
    }

    private void moveSouth() {
        image = directionSwitchGear.get("South");//turning tank to south

        if (canGoSouth()) {

            Y++;
            for (int a = hull.size() - 1; a >= 0; a--) {
                Particle particle = hull.get(a);
                BattleField.matrix[X + particle.getX()][Y - 1 + particle.getY()] = null;
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }

    private void moveNorth() {
        image = directionSwitchGear.get("North");//turning tank to south

        if (canGoNorth()) {
            Y--;
            for (int a = 0;a<hull.size(); a++) {
                Particle particle = hull.get(a);
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
                BattleField.matrix[X + particle.getX()][Y + 1 + particle.getY()] = null;
            }
        }
    }

    private void moveEast() {
        image = directionSwitchGear.get("East");//turning tank to south

        if (canGoEast()) {//matrix.leng width of "baatlefield", 36-width of tank hull(its square 36x36)
            X++;
            for (int a = hull.size() - 1; a >= 0; a--) {
                Particle particle = hull.get(a);
                BattleField.matrix[X - 1 + particle.getX()][Y + particle.getY()] = null;
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }

    private void moveWest() {
        image = directionSwitchGear.get("West");//turning tank to south

        if (canGoWest()) {
            X--;
            for (int a = 0; a < hull.size(); a++) {
                Particle particle = hull.get(a);
                BattleField.matrix[X + 1 + particle.getX()][Y + particle.getY()] = null;
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }
//canGO

    private boolean canGoSouth() {
        if (Y < BattleField.matrix.length - 1 - 40) {
            for (int a = X; a < X + 36; a++) {
                Particle particle = BattleField.matrix[a][Y + 37];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean canGoNorth() {
        if (Y > 0) {
            for (int a = X; a < X + 36; a++) {
                Particle particle = BattleField.matrix[a][Y - 1];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean canGoEast() {
        if (X < BattleField.matrix[0].length - 36 - 1) {
            for (int a = Y; a < Y + 36; a++) {
                Particle particle = BattleField.matrix[X + 37][a];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean canGoWest() {
        if (X > 0) {
            for (int a = Y; a < Y + 36; a++) {
                Particle particle = BattleField.matrix[X - 1][a];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    protected class TankEngine implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction == Direction.SOUTH) {

                moveSouth();
            }
            if (direction == Direction.NORTH) {

                moveNorth();
            }
            if (direction == Direction.EAST) {

                moveEast();
            }
            if (direction == Direction.WEST) {

                moveWest();
            }
        }
    }

    class DirectionSwitchDispatcher implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Random randomGenerator = new Random();
            int randomDirection = randomGenerator.nextInt(6);
            if (randomDirection == 0) {
                direction = Direction.SOUTH;

            }
            if (randomDirection == 1) {
                direction = Direction.NORTH;

            }
            if (randomDirection == 2) {
                direction = Direction.EAST;

            }
            if (randomDirection == 3) {
                direction = Direction.WEST;
            }
            // trend to south, towards the goal
            if (randomDirection == 4) {
                direction = Direction.SOUTH;
            }
            if (randomDirection == 5) {
                direction = Direction.SOUTH;
            }
        }
    }
}
