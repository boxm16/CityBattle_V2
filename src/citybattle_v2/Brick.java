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
public class Brick {

    private int X, Y;
    private final int LENGTH;
    private final int WIDTH;
    private ArrayList<Particle> hull;
    private final String name;
    private static int serialNumber = 0;
    private final int BRICK_NUMBER;
    private String status;

    public Brick(int X, int Y) {
        this.X = X;
        this.Y = Y;
        LENGTH = 20;
        WIDTH = 20;
        name = "Brick";
        BRICK_NUMBER = serialNumber++;
        hull = createHull();
        status = "inTheWall";

    }

    private ArrayList createHull() {
        hull = new ArrayList();

        for (int x = 0; x < LENGTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (y == 0 || y == WIDTH - 1) {
                    Particle particle = new Particle(name, BRICK_NUMBER);
                    particle.setX(x);
                    particle.setY(y);
                    hull.add(particle);
                } else {
                    if (x == 0 || x == LENGTH - 1) {
                        Particle particle = new Particle(name, BRICK_NUMBER);
                        particle.setX(x);
                        particle.setY(y);
                        hull.add(particle);
                    }
                }

            }
        }
        return hull;
    }

    public int getBRICK_NUMBER() {
        return BRICK_NUMBER;
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

    public void destroyBrick() {
        //removing all particlas from Battlefield
        for (Particle particle : hull) {
            BattleField.matrix[X + particle.getX()][Y + particle.getY()] = null;
        }
        status = "destroyed";
    }
}
