package com.youtube.api.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * A simple UI class for testing OAuth authentication with the YouTube API.
 * This class creates a basic GUI with a button to initiate OAuth authentication.
 */
public class OAuthTestUI {

    /**
     * The main method that sets up the UI elements and event handling.
     * It creates a JFrame containing a single button. When the button is clicked,
     * it attempts to open a browser window to a predefined authorization URL.
     *
     * @param args The command line arguments. (Not used in this application)
     */
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
