package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Baseball!");
        ImageIcon icon = new ImageIcon("res/player/ball.png");
		window.setIconImage(icon.getImage());
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.startThread();
        
    }
}