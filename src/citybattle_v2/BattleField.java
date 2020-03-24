/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 *
 * @author Michail Sitmalidis
 */
public class BattleField extends JPanel {

    private final int WIDTH;
    private final int HEIGHT;
    public static Particle matrix[][];
    private HashMap<Integer, Tank> tanksOnField;

    public BattleField() {

        WIDTH = 800;
        HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        matrix = new Particle[WIDTH][HEIGHT];
        tanksOnField = new HashMap();
    }

//-------------------------------------------->
    public void paintComponent(Graphics g) {
        //   super.paintComponent(g);
        for (Tank tank : tanksOnField.values()) {
            g.drawImage(tank.getImage(), tank.getX(), tank.getY(), tank.getTANK_LENGTH(), tank.getTANK_WIDTH(), null);
        }

    }

    void newTank() {
        Tank tank = new Tank("T2", 0, 0);
        tanksOnField.put(tank.getTANK_NUMBER(), tank);
    }
    void myNewTank() {
        MyTank tank = new MyTank("T2", 500, 0);
        tanksOnField.put(tank.getTANK_NUMBER(), tank);
         
    }

}
