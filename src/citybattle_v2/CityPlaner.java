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
public class CityPlaner extends MyTank {

    public CityPlaner(String type, int X, int Y) {
        super(type, X, Y);
    }

    protected void setTankSize() {
        TANK_LENGTH = 20;
        TANK_WIDTH = 20;
    }

    protected void placeBlock() {
        if (MainFrame.putBrick.isSelected()) {
            Block block = new Block("Brick", getX(), getY());
            ArrayList<Particle> hull = block.getHull();
            for (Particle particle : hull) {
                BattleField.matrix[getX() + particle.getX()][getY() + particle.getY()] = particle;
            }
            BattleField.cityPlan.put(block.getBLOCK_NUMBER(), block);
        }
        if (MainFrame.putSteel.isSelected()) {
            Block block = new Block("Steel", getX(), getY());
            BattleField.cityPlan.put(block.getBLOCK_NUMBER(), block);
            ArrayList<Particle> hull = block.getHull();
            for (Particle particle : hull) {
                BattleField.matrix[getX() + particle.getX()][getY() + particle.getY()] = particle;
            }
        }
        if (MainFrame.putWater.isSelected()) {
            Block block = new Block("Water", getX(), getY());
            BattleField.cityPlan.put(block.getBLOCK_NUMBER(), block);
            ArrayList<Particle> hull = block.getHull();
            for (Particle particle : hull) {
                BattleField.matrix[getX() + particle.getX()][getY() + particle.getY()] = particle;
            }
        }

    }

    public void tik() {
        if (clutch) {
            jump();
        }
    }

    private void jump() {
        if (direction == Direction.SOUTH) {
            turnSouth();
            jumpSouth();
            stopMoving();
        }
        if (direction == Direction.NORTH) {
            turnNorth();
            jumpNorth();
            stopMoving();
        }
        if (direction == Direction.EAST) {
            turnEast();
            jumpEast();
            stopMoving();
        }
        if (direction == Direction.WEST) {
            turnWest();
            jumpWest();
            stopMoving();
        }

    }

    private void jumpSouth() {
        if (canJumpSouth()) {
            placeBlock();
            Y += TANK_LENGTH;
            for (int a = getHull().size() - 1; a >= 0; a--) {
                Particle particle = getHull().get(a);
                if (MainFrame.walk.isSelected()) {
                    BattleField.matrix[X + particle.getX()][Y - TANK_LENGTH + particle.getY()] = null;
                }
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }

    private void jumpNorth() {
        if (canJumpNorth()) {
            placeBlock();
            Y -= TANK_LENGTH;
            for (int a = 0; a < getHull().size(); a++) {
                Particle particle = getHull().get(a);
                if (MainFrame.walk.isSelected()) {
                    BattleField.matrix[X + particle.getX()][Y + TANK_LENGTH + particle.getY()] = null;
                }
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }

    private void jumpEast() {

        if (canJumpEast()) {
            placeBlock();
            X += TANK_LENGTH;
            for (int a = getHull().size() - 1; a >= 0; a--) {
                Particle particle = getHull().get(a);
                if (MainFrame.walk.isSelected()) {
                    BattleField.matrix[X - TANK_LENGTH + particle.getX()][Y + particle.getY()] = null;
                }
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }

    private void jumpWest() {
        if (canJumpWest()) {
            placeBlock();
            X -= TANK_LENGTH;
            for (int a = 0; a < getHull().size(); a++) {
                Particle particle = getHull().get(a);
                if (MainFrame.walk.isSelected()) {
                    BattleField.matrix[X + TANK_LENGTH + particle.getX()][Y + particle.getY()] = null;
                }
                BattleField.matrix[X + particle.getX()][Y + particle.getY()] = particle;
            }
        }
    }
//canGO

    private boolean canJumpSouth() {
        if (Y < BattleField.matrix[0].length - TANK_LENGTH - 1) {
            for (int a = X; a < X + TANK_WIDTH; a++) {
                Particle particle = BattleField.matrix[a][Y + TANK_LENGTH];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean canJumpNorth() {
        if (Y > TANK_LENGTH*3 - 1) {//i leave place for tanks creation place
            for (int a = X; a < X + TANK_LENGTH; a++) {
                Particle particle = BattleField.matrix[a][Y - TANK_LENGTH];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean canJumpEast() {
        if (X < BattleField.matrix.length - TANK_WIDTH - 1) {
            for (int a = Y; a < Y + TANK_LENGTH - 1; a++) {
                Particle particle = BattleField.matrix[X + TANK_WIDTH][a];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean canJumpWest() {
        if (X > TANK_WIDTH - 1) {
            for (int a = Y; a < Y + TANK_LENGTH; a++) {
                Particle particle = BattleField.matrix[X - TANK_WIDTH][a];
                if (particle != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    protected void fire(){
        
    }

}
