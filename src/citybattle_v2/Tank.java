/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Michail Sitmalidis
 */
public class Tank {

    protected int speedTik;
    private int directionTik;
    private int fireTik;
    protected boolean clutch;
    private String name;
    protected String status;
    private final String TYPE;
    private static int serialNumber = 0;
    private final int TANK_NUMBER;
    private final int TANK_LENGTH;
    private final int TANK_WIDTH;
    private final int directionSwitchPeriod;
    private int armour;
    protected int speed;
    private ArrayList<Particle> hull;
    protected int barrel[];
    private int X, Y;//positions on BattleFiled(matrix)
    private Scanner sc = null;
    private URL imageN, imageS, imageE, imageW;
    private BufferedImage image;//this is image painted on BattleField
    private BufferedImage imageNorth, imageSouth, imageEast, imageWest;
    protected BufferedImage explosionAnimImg;
    private HashMap<String, BufferedImage> directionSwitchGear;//this is directions switching gear-switcher

    private int rateOfFire;

    private Random randomGenerator;
    protected Direction direction;

    public Tank(String type, String name, int X, int Y) {
        speedTik = 0;
        directionTik = 0;
        fireTik = 0;
        this.name = name;
        this.TYPE = type;
        status = "active";
        TANK_NUMBER = serialNumber++;
        TANK_LENGTH = 37;
        TANK_WIDTH = 37;
        this.X = X;//those are starting positions on battleField
        this.Y = Y;
        randomGenerator = new Random();
        hull = createHull();
        barrel = new int[2];
        directionSwitchGear = new HashMap();
        directionSwitchPeriod = 1000;//3 seconds

        //shell explosion image
        //i load it here, one time, because it takes resources to load for every explosion different image
        URL explosionAnimImgUrl = this.getClass().getResource("/resources/images/explosion_anim.png");
        try {
            explosionAnimImg = ImageIO.read(explosionAnimImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        }
        //--

        if (TYPE.equals("T1")) {
            createT1();
        }
        if (TYPE.equals("T2")) {
            createT2();
        }
        if (TYPE.equals("T3")) {
            createT3();
        }
       

        placeTankOnBattleField();
        // engine = new Timer(speed, new TankEngine());
        startMoving();
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

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public String getTankName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
    //end of setters getters

    protected ArrayList<Particle> createHull() {
        hull = new ArrayList();

        for (int x = 0; x < TANK_LENGTH; x++) {
            for (int y = 0; y < TANK_WIDTH; y++) {
                if (y == 0 || y == TANK_WIDTH - 1) {
                    Particle particle = new Particle(name, TANK_NUMBER);
                    particle.setX(x);
                    particle.setY(y);
                    hull.add(particle);
                } else {
                    if (x == 0 || x == TANK_LENGTH - 1) {
                        Particle particle = new Particle(name, TANK_NUMBER);
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
        speed = 2;
        armour = 3;
        rateOfFire = 300;
        try {
            // Imege of tank load.
            imageN = this.getClass().getResource("/resources/images/T2_NORTH.png");
            imageNorth = ImageIO.read(imageN);
            imageS = this.getClass().getResource("/resources/images/T2_SOUTH.png");
            imageSouth = ImageIO.read(imageS);
            imageE = this.getClass().getResource("/resources/images/T2_EAST.png");
            imageEast = ImageIO.read(imageE);
            imageW = this.getClass().getResource("/resources/images/T2_WEST.png");
            imageWest = ImageIO.read(imageW);

            //directions gear
            directionSwitchGear.put("North", imageNorth);
            directionSwitchGear.put("South", imageSouth);
            directionSwitchGear.put("East", imageEast);
            directionSwitchGear.put("West", imageWest);
        } catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createT3() {

        speed = 5;
        armour = 5;
        rateOfFire = 500;
        try {
            // Imege of tank load.
            imageN = this.getClass().getResource("/resources/images/T3-NORTH.png");
            imageNorth = ImageIO.read(imageN);
            imageS = this.getClass().getResource("/resources/images/T3-SOUTH.png");
            imageSouth = ImageIO.read(imageS);
            imageE = this.getClass().getResource("/resources/images/T3-EAST.png");
            imageEast = ImageIO.read(imageE);
            imageW = this.getClass().getResource("/resources/images/T3-WEST.png");
            imageWest = ImageIO.read(imageW);

            //directions gear
            directionSwitchGear.put("North", imageNorth);
            directionSwitchGear.put("South", imageSouth);
            directionSwitchGear.put("East", imageEast);
            directionSwitchGear.put("West", imageWest);
        } catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void placeTankOnBattleField() {
        image = directionSwitchGear.get("South");//turning tank to south

        for (Particle particle : hull) {
            BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
        }
    }
//this thing make tank move, tik come from timer in MainFrame 

    public void tik() {
        speedTik = speedTik + 1;
        directionTik = directionTik + 1;
        fireTik = fireTik + 1;
        if (fireTik == rateOfFire) {
            fireTik = 0;
            fire();
        }
        if (speedTik == speed && clutch) {
            speedTik = 0;
            move();
        }
        if (directionTik == directionSwitchPeriod) {
            directionTik = 0;
            turn();
        }

    }

    protected void startMoving() {
        clutch = true;
    }

    protected void stopMoving() {
        clutch = false;
    }

// turning
    private void turnSouth() {
        image = directionSwitchGear.get("South");//turning tank to south
        barrel[0] = X + (TANK_WIDTH - 1) / 2;
        barrel[1] = Y + TANK_LENGTH;
    }

    private void turnNorth() {
        image = directionSwitchGear.get("North");//turning tank to north
        barrel[0] = X + (TANK_WIDTH - 1) / 2;
        barrel[1] = Y;
    }

    private void turnEast() {
        image = directionSwitchGear.get("East");//turning tank to north
        barrel[0] = X + TANK_LENGTH;
        barrel[1] = Y + (TANK_WIDTH - 1) / 2 + 1;
    }

    private void turnWest() {
        image = directionSwitchGear.get("West");//turning tank to north
        barrel[0] = X;
        barrel[1] = Y + (TANK_WIDTH - 1) / 2 + 1;;
    }

    private void moveSouth() {

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
        if (canGoNorth()) {
            Y--;
            for (int a = 0; a < hull.size(); a++) {
                Particle particle = hull.get(a);
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
                BattleField.matrix[X + particle.getX()][Y + 1 + particle.getY()] = null;
            }
        }
    }

    private void moveEast() {

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
            for (int a = X; a < X + TANK_WIDTH; a++) {
                Particle particle = BattleField.matrix[a][Y + TANK_LENGTH + 1];
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
            for (int a = X; a < X + TANK_LENGTH; a++) {
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
        if (X < BattleField.matrix[0].length - TANK_WIDTH - 1) {
            for (int a = Y; a < Y + 36; a++) {
                Particle particle = BattleField.matrix[X + TANK_WIDTH + 1][a];
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
            for (int a = Y; a < Y + TANK_LENGTH; a++) {
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

    protected void fire() {

        //Shell shell = BattleField.shells.removeFirst();
        String shellType = "S1";

        if (TYPE.equals("T2")) {
            shellType = "S2";
        }
        if (TYPE.equals("T3")) {
            shellType = "S3";
        }

        Shell shell = new Shell(shellType, "MyTank", explosionAnimImg);
        shell.fire(barrel, direction);
    }

    //start classes
    protected void move() {
        if (direction == Direction.SOUTH) {
            turnSouth();
            moveSouth();
        }
        if (direction == Direction.NORTH) {
            turnNorth();
            moveNorth();
        }
        if (direction == Direction.EAST) {
            turnEast();
            moveEast();
        }
        if (direction == Direction.WEST) {
            turnWest();
            moveWest();
        }
    }

    private void turn() {
        
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

    void destroyTank() {
        stopMoving();//stop moving
        //removing all particlas from Battlefield
        for (Particle particle : hull) {
            BattleField.matrix[X + particle.getX()][Y + particle.getY()] = null;
        }
        status = "destroyed";

    }

}
