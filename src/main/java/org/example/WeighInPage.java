package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WeighInPage extends Layout {

    private CurrentUser currentUser = CurrentUser.getInstance();
    private JButton userSubmit = new JButton("Enter");
    private JButton userSubmit2 = new JButton("Enter");
    private JTextArea userInput = new JTextArea();
    private JTextArea userInput2 = new JTextArea();
    SQLiteConnection connection = SQLiteConnection.getInstance();


    public WeighInPage(CardLayout cardLayout, JPanel cardPanel, String userID, SQLiteConnection connection) {

        super(cardLayout, cardPanel);  // Pass the connection to the parent class
        this.connection = connection;

        JPanel abstractContainer = new JPanel(new BorderLayout());
        this.add(abstractContainer, BorderLayout.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        JPanel bot = new JPanel(new BorderLayout());
        abstractContainer.add(top, BorderLayout.NORTH);
        abstractContainer.add(bot, BorderLayout.CENTER);

        JLabel currentWeight = new JLabel("Enter Current Weight: ", SwingConstants.CENTER);
        top.add(currentWeight, BorderLayout.NORTH);
        top.add(userInput, BorderLayout.CENTER);
        top.add(userSubmit, BorderLayout.SOUTH);

        JLabel targetWeight = new JLabel("Enter New Target Weight: ", SwingConstants.CENTER);
        bot.add(targetWeight, BorderLayout.NORTH);
        bot.add(userInput2, BorderLayout.CENTER);
        bot.add(userSubmit2, BorderLayout.SOUTH);

        // Add spacing
        JLabel lspace = new JLabel("        ");
        JLabel rspace = new JLabel("        ");
        this.add(lspace, BorderLayout.WEST);
        this.add(rspace, BorderLayout.EAST);

        userSubmit.addActionListener(this);
        userSubmit2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        super.actionPerformed(e);

        if (e.getSource() == userSubmit) {
            try {
                double oldCurrent = connection.getCurrentWeight(currentUser.getLoggedInUser());
                double currentWeighIn = Double.parseDouble(userInput.getText());
                //
                HistoryPage.prevCurrent = oldCurrent;
                HistoryPage.current = currentWeighIn;
                //
                System.out.println("Current Weigh-In: " + currentWeighIn);
                // Update database with new current weight
                if (connection.setCurrentWeight(currentUser.getLoggedInUser(), currentWeighIn)) {
                    System.out.println("Current weight updated successfully.");
                } else {
                    System.out.println("Failed to update current weight.");
                }

                // reset the input box
                userInput.setText("");

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        if (e.getSource() == userSubmit2) {
            try {
                double oldTarget = connection.getTargetWeight((currentUser.getLoggedInUser()));
                double newTargetWeight = Double.parseDouble(userInput2.getText());
                //
                HistoryPage.prevTarget = oldTarget;
                HistoryPage.target = newTargetWeight;
                //
                System.out.println("New Target Weight: " + newTargetWeight);
                // Update database with new target weight
                if (connection.setTargetWeight(currentUser.getLoggedInUser(), newTargetWeight)) {
                    System.out.println("Target weight updated successfully.");
                } else {
                    System.out.println("Failed to update target weight.");
                }
                //
                // have to update the starting weight
                connection.setStartWeight(currentUser.getLoggedInUser(), HistoryPage.current);
                //
                if(connection.setStartWeight(currentUser.getLoggedInUser(), HistoryPage.current)) {
                    System.out.println("Start weight updated successfully.");
                } else {
                    System.out.println("Failed to update start weight.");
                }

                // reset the input box
                userInput2.setText("");

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

}
