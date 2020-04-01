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
public class Base {

    private int X, Y;

    private final int LENGTH;
    private final int WIDTH;
    private ArrayList<Particle> hull;
    private final String name;
    private String status;

    public Base(int X, int Y) {
        this.X = X;
        this.Y = Y;
        LENGTH = 60;
        WIDTH = 40;
        name = "Base";
        hull = createHull();
        placeBaseOnBattleField();
        status = "active";
    }

    private ArrayList createHull() {
        hull = new ArrayList();

        for (int x = 0; x < LENGTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (y == 0 || y == WIDTH - 1) {
                    Particle particle = new Particle(name, 0);
                    particle.setX(x);
                    particle.setY(y);
                    hull.add(particle);
                } else {
                    if (x == 0 || x == LENGTH - 1) {
                        Particle particle = new Particle(name, 0);
                        particle.setX(x);
                        particle.setY(y);
                        hull.add(particle);
                    }
                }

            }
        }
        return hull;
    }

    private void placeBaseOnBattleField() {
        for (Particle particle : hull) {
            BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
        }
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    void destroy() {
        for (Particle particle : hull) {
            BattleField.matrix[X + particle.getX()][Y + particle.getY()] = null;
        }
        status = "destroyed";
    }

}
