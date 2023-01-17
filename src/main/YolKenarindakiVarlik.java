package main;

public class YolKenarindakiVarlik {
    int x,y,w,h;
    int resim;
    boolean visible;
    public YolKenarindakiVarlik(int x, int y, int w, int h, boolean visible,int resim) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.visible = visible;
        this.resim=resim;
    }
    public void addY(int y){
        this.y+=y;
    }
}