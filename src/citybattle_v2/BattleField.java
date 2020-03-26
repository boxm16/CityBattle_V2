/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

/**
 *
 * @author Michail Sitmalidis
 */
public class BattleField extends JPanel {

    private final int WIDTH;
    private final int HEIGHT;
    public static Particle matrix[][];
    public static HashMap<Integer, Tank> tanksOnField;
    public static HashMap<Integer, Animation> explosionsList;
    public static LinkedList<Shell> shells;

    public BattleField() {

        WIDTH = 800;
        HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        matrix = new Particle[WIDTH][HEIGHT];
        tanksOnField = new HashMap();
        explosionsList = new HashMap();
        shells = new LinkedList();
    }

//-------------------------------------------->
    public void paintComponent(Graphics g) {
        //   super.paintComponent(g);
//----this is for images
        for (Tank tank : tanksOnField.values()) {

            if (tank.getStatus().equals("active")) {
                g.drawImage(tank.getImage(), tank.getX(), tank.getY(), tank.getTANK_LENGTH(), tank.getTANK_WIDTH(), null);
            }
        }
//------------------this is for matrix painting
        /*    for (int a = 0; a < WIDTH; a++) {
            for (int b = 0; b < HEIGHT; b++) {
                Particle particle = matrix[a][b];
                if (particle != null) {
                    if (particle.getName().equals("Tank")) {
                        g.setColor(Color.red);
                        g.drawRect(a, b, 1, 1);
                    }
                    if (particle.getName().equals("Shell")) {
                        g.setColor(Color.yellow);
                        g.drawRect(a, b, 1, 1);
                    }
                }
            }
        }*/
        //------------for animation(explosion and so on)
        Graphics2D g2d = (Graphics2D) g;
        Draw(g2d);

    }

    int bubu = 0;

    void newTank() {
        /*   for (int a = 0; a < 15; a++) {
            Tank tank = new Tank("T2", a * 50, 0);
            tanksOnField.put(tank.getTANK_NUMBER(), tank);
        }
         */

        Tank tank = new Tank("T2", "EnemyTank", bubu, 0);
        tanksOnField.put(tank.getTANK_NUMBER(), tank);
        bubu = bubu + 50;
        if (bubu > 600) {
            bubu = 0;
        }
    }

    void myNewTank() {
        MyTank tank = new MyTank("T2", "MyTank", 500, 600);
        tanksOnField.put(tank.getTANK_NUMBER(), tank);

    }

    public void Draw(Graphics2D g2d) {

        for (Animation animation : explosionsList.values()) {
            animation.Draw(g2d);
        }

    }

}
