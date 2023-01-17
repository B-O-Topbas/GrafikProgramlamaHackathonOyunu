package main;

import java.awt.geom.Rectangle2D;

public class Araba extends Rectangle2D.Float {
//    int x, y;
//    float w, h;
    public Araba(int x, int y, float w, float h) {
//        this.x=x;
//        this.y=y;
//        this.w=w;
//        this.h=h;
        setFrame(x,y,w,h);
    }

    public void addX(int dx) {
        this.x += dx;
    }
    public void addY(int dy) {
        this.y += dy;
    }
    public void substractX(int dx){
        this.x-=dx;
    }
    public void substractY(int dy){
        this.y-=dy;
    }
}