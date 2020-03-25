/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

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
    private int SHELL_DAMAGE_PAOWER;
    private int SHELL_RANGE;
    protected Timer engine;
    protected Direction direction;

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

    }

    private void createS1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createS2() {
        SHELL_DAMAGE_PAOWER = 3;
        SHELL_RANGE = 600;
    }

    private void createS3() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
