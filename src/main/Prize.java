package main;

import java.awt.geom.RoundRectangle2D;

public class Prize extends RoundRectangle2D.Float {
    boolean visible;
    public Prize(int x,int y,int w,int h,boolean visible){
        this.visible=visible;
        setFrame(x,y,w,h);
    }
    public void addY(int y){
        this.y+=y;
    }
}
