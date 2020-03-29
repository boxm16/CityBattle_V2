/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 *
 * @author Michail Sitmalidis
 */
public class MyTank extends Tank {

    private boolean s_key, n_key, w_key, e_key;

    public MyTank(String TYPE,String name, int X, int Y) {
        super("T2", "MyTank", X, Y);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
    }

    public void tik() {
        speedTik = speedTik + 1;
        if (speedTik == speed) {
            if (clutch) {
                move();
            }
            speedTik = 0;
        }
    }

    @Override
    protected void fire() {
        Shell myShell = new Shell("S2", "EnemyTank", explosionAnimImg);
        myShell.fire(barrel, direction);
    }

    //----key listener--
    private class MyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (status.equals("active")) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {

                    if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                        direction = Direction.NORTH;
                        n_key = true;
                        startMoving();
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
                        direction = Direction.SOUTH;
                        s_key = true;
                        startMoving();
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                        direction = Direction.WEST;
                        w_key = true;
                        startMoving();
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                        direction = Direction.EAST;
                        e_key = true;
                        startMoving();
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        fire();
                    }

                }
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                        n_key = false;

                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {

                        s_key = false;
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {

                        w_key = false;
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {

                        e_key = false;
                    }
                    if (!s_key & !n_key & !e_key & !w_key) {
                        stopMoving();
                    }

                }

            }
            return false;
        }

    }

}
