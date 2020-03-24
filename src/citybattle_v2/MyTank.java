/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Michail Sitmalidis
 */
public class MyTank extends Tank {

    private boolean s_key, n_key, w_key, e_key;

    public MyTank(String TYPE, int X, int Y) {
        super("T2", X, Y);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());

    }

    @Override
    protected void startEngine() {
        engine = new Timer(10, new TankEngine());
        engine.start();
    }

    @Override
    protected void stopEngine() {
        engine.stop();
    }

    //----key listener--
    private class MyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (engine != null) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {

                    if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                        direction = Direction.NORTH;
                        n_key = true;
                        engine.start();
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
                        direction = Direction.SOUTH;
                        s_key = true;
                        engine.start();
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                        direction = Direction.WEST;
                        w_key = true;
                        engine.start();
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                        direction = Direction.EAST;
                        e_key = true;
                        engine.start();
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        //fire();
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
                        engine.stop();
                    }

                }

            }
            return false;
        }

    }

}