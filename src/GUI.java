package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    StreamingPlatform streamingPlatform;
    JButton loginButton;
    JButton createButton;

    public GUI(StreamingPlatform streamingPlatform) {
        this.streamingPlatform = streamingPlatform;
        startGUI();
    }
    public void startGUI(){
        //Start site buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(100, 250, 150, 50);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);



        createButton = new JButton("Create user");
        createButton.setBounds(350, 250, 150, 50);
        createButton.setFocusable(false);
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

    public void userLogin(String username, String password){
        streamingPlatform.userLogin();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==loginButton){
            streamingPlatform.userLogin();
        } else if (e.getSource()==createButton) {
            streamingPlatform.userRegister();
        }
    }
}
