package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame{
    public GUI() {
        this.setTitle("WBBT - Streaming services");
        this.setSize(640, 640);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Graphics
        this.setIconImage(new ImageIcon("images/icon.png").getImage());
        this.getContentPane().setBackground(new Color(0x005640));

        //Start site buttons
        JButton loginButton = new JButton("Login");
        JButton createButton = new JButton("Create user");


    }
}
