package org.example;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HomePage extends Layout implements ComponentListener {

	//SQLiteConnection connection = new SQLiteConnection();
	CurrentUser currentUser = CurrentUser.getInstance();
    JLabel startLabel = new JLabel("Starting Weight");
    JLabel currentLabel = new JLabel("Current Weight");
    JLabel targetLabel = new JLabel("Target Weight");

	private JLabel startNumLabel;
	private JLabel currentNumLabel;
	private JLabel targetNumLabel;
	private JProgressBar weightProgress;
	private JLabel bmi;


    HomePage(CardLayout cardLayout, JPanel cardPanel, String userID, SQLiteConnection connection) {

		super(cardLayout, cardPanel);

		startNumLabel = new JLabel("" + connection.getStartWeight(currentUser.getLoggedInUser()));
		currentNumLabel = new JLabel("" + connection.getCurrentWeight(currentUser.getLoggedInUser()));
		targetNumLabel = new JLabel("" + connection.getTargetWeight(currentUser.getLoggedInUser()));

		////////////////////////////////////
		// need a second container to store the content below the nav bar 
		////////////////////////////////////

		JPanel abstractContainer = new JPanel(new BorderLayout());
		this.add(abstractContainer, BorderLayout.CENTER);

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
		weightProgress = new JProgressBar(0,100);

		//
		// how to show percentage gained
		//
		double starting = connection.getStartWeight(currentUser.getLoggedInUser());
		double current = connection.getCurrentWeight(currentUser.getLoggedInUser());
		double target = connection.getTargetWeight(currentUser.getLoggedInUser());

		double denom = target - starting;
		double numer = current - starting;
		double percentage = (numer / denom) * 100;

		weightProgress.setValue((int)(percentage));
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

		double BMIans = 703 * (current/Math.pow(connection.getHeight(currentUser.getLoggedInUser()),2));

		// method call that classifies how healthy you are based off bmi score
		String classification = bmiClassification(BMIans);

		// Format BMI to one decimal place
		String formattedBMI = String.format("%.1f", BMIans);
		bmi = new JLabel("Your BMI score is " + formattedBMI + " | Weight Classification = " + classification, SwingConstants.CENTER);



		this.add(bmi, BorderLayout.SOUTH);

		// add spacing
		JLabel lspace = new JLabel("        ");
		JLabel rspace = new JLabel("        ");
		this.add(lspace, BorderLayout.WEST);
		this.add(rspace, BorderLayout.EAST);

		this.addComponentListener(this);

    }

	public void componentShown(ComponentEvent e) {
		// Refresh data here
		CurrentUser currentUser = CurrentUser.getInstance();
		startNumLabel.setText("" + connection.getStartWeight(currentUser.getLoggedInUser()));
		currentNumLabel.setText("" + connection.getCurrentWeight(currentUser.getLoggedInUser()));
		targetNumLabel.setText("" + connection.getTargetWeight(currentUser.getLoggedInUser()));

		// Recalculate progress bar value
		double starting = connection.getStartWeight(currentUser.getLoggedInUser());
		double current = connection.getCurrentWeight(currentUser.getLoggedInUser());
		double target = connection.getTargetWeight(currentUser.getLoggedInUser());

		double denom = target - starting;
		double numer = current - starting;
		double percentage = (numer / denom) * 100;

		weightProgress.setValue((int) percentage);

		// Recalculate BMI
		double BMIans = 703 * (current / Math.pow(connection.getHeight(currentUser.getLoggedInUser()), 2));
		String classification = bmiClassification(BMIans);
		String formattedBMI = String.format("%.1f", BMIans);
		bmi.setText("Your BMI score is " + formattedBMI + " | Weight Classification = " + classification);
	}


	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {}


	// set up action listener 
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}

	public static String bmiClassification(double bmi) {
		String ans = "";
		if(bmi<18.5)
			ans = "underweight";
		else if(bmi>=18.5&&bmi<=24.9)
			ans = "normal weight";
		else if(bmi>=25&&bmi<=29.9)
			ans = "overweight";
		else
			ans = "obese";
		return ans;
	}



}
