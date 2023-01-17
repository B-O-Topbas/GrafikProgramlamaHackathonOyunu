package main;

public class YolCizgileri {
    int x,y,w,h;
    boolean visible;
    public YolCizgileri(int x, int y, int w, int h,boolean visible) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.visible=visible;
    }
    public void addY(int y){
        this.y+=y;
    }
}
