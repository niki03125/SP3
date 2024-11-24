package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    JButton loginButton;
    JButton createButton;

    public GUI() {
        //Start site buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(200, 300, 150, 50);
//        loginButton.set
        loginButton.addActionListener(this);



        createButton = new JButton("Create user");
        createButton.setBounds(400, 300, 150, 50);
        createButton.addActionListener(this);


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
        if (e.getSource()==loginButton){
            System.out.println("Login");
        } else if (e.getSource()==createButton) {
            System.out.println("Create new user");
        }
    }
}
