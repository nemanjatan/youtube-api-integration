package com.youtube.api.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class OAuthTestUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("YouTube API OAuth Test");
        JButton button = new JButton("Authenticate with Google");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://localhost:8080/authorize"));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.setVisible(true);
    }

}
