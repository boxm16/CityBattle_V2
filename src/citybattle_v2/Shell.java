/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.image.BufferedImage;

/**
 *
 * @author Michail Sitmalidis
 */
public class Shell {

    private String target;
    private final String TYPE;
    private static int serialNumber = 0;
    private final int SHELL_NUMBER;
    private int shellDamagePower;
    private int shellRange;
    private int fireRange;
    private int X, Y;//positions on BattleFiled(matrix)
    private final BufferedImage explosionAnimImg;
    private boolean bigExplosion;

    public Shell(String type, String target, BufferedImage explosionAnimImg) {
        this.target = target;
        this.TYPE = type;
        SHELL_NUMBER = serialNumber++;

        this.explosionAnimImg = explosionAnimImg;
        if (TYPE.equals("S1")) {
            createS1();
        }
        if (TYPE.equals("S2")) {
            createS2();
        }
        if (TYPE.equals("S3")) {
            createS3();
        }

    }

    public void setTarge(String target) {
        this.target = target;
    }

    private void createS1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createS2() {
        shellDamagePower = 2;
        shellRange = 500;
    }

    private void createS3() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void fire(int[] barrel, Tank.Direction direction) {
        X = barrel[0];
        Y = barrel[1];

        if (direction == Tank.Direction.NORTH) {
            shootNorth();
        }
        if (direction == Tank.Direction.SOUTH) {
            shootSouth();
        }
        if (direction == Tank.Direction.EAST) {
            shootEast();
        }
        if (direction == Tank.Direction.WEST) {
            shootWest();
        }

    }

    private void shootNorth() {
        bigExplosion = false;
        fireRange = Y - shellRange;
        while (Y >= 0) {//till it reaches north edge of battlefield
            if (Y < fireRange) {
                break;
            }
            Y--;
            if (Y > 0) {
                Particle p1 = BattleField.matrix[X][Y - 1];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {
                        Tank tank = BattleField.tanksOnField.get(p1.getStamp());
                        if (tank.getArmour() <= shellDamagePower) {
                            bigExplosion = true;
                            tank.destroyTank();
                           
                        } else {
                            tank.setArmour(tank.getArmour() - shellDamagePower);
                        }
                         break;
                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y, bigExplosion);
    }

    private void shootSouth() {

        bigExplosion = false;
        fireRange = Y + shellRange;
        while (Y <= BattleField.matrix.length - 1) {//till it reaches north edge of battlefield
            if (Y > fireRange) {
                break;
            }
            Y++;
            if (Y < BattleField.matrix.length - 1) {
                Particle p1 = BattleField.matrix[X][Y + 1];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {

                        Tank tank = BattleField.tanksOnField.get(p1.getStamp());
                        if (tank.getArmour() <= shellDamagePower) {
                            bigExplosion = true;
                            tank.destroyTank();
                           
                        } else {
                            tank.setArmour(tank.getArmour() - shellDamagePower);
                        }
                         break;
                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y, bigExplosion);
    }

    private void shootEast() {
        bigExplosion = false;
        fireRange = X + shellRange;
        while (X <= BattleField.matrix[0].length - 1) {//till it reaches north edge of battlefield
            if (X > fireRange) {
                break;
            }
            X++;
            if (X < BattleField.matrix.length - 1) {
                Particle p1 = BattleField.matrix[X + 1][Y];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {
                        Tank tank = BattleField.tanksOnField.get(p1.getStamp());
                        if (tank.getArmour() <= shellDamagePower) {
                            bigExplosion = true;
                            tank.destroyTank();

                        } else {
                            tank.setArmour(tank.getArmour() - shellDamagePower);
                        }
                        break;
                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y, bigExplosion);
    }

    private void shootWest() {
        bigExplosion = false;
        fireRange = X - shellRange;
        while (X >= 0) {//till it reaches north edge of battlefield
            if (X < fireRange) {
                break;
            }
            X--;
            if (X > 0) {
                Particle p1 = BattleField.matrix[X - 1][Y];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {
                        Tank tank = BattleField.tanksOnField.get(p1.getStamp());
                        if (tank.getArmour() <= shellDamagePower) {
                            bigExplosion = true;
                            tank.destroyTank();

                        } else {
                            tank.setArmour(tank.getArmour() - shellDamagePower);
                        }
                        break;
                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y, bigExplosion);
    }

    private void explode(int X, int Y, boolean bigExplosion) {
        int explosionPower = 20;
        if (bigExplosion) {
            explosionPower = 100;
        }

        Animation expAnim = new Animation(explosionAnimImg, 134, 134, explosionPower, 45, false, X - 65, Y - 70, 1, explosionPower);
        BattleField.explosionsList.put(SHELL_NUMBER, expAnim);
    }
}
