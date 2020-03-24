/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citybattle_v2;

/**
 *
 * @author Michail Sitmalidis
 */
public class Particle {

    private int x;//positions in shape(tank, wall e.c.t)
    private int y;
    private String name;
    private int stamp;

    public Particle(String name, int stamp) {
        this.name = name;
        this.stamp = stamp;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int getStamp() {
        return stamp;
    }

 

   
    
    

}
