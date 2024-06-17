package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Layout extends JPanel implements ActionListener {
    protected JFrame frame = new JFrame();
    protected JButton homeButton = new JButton("Home");
    protected JButton weighInButton = new JButton("Weigh-In");
    protected JButton graphButton = new JButton("Graph");
    protected JButton historyButton = new JButton("History");
    protected SQLiteConnection connection;

    public Layout() {
        this.connection = SQLiteConnection.getInstance();

        frame.setLayout(new BorderLayout());

        homeButton.addActionListener(this);
        weighInButton.addActionListener(this);
        graphButton.addActionListener(this);
        historyButton.addActionListener(this);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4));
        buttonsPanel.add(homeButton);
        buttonsPanel.add(weighInButton);
        buttonsPanel.add(graphButton);
        buttonsPanel.add(historyButton);
        frame.add(buttonsPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(520, 640);
        frame.setMinimumSize(new Dimension(520, 640));
        frame.setMaximumSize(new Dimension(520, 640));
        frame.setTitle("Weight Tracker");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        CurrentUser currentUser = CurrentUser.getInstance();
        String user = currentUser.getLoggedInUser();

        if (e.getSource() == homeButton) {
            frame.dispose();
            new HomePage(user, connection);
        } else if (e.getSource() == weighInButton) {
            frame.dispose();
            new WeighInPage(user, connection);
        } else if (e.getSource() == graphButton) {
            frame.dispose();
            new GraphPage(user, connection);
        } else if (e.getSource() == historyButton) {
            frame.dispose();
            new HistoryPage(user, connection);
        }
    }
}
