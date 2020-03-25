/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Michail Sitmalidis
 */
public class Shell {

    private final String TYPE;
    private static int serialNumber = 0;
    private final int SHELL_NUMBER;
    private final int SHELL_LENGTH;
    private final int SHELL_WIDTH;
    private int shellDamagePower;
    private int shellRange;
    private int fireRange;
    private int X, Y;//positions on BattleFiled(matrix)
    private BufferedImage explosionAnimImg;
    protected Direction direction;
    private Timer deleteBlast;
    private boolean bigExplosion;
    private int explosionTime;

    protected enum Direction {
        SOUTH, NORTH, EAST, WEST
    }

    public Shell(String type) {
        this.TYPE = type;
        SHELL_NUMBER = serialNumber++;
        SHELL_LENGTH = 10;
        SHELL_WIDTH = 1;
        if (TYPE.equals("S1")) {
            createS1();
        }
        if (TYPE.equals("S2")) {
            createS2();
        }
        if (TYPE.equals("S3")) {
            createS3();
        }

        //load explosion image form file
        try {
            // Imege of explosion animation.
            URL explosionAnimImgUrl = this.getClass().getResource("/citybattle_v2/resources/images/explosion_anim.png");
            explosionAnimImg = ImageIO.read(explosionAnimImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createS1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createS2() {
        shellDamagePower = 2;
        shellRange = 600;
    }

    private void createS3() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void fire(int[] barrel, Tank.Direction direction) {
        X = barrel[0];
        Y = barrel[1];
        fireRange = Y - shellRange;
        if (direction == Tank.Direction.NORTH) {
            this.direction = Direction.NORTH;
            shootNorth();
        }
        if (direction == Tank.Direction.SOUTH) {
            this.direction = Direction.SOUTH;
            shootSouth();
        }
        if (direction == Tank.Direction.EAST) {
            this.direction = Direction.EAST;
            shootEast();
        }
        if (direction == Tank.Direction.WEST) {
            this.direction = Direction.WEST;
            shootWest();
        }

    }

    private void shootNorth() {
        bigExplosion = false;
        while (Y >= 0) {//till it reaches north edge of battlefield
            Particle particle = new Particle("Shell", SHELL_NUMBER);
            BattleField.matrix[X][Y] = particle;
            if (Y < fireRange) {
                break;
            }
            Y--;
            if (Y > 0) {
                Particle p1 = BattleField.matrix[X][Y - 1];
                if (p1 != null) {
                    if (p1.getName().equals("Tank")) {
                        Tank tank = BattleField.tanksOnField.get(p1.getStamp());
                        if (tank.getArmour() <= shellDamagePower) {
                            bigExplosion = true;
                        } else {
                            tank.setArmour(tank.getArmour() - shellDamagePower);
                        }
                    }
                    break;
                }
            }

        }
        explode(X, Y, bigExplosion);
    }

    private void shootSouth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void shootEast() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void shootWest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void explode(int X, int Y, boolean bigExplosion) {

        int explosionPower = 1;
        if (bigExplosion) {
            explosionPower = 12;
        }

        Animation expAnim = new Animation(explosionAnimImg, 134, 134, explosionPower, 45, false, X - 65, Y - 70, 1);
        BattleField.explosionsList.put(SHELL_NUMBER, expAnim);
        if (bigExplosion) {
            explosionTime = 1000;
        } else {
            explosionTime = 200;
        }
        deleteBlast = new Timer(explosionTime, new DeleteBlust());
        deleteBlast.start();
    }

    class DeleteBlust implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // removing explosion from the list.
            BattleField.explosionsList.remove(SHELL_NUMBER);
           deleteBlast.stop();
             deleteBlast = null;
        }

    }
}
