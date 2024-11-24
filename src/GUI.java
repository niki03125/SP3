package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    public GUI() {
        //Start site buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 300, 150, 50);

        JButton createButton = new JButton("Create user");
        createButton.setBounds(400, 300, 150, 50);


        this.setTitle("WBBT - Streaming services");
        this.setSize(600, 600);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Graphics
        this.setIconImage(new ImageIcon("images/icon.png").getImage());
        this.getContentPane().setBackground(new Color(0x005640));

        this.add(loginButton);
        this.add(createButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
