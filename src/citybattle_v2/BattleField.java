/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
    public static HashMap<Integer, Brick> cityWall;
    public static LinkedList<Shell> shells;
    private BufferedImage imageWall;

    public BattleField() {

        WIDTH = 800;
        HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setBackground(Color.RED);
        matrix = new Particle[WIDTH][HEIGHT];
        tanksOnField = new HashMap();
        explosionsList = new HashMap();
        cityWall = new HashMap();
        shells = new LinkedList();
//for bricks
        try {
            URL imageW = this.getClass().getResource("/resources/images/brick.png");
            imageWall = ImageIO.read(imageW);
        } catch (IOException ex) {
            Logger.getLogger(BattleField.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//-------------------------------------------->
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//----this is for tanks images
        for (Tank tank : tanksOnField.values()) {
            if (tank.getStatus().equals("active")) {
                g.drawImage(tank.getImage(), tank.getX(), tank.getY(), tank.getTANK_LENGTH(), tank.getTANK_WIDTH(), null);
            }
        }
        //this is for bricks images
        for (Brick brick : cityWall.values()) {
            if (brick.getStatus().equals("inTheWall")) {
                g.drawImage(imageWall, brick.getX(), brick.getY(), brick.getLENGTH(), brick.getWIDTH(), null);
            }
        }
        //---
//------------------this is for matrix painting
        /*   for (int a = 0; a < WIDTH; a++) {
            for (int b = 0; b < HEIGHT; b++) {
                Particle particle = matrix[a][b];
                if (particle != null) {
                    if (particle.getName().equals("EnemyTank___")) {
                        g.setColor(Color.red);
                        g.drawRect(a, b, 1, 1);
                    }
                    if (particle.getName().equals("Shell")) {
                        g.setColor(Color.yellow);
                        g.drawRect(a, b, 1, 1);
                    }
                    if (particle.getName().equals("Brick---")) {
                        g.setColor(Color.blue);
                        g.drawRect(a, b, 1, 1);
                    }
                }
            }
        }
         */
        //------------for animation(explosion and so on)
        Graphics2D g2d = (Graphics2D) g;
        Draw(g2d);

    }
//-----------
    int bubu = 0;

    void newTank() {
        String type = "T2";
        for (int a = 0; a < 15; a++) {
            if (a > 4) {
                type = "T3";
            }
            Tank tank = new Tank(type, "EnemyTank", a * 50, 0);
            tanksOnField.put(tank.getTANK_NUMBER(), tank);
        }

        /* 
        Tank tank = new Tank("T2", "EnemyTank", bubu, 0);
        tanksOnField.put(tank.getTANK_NUMBER(), tank);
        bubu = bubu + 50;
        if (bubu > 600) {
            bubu = 0;
        }   */
    }

    public void myNewTank() {
        MyTank tank = new MyTank("T2", "MyTank", 750, 750);
        tanksOnField.put(tank.getTANK_NUMBER(), tank);

    }

    public void Draw(Graphics2D g2d) {

        //i`m afraid of ConcurretnModificExept. so i use Iteratero insted of for-each loop 
        //and yet it happens some time, need to find another way, syncronize or something
        Iterator it = BattleField.explosionsList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Animation animation = (Animation) pair.getValue();
            animation.Draw(g2d);

        }
    }
}
