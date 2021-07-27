package main;
import gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class main {

    public static void main(String[] args) {
    	
          
        JFrame j = new JFrame();
        j.setTitle("Gjetja e rruges me te shkurter duke perdorur Algoritmin Dijkstra");

        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(900, 600));
        j.add(new MainWindow());
        j.setVisible(true);
        

    }

}

