/**
 * The Layout class serves as a base class for various screens in the application.
 * It provides a consistent layout with navigation buttons and manages switching
 * between different screens (cards) in the application.
 */

package org.example;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Layout extends JPanel implements ActionListener {
    protected JButton homeButton = new JButton("Home");
    protected JButton weighInButton = new JButton("Weigh-In");
    protected JButton graphButton = new JButton("Graph");
    protected JButton historyButton = new JButton("History");
    protected CardLayout cardLayout;
    protected JPanel cardPanel;
    SQLiteConnection connection = SQLiteConnection.getInstance();

    /**
     * Constructor for the Layout class.
     * Initializes the navigation buttons and sets up the layout.
     *
     * @param cardLayout The CardLayout manager used for switching between different screens.
     * @param cardPanel The JPanel that holds the different screens.
     */
    public Layout(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // Set up the buttons
        homeButton.addActionListener(this);
        weighInButton.addActionListener(this);
        graphButton.addActionListener(this);
        historyButton.addActionListener(this);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4));
        buttonsPanel.add(homeButton);
        buttonsPanel.add(weighInButton);
        buttonsPanel.add(graphButton);
        buttonsPanel.add(historyButton);

        setLayout(new BorderLayout());
        add(buttonsPanel, BorderLayout.NORTH);
    }

    /**
     * Switches the displayed card to the one specified by the cardName.
     * This method refreshes the display after switching.
     *
     * @param cardName The name of the card to switch to.
     * @throws RefreshFailedException If there is an error while refreshing the screen.
     */
    protected void switchToCard(String cardName) throws RefreshFailedException {
        cardLayout.show(cardPanel, cardName);
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    /**
     * Handles button clicks and switches to the corresponding screen (card).
     *
     * @param e The ActionEvent triggered by button clicks.
     */
    @Override
    public void actionPerformed(ActionEvent e)  {
        try {
            // Check which button was clicked and switch to the corresponding card
            if (e.getSource() == homeButton) {
                switchToCard("Home");
            } else if (e.getSource() == weighInButton) {
                switchToCard("Weigh-In");
            } else if (e.getSource() == graphButton) {
                switchToCard("Graph");
            } else if (e.getSource() == historyButton) {
                switchToCard("History");
            }
        } catch (RefreshFailedException ex) {
            // Handle the exception by wrapping it in a RuntimeException
            throw new RuntimeException(ex);
        }
    }

}
