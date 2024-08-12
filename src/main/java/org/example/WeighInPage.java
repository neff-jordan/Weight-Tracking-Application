/**
 * Represents the weigh-in page in the application where users can update their current weight
 * and target weight. This class extends the `Layout` class and uses Swing components to create
 * a graphical user interface for interacting with the user's weight data.
 */

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

    /**
     * Constructs the weigh-in page with specified layout and user information.
     *
     * @param cardLayout  The CardLayout instance used to switch between different pages.
     * @param cardPanel   The JPanel that holds the different pages.
     * @param userID      The ID of the logged-in user.
     * @param connection  The SQLiteConnection instance for database operations.
     */
    public WeighInPage(CardLayout cardLayout, JPanel cardPanel, String userID, SQLiteConnection connection) {
        super(cardLayout, cardPanel);  // Pass the connection to the parent class
        this.connection = connection;

        // Create a spacer panel with BorderLayout
        JPanel spacer = new JPanel(new BorderLayout());

        // Create the main container with BoxLayout for vertical alignment
        JPanel abstractContainer = new JPanel();
        abstractContainer.setLayout(new BoxLayout(abstractContainer, BoxLayout.Y_AXIS));
        spacer.add(abstractContainer, BorderLayout.CENTER);

        // Add vertical space
        JLabel spaces = new JLabel("\n\n\n\n\n\n\n");
        spacer.add(spaces, BorderLayout.NORTH);
        this.add(spacer, BorderLayout.CENTER);

        // Create panels for each section and add them to the abstractContainer
        JPanel currentWeightPanel = new JPanel();
        currentWeightPanel.setLayout(new BoxLayout(currentWeightPanel, BoxLayout.Y_AXIS));
        JPanel targetWeightPanel = new JPanel();
        targetWeightPanel.setLayout(new BoxLayout(targetWeightPanel, BoxLayout.Y_AXIS));

        abstractContainer.add(currentWeightPanel);
        abstractContainer.add(Box.createVerticalStrut(20));  // Add spacing between panels
        abstractContainer.add(targetWeightPanel);



        JLabel currentWeight = new JLabel("Enter Current Weight: ", SwingConstants.CENTER);
        currentWeight.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label
        currentWeightPanel.add(currentWeight);
        currentWeightPanel.add(Box.createVerticalStrut(10));  // Add spacing between components

        userInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));  // Increase height
        userInput.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the text area
        currentWeightPanel.add(userInput);
        currentWeightPanel.add(Box.createVerticalStrut(10));  // Add spacing between components

        userSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        currentWeightPanel.add(userSubmit);

        JLabel targetWeight = new JLabel("Enter New Target Weight: ", SwingConstants.CENTER);
        targetWeight.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label
        targetWeightPanel.add(targetWeight);
        targetWeightPanel.add(Box.createVerticalStrut(10));  // Add spacing between components

        userInput2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));  // Increase height
        userInput2.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the text area
        targetWeightPanel.add(userInput2);
        targetWeightPanel.add(Box.createVerticalStrut(10));  // Add spacing between components

        userSubmit2.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        targetWeightPanel.add(userSubmit2);

        // Add spacing
        JLabel lspace = new JLabel("        ");
        JLabel rspace = new JLabel("        ");
        this.add(lspace, BorderLayout.WEST);
        this.add(rspace, BorderLayout.EAST);

        userSubmit.addActionListener(this);
        userSubmit2.addActionListener(this);
    }

    /**
     * Handles action events for the buttons on the weigh-in page.
     * Updates the user's current weight and target weight in the database based on button clicks.
     *
     * @param e The ActionEvent triggered by button clicks.
     */
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
