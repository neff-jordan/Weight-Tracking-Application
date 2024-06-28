package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {


        // total project lines of code = ~1400
        // medium-sized project


        SwingUtilities.invokeLater(() -> {

            FlatLightLaf.setup();

            JFrame frame = new JFrame("Weight Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(520, 640);
            frame.setMinimumSize(new Dimension(520, 640));
            frame.setMaximumSize(new Dimension(520, 640));

            CardLayout cardLayout = new CardLayout();
            JPanel cardPanel = new JPanel(cardLayout);

            SQLiteConnection connection = SQLiteConnection.getInstance();

            // Add LoginPage to the card panel
            LoginPage loginPage = new LoginPage(cardLayout, cardPanel, connection);
            CreateAccount createAccountPage = new CreateAccount(cardLayout, cardPanel, connection);

            cardPanel.add(loginPage, "Login");
            cardPanel.add(createAccountPage, "Create Account");

            frame.add(cardPanel);
            frame.setVisible(true);

            // Show the login page by default
            cardLayout.show(cardPanel, "Login");
        });
    }
}