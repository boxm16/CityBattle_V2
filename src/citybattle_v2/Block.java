/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.util.ArrayList;

/**
 *
 * @author Michail Sitmalidis
 */
public class Block {

    private int X, Y;
    private final int LENGTH;
    private final int WIDTH;
    private ArrayList<Particle> hull;
    private final String name;
    private static int serialNumber = 0;
    private final int BLOCK_NUMBER;
    private String status;

    public Block(String type, int X, int Y) {
        this.X = X;
        this.Y = Y;
        LENGTH = 20;
        WIDTH = 20;
        name = type;
        BLOCK_NUMBER = serialNumber++;
        hull = createHull();
        status = "inTheWall";

    }

    private ArrayList createHull() {
        hull = new ArrayList();

        for (int x = 0; x < LENGTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (y == 0 || y == WIDTH - 1) {
                    Particle particle = new Particle(name, BLOCK_NUMBER);
                    particle.setX(x);
                    particle.setY(y);
                    hull.add(particle);
                } else {
                    if (x == 0 || x == LENGTH - 1) {
                        Particle particle = new Particle(name, BLOCK_NUMBER);
                        particle.setX(x);
                        particle.setY(y);
                        hull.add(particle);
                    }
                }

            }
        }
        return hull;
    }

    public int getBLOCK_NUMBER() {
        return BLOCK_NUMBER;
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public ArrayList getHull() {
        return hull;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
    

    public void destroyBlock() {
        if (name.equals("Brick")) {
            //removing all particlas from Battlefield
            for (Particle particle : hull) {
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = null;
            }
            status = "destroyed";
        }
    }
}
