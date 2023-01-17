package main;

import java.awt.*;

public class App {
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Cerceve cerceve=new Cerceve();
            cerceve.setVisible(true);
        });
    }
}