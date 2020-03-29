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
    private Direction direction;

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
        shellDamagePower = 3;
        shellRange = 800;
    }

    void fire(int[] barrel, Direction direction) {
        X = barrel[0];
        Y = barrel[1];
        this.direction = direction;
        if (direction == Direction.NORTH) {
            shootNorth();
        }
        if (direction == Direction.SOUTH) {
            shootSouth();
        }
        if (direction == Direction.EAST) {
            shootEast();
        }
        if (direction == Direction.WEST) {
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
            // Particle p = new Particle("Shell", 1);
            // BattleField.matrix[X][Y] = p;
            Y--;
            if (Y > 0) {
                Particle p1 = BattleField.matrix[X][Y];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {
                        hitTank(p1);
                        break;
                    }
                    if (p1.getName().equals("Brick")) {
                        hitBrick(p1);

                        break;

                    }
//if i put break here, enemy tank will hit another enemy tank(but not explode)
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

            //  Particle p = new Particle("Shell", 1);
            //  BattleField.matrix[X][Y] = p;
            Y++;
            if (Y < BattleField.matrix.length - 2) {
                Particle p1 = BattleField.matrix[X][Y];
                if (p1 != null) {

                    if (p1.getName().equals(target)) {
                        hitTank(p1);
                        break;
                    }
                    if (p1.getName().equals("Brick")) {
                        hitBrick(p1);

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

            //   Particle p = new Particle("Shell", 1);
            //  BattleField.matrix[X][Y] = p;
            X++;
            if (X < BattleField.matrix.length - 2) {
                Particle p1 = BattleField.matrix[X][Y];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {
                        hitTank(p1);
                        break;
                    }
                    if (p1.getName().equals("Brick")) {
                        hitBrick(p1);

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

            //  Particle p = new Particle("Shell", 1);
            //  BattleField.matrix[X][Y] = p;
            X--;
            if (X > 0) {
                Particle p1 = BattleField.matrix[X][Y];
                if (p1 != null) {
                    if (p1.getName().equals(target)) {
                        hitTank(p1);
                        break;
                    }
                    if (p1.getName().equals("Brick")) {
                        hitBrick(p1);
                        break;

                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y, bigExplosion);
    }

    private void hitTank(Particle particle) {

        Tank tank = BattleField.tanksOnField.get(particle.getStamp());
        if (tank.getArmour() <= shellDamagePower) {
            bigExplosion = true;
            tank.destroyTank();

        } else {
            tank.setArmour(tank.getArmour() - shellDamagePower);
        }
    }

    private void hitBrick(Particle particle) {
        Brick brick = BattleField.cityWall.get(particle.getStamp());
        brick.destroyBrick();
        //if shellDamage==2
        if (shellDamagePower == 2) {
            if (direction == Direction.SOUTH || direction == Direction.NORTH) {
                int hitSpot = particle.getX();
                Particle nextBrickParticle = null;
                if (hitSpot < brick.getWIDTH() / 2) {
                    //trying to find next brick on one side
                    nextBrickParticle = BattleField.matrix[brick.getX() - 1][brick.getY()];
                } else {
                    //trying to find next brick on another side
                    nextBrickParticle = BattleField.matrix[brick.getX() + brick.getLENGTH() + 1][brick.getY()];
                }
                if (nextBrickParticle != null && nextBrickParticle.getName().equals("Brick")) {
                    Brick nextBrick = BattleField.cityWall.get(nextBrickParticle.getStamp());
                    nextBrick.destroyBrick();
                }
            }
            if (direction == Direction.EAST || direction == Direction.WEST) {
                int hitSpot = particle.getY();
                Particle nextBrickParticle = null;
                if (hitSpot < brick.getLENGTH() / 2) {
                    //trying to find next brick on one side
                    nextBrickParticle = BattleField.matrix[brick.getX()][brick.getY() - 1];
                } else {
                    //trying to find next brick on another side
                    nextBrickParticle = BattleField.matrix[brick.getX()][brick.getY() + brick.getWIDTH() + 1];
                }
                if (nextBrickParticle != null && nextBrickParticle.getName().equals("Brick")) {
                    Brick nextBrick = BattleField.cityWall.get(nextBrickParticle.getStamp());
                    nextBrick.destroyBrick();
                }
            }
        }
        //if shellDamage==3
        if (shellDamagePower == 3) {
            if (direction == Direction.SOUTH || direction == Direction.NORTH) {
                Particle particleBrickWest = BattleField.matrix[brick.getX() - 1][brick.getY()];
                Particle particleBrickEast = BattleField.matrix[brick.getX() + brick.getWIDTH() + 1][brick.getY()];

                if (particleBrickWest != null && particleBrickWest.getName().equals("Brick")) {
                    Brick nextBrick = BattleField.cityWall.get(particleBrickWest.getStamp());
                    nextBrick.destroyBrick();
                }
                if (particleBrickEast != null && particleBrickEast.getName().equals("Brick")) {
                    Brick nextBrick = BattleField.cityWall.get(particleBrickEast.getStamp());
                    nextBrick.destroyBrick();
                }
            }
            if (direction == Direction.EAST || direction == Direction.WEST) {
                Particle particleBrickNorth = BattleField.matrix[brick.getX()][brick.getY() - 1];
                Particle particleBrickSouth = BattleField.matrix[brick.getX()][brick.getY() + brick.getLENGTH() + 1];
                if (particleBrickNorth != null && particleBrickNorth.getName().equals("Brick")) {
                    Brick nextBrick = BattleField.cityWall.get(particleBrickNorth.getStamp());
                    nextBrick.destroyBrick();
                }
                if (particleBrickSouth != null && particleBrickSouth.getName().equals("Brick")) {
                    Brick nextBrick = BattleField.cityWall.get(particleBrickSouth.getStamp());
                    nextBrick.destroyBrick();
                }
            }
        }

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
