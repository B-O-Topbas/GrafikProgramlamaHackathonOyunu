package main;

import java.awt.geom.RoundRectangle2D;

public class KarsiAraba extends RoundRectangle2D.Float {
//    int x,y;
//    float w,h;
    boolean visible;
    int arabaResmi;
    public KarsiAraba(int x,int y,float w,float h,boolean visible,int arabaResmi){
        this.visible=visible;
        this.arabaResmi=arabaResmi;
//        this.x=x;
//        this.y=y;
//        this.w=w;
//        this.h=h;
        setFrame(x,y,w,h);
    }
    public void addY(int dy){
        this.y+=dy;
    }
    public void setVisible(boolean visible){
        this.visible=visible;
    }
}
