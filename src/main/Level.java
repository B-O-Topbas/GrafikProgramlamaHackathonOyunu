package main;

import javax.swing.*;
import java.awt.*;

public class Level extends JPanel {
    public Level(){
        setBackground(Color.BLACK);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ciz(g);
    }

    public void ciz(Graphics g){
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(Color.WHITE);





    }
}