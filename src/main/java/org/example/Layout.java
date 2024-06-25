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

    // Method to switch cards and repaint them
    protected void switchToCard(String cardName) throws RefreshFailedException {
        cardLayout.show(cardPanel, cardName);
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        if (e.getSource() == homeButton) {
            try {
                switchToCard("Home");
            } catch (RefreshFailedException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == weighInButton) {
            try {
                switchToCard("Weigh-In");
            } catch (RefreshFailedException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == graphButton) {
            try {
                switchToCard("Graph");
            } catch (RefreshFailedException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == historyButton) {
            try {
                switchToCard("History");
            } catch (RefreshFailedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
