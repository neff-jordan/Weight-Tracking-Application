package org.example;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Layout extends JPanel implements ActionListener {
    
    JFrame frame = new JFrame();
    JButton homeButton = new JButton("Home");
    JButton weighInButton = new JButton("Weigh-In");
    JButton graphButton = new JButton("Graph");
    JButton historyButton = new JButton("History");
    
    Layout() { 

        frame.setLayout(new BorderLayout());
        
        homeButton.addActionListener(this);
        weighInButton.addActionListener(this);
        graphButton.addActionListener(this);
		historyButton.addActionListener(this);

        // Set preferred sizes for buttons
        homeButton.setPreferredSize(new Dimension(125, 125));
        //weighInButton.setPreferredSize(new Dimension(125, 125));
        //graphButton.setPreferredSize(new Dimension(125, 125));
        //historyButton.setPreferredSize(new Dimension(125, 125));

        // Add buttons to the frame
        JPanel buttonsPanel = new JPanel(new GridLayout(1,4));
        buttonsPanel.add(homeButton);
        buttonsPanel.add(weighInButton);
        buttonsPanel.add(graphButton);
        buttonsPanel.add(historyButton);
        frame.add(buttonsPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		frame.setBackground(Color.yellow);
        frame.setSize(520, 640);
		frame.setMinimumSize(new Dimension(520, 640));
        frame.setMaximumSize(new Dimension(520, 640)); 
		frame.setTitle("Weight Tracker");
    }

    // set up action listener 
	public void actionPerformed(ActionEvent e) {

        // how do i get the current user object to be here
        CurrentUser currentUser = CurrentUser.getInstance();
        String user = currentUser.getLoggedInUser();

		if(e.getSource()==homeButton) { 
			frame.dispose();
			HomePage home = new HomePage(user);
		} else if(e.getSource()==weighInButton) { 
			frame.dispose();
			WeighInPage weigh = new WeighInPage(user);
		} else if (e.getSource()==graphButton) { 
            frame.dispose();
            GraphPage graphPage = new GraphPage(user);
        } else if (e.getSource()==historyButton) { 
            frame.dispose();
            HistoryPage history = new HistoryPage(user);
        } 
	}
}
