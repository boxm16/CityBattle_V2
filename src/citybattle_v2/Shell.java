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
    private boolean baseExplosion;
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
        shellDamagePower = 1;
        shellRange = 300;
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
                    if (p1.getName().equals("Brick")||p1.getName().equals("Steel")) {
                        hitBlock(p1);
                        break;
                    }
                    if (p1.getName().equals("Base")) {
                        hitBase();
                        break;
                    }
//if i put break here, enemy tank will hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y);
    }

    private void shootSouth() {

        bigExplosion = false;
        fireRange = Y + shellRange;
        while (Y <= BattleField.matrix[0].length - 1) {//till it reaches north edge of battlefield
            if (Y > fireRange) {
                break;
            }

            //  Particle p = new Particle("Shell", 1);
            //  BattleField.matrix[X][Y] = p;
            Y++;
            if (Y < BattleField.matrix[0].length - 2) {
                Particle p1 = BattleField.matrix[X][Y];
                if (p1 != null) {

                    if (p1.getName().equals(target)) {
                        hitTank(p1);
                        break;
                    }
                    if (p1.getName().equals("Brick")||p1.getName().equals("Steel")) {
                        hitBlock(p1);
                        break;
                    }
                    if (p1.getName().equals("Base")) {
                        hitBase();
                        break;
                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y);
    }

    private void shootEast() {
        bigExplosion = false;
        fireRange = X + shellRange;
        while (X <= BattleField.matrix.length - 1) {//till it reaches north edge of battlefield
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
                    if (p1.getName().equals("Brick")||p1.getName().equals("Steel")) {
                        hitBlock(p1);
                        break;
                    }
                    if (p1.getName().equals("Base")) {
                        hitBase();
                        break;
                    }
//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y);
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
                    if (p1.getName().equals("Brick")||p1.getName().equals("Steel")) {
                        hitBlock(p1);
                        break;
                    }
                    if (p1.getName().equals("Base")) {
                        hitBase();
                        break;
                    }

//if i put break here, enemy tank wil hit another enemy tank(but not explode)
                }
            }

        }
        explode(X, Y);
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
//its a big method

    private void hitBlock(Particle particle) {
        Block block = BattleField.cityPlan.get(particle.getStamp());
        block.destroyBlock();

        //if shellDamage==3
        if (shellDamagePower == 3) {
            if (direction == Direction.SOUTH || direction == Direction.NORTH) {
                if (X < 40 || X > BattleField.matrix.length - 40) {//δηλαδη, this means that tank is near the edge, so it cant destroy 3 brick, onlty 2
                    shellDamagePower = 2;//so shell will explode with damage power 2, see lower 
                } else {
                    Particle particleBlockWest = BattleField.matrix[block.getX() - 1][block.getY()];
                    Particle particleBlockEast = BattleField.matrix[block.getX() + block.getWIDTH() + 1][block.getY()];

                    if (particleBlockWest != null && particleBlockWest.getName().equals("Brick")) {
                        Block nextBrick = BattleField.cityPlan.get(particleBlockWest.getStamp());
                        nextBrick.destroyBlock();
                    }
                    if (particleBlockEast != null && particleBlockEast.getName().equals("Brick")) {
                        Block nextBrick = BattleField.cityPlan.get(particleBlockEast.getStamp());
                        nextBrick.destroyBlock();
                    }
                }
            }
            if (direction == Direction.EAST || direction == Direction.WEST) {
                if (Y < 40 && Y > BattleField.matrix[0].length - 40) {//δηλαδη, this means that tank is near the edge, so it cant destroy 3 brick, onlty 2
                    shellDamagePower = 2;//so shell will explode with damage power 2, see lower 
                } else {
                    Particle particleBlockNorth = BattleField.matrix[block.getX()][block.getY() - 1];
                    Particle particleBlockSouth = BattleField.matrix[block.getX()][block.getY() + block.getLENGTH() + 1];
                    if (particleBlockNorth != null && particleBlockNorth.getName().equals("Brick")) {
                        Block nextBlock = BattleField.cityPlan.get(particleBlockNorth.getStamp());
                        nextBlock.destroyBlock();
                    }
                    if (particleBlockSouth != null && particleBlockSouth.getName().equals("Brick")) {
                        Block nextBlock = BattleField.cityPlan.get(particleBlockSouth.getStamp());
                        nextBlock.destroyBlock();
                    }
                }
            }
        }
        //if shellDamage==2
        if (shellDamagePower == 2) {
            if (direction == Direction.SOUTH || direction == Direction.NORTH) {
                int hitSpot = particle.getX();
                Particle nextBlockParticle = null;
                if (hitSpot < block.getWIDTH() / 2) {
                    //trying to find next brick on one side
                    nextBlockParticle = BattleField.matrix[block.getX() - 1][block.getY()];
                } else {
                    //trying to find next brick on another side
                    nextBlockParticle = BattleField.matrix[block.getX() + block.getLENGTH() + 1][block.getY()];
                }
                if (nextBlockParticle != null && nextBlockParticle.getName().equals("Brick")) {
                    Block nextBrick = BattleField.cityPlan.get(nextBlockParticle.getStamp());
                    nextBrick.destroyBlock();
                }
            }
            if (direction == Direction.EAST || direction == Direction.WEST) {
                int hitSpot = particle.getY();
                Particle nextBlockParticle = null;
                if (hitSpot < block.getLENGTH() / 2) {
                    //trying to find next brick on one side
                    nextBlockParticle = BattleField.matrix[block.getX()][block.getY() - 1];
                } else {
                    //trying to find next brick on another side
                    nextBlockParticle = BattleField.matrix[block.getX()][block.getY() + block.getWIDTH() + 1];
                }
                if (nextBlockParticle != null && nextBlockParticle.getName().equals("Brick")) {
                    Block nextBlock = BattleField.cityPlan.get(nextBlockParticle.getStamp());
                    nextBlock.destroyBlock();
                }
            }
        }

    }

    private void hitBase() {
        BattleField.base.destroy();
        baseExplosion = true;
    }

    private void explode(int X, int Y) {
        int explosionPower = 20;
        if (bigExplosion) {
            explosionPower = 100;
        }
        if (baseExplosion) {

            explosionPower = 200;
        }

        Animation expAnim = new Animation(explosionAnimImg, 134, 134, explosionPower, 45, false, X - 65, Y - 70, 1, explosionPower);
        BattleField.explosionsList.put(SHELL_NUMBER, expAnim);
    }
}
