package org.example;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends Layout {

    JLabel startLabel = new JLabel("Starting Weight");
    JLabel currentLabel = new JLabel("Current Weight");
    JLabel targetLabel = new JLabel("Target Weight");
    JLabel startNumLabel = new JLabel("170");
    JLabel currentNumLabel = new JLabel("172");
    JLabel targetNumLabel = new JLabel("185");

    HomePage(String userID) {

		super();

		////////////////////////////////////
		// need a second container to store the content below the nav bar 
		////////////////////////////////////

		JPanel abstractContainer = new JPanel(new BorderLayout());
		frame.add(abstractContainer, BorderLayout.CENTER);

        JPanel progressPanel = new JPanel(new GridLayout(2, 3)); 

		// Create inner panels for each label to center them ////////////////
		JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		startPanel.add(startLabel);
		progressPanel.add(startPanel);

		JPanel currentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		currentPanel.add(currentLabel);
		progressPanel.add(currentPanel);

		JPanel targetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		targetPanel.add(targetLabel);
		progressPanel.add(targetPanel);

		JPanel startNumPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		startNumPanel.add(startNumLabel);
		progressPanel.add(startNumPanel);

		JPanel currentNumPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		currentNumPanel.add(currentNumLabel);
		progressPanel.add(currentNumPanel);

		JPanel targetNumPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		targetNumPanel.add(targetNumLabel);
		progressPanel.add(targetNumPanel);

		abstractContainer.add(progressPanel, BorderLayout.NORTH);

		///////////////////////////////

		// Create a new panel for the progress bar and its title
		JPanel progressBarPanel = new JPanel(new BorderLayout());
		JProgressBar weightProgress = new JProgressBar(0,100);

		// call the user class and get updated number
		weightProgress.setValue(10);
		weightProgress.setStringPainted(true);
		weightProgress.setFont(new Font("MV Boli",Font.BOLD,25));
		weightProgress.setForeground(Color.red);
		weightProgress.setBackground(Color.black);
			
		JLabel progressBarTitle = new JLabel("Weight Progress");
		progressBarTitle.setHorizontalAlignment(SwingConstants.CENTER); 
		progressBarPanel.add(progressBarTitle, BorderLayout.NORTH); //
		progressBarPanel.add(weightProgress, BorderLayout.CENTER);
		abstractContainer.add(progressBarPanel, BorderLayout.CENTER);
 
		// average wieght gain/loss per week 
		JLabel stats = new JLabel("This is a random stat: X%",SwingConstants.CENTER);
		abstractContainer.add(stats, BorderLayout.SOUTH);

		// add BMI calculation - south 
		JLabel bmi = new JLabel("Your current BMI score is 24.4",SwingConstants.CENTER); 
		frame.add(bmi, BorderLayout.SOUTH);

		// add spacing
		JLabel lspace = new JLabel("        ");
		JLabel rspace = new JLabel("        ");
		frame.add(lspace, BorderLayout.WEST);
		frame.add(rspace, BorderLayout.EAST);

    }

	// set up action listener 
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}

	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage("userID");
        });
    }
	 
}
