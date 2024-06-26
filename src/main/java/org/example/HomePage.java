package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends Layout implements ComponentListener {

	CurrentUser currentUser = CurrentUser.getInstance();
	JLabel startLabel = new JLabel("Starting Weight");
	JLabel currentLabel = new JLabel("Current Weight");
	JLabel targetLabel = new JLabel("Target Weight");

	private final JLabel startNumLabel;
	private final JLabel currentNumLabel;
	private final JLabel targetNumLabel;
	private final JProgressBar weightProgress;
	private final JLabel bmi;

	HomePage(CardLayout cardLayout, JPanel cardPanel, String userID, SQLiteConnection connection) {

		super(cardLayout, cardPanel);

		startNumLabel = new JLabel("" + connection.getStartWeight(currentUser.getLoggedInUser()));
		currentNumLabel = new JLabel("" + connection.getCurrentWeight(currentUser.getLoggedInUser()));
		targetNumLabel = new JLabel("" + connection.getTargetWeight(currentUser.getLoggedInUser()));

		// change text size
		startLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		currentLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		targetLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));

		startNumLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
		currentNumLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
		targetNumLabel.setFont(new Font("MV Boli", Font.BOLD, 20));


		startLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Top margin of 20 pixels
		currentLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Top margin of 20 pixels
		targetLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Top margin of 20 pixels

		JPanel abstractContainer = new JPanel(new BorderLayout());
		this.add(abstractContainer, BorderLayout.CENTER);

		JPanel progressPanel = new JPanel(new GridLayout(2, 3));

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

		//progressPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Creates a rigid area of height 20 pixels

		//progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		abstractContainer.add(progressPanel, BorderLayout.NORTH);
		//abstractContainer.add(Box.createVerticalGlue());


		/////////////////////////////////////////////////////////////

		JPanel progressBarPanel = new JPanel();
		progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
		progressBarPanel.setBackground(null);

		JLabel progressBarTitle = new JLabel("Weight Progress Goal");
		progressBarTitle.setFont(new Font("MV Boil", Font.PLAIN, 15));
		progressBarTitle.setHorizontalAlignment(SwingConstants.CENTER);
		progressBarTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		weightProgress = new JProgressBar(0, 100);
		weightProgress.setStringPainted(true);
		weightProgress.setFont(new Font("MV Boli", Font.BOLD, 25));
		weightProgress.setPreferredSize(new Dimension(500, 30));
		weightProgress.setMaximumSize(new Dimension(500, 30));  // Prevents it from growing vertically
		weightProgress.setAlignmentX(Component.CENTER_ALIGNMENT);

		double starting = connection.getStartWeight(currentUser.getLoggedInUser());
		double current = connection.getCurrentWeight(currentUser.getLoggedInUser());
		double target = connection.getTargetWeight(currentUser.getLoggedInUser());

		double denom = target - starting;
		double numer = current - starting;
		double percentage = (numer / denom) * 100;

		weightProgress.setValue((int) (percentage));

		progressBarPanel.add(Box.createVerticalGlue());
		progressBarPanel.add(progressBarTitle);
		progressBarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		progressBarPanel.add(weightProgress);
		progressBarPanel.add(Box.createVerticalGlue());

		abstractContainer.add(progressBarPanel, BorderLayout.CENTER);

		double BMIans = 703 * (current / Math.pow(connection.getHeight(currentUser.getLoggedInUser()), 2));
		String classification = bmiClassification(BMIans);
		String formattedBMI = String.format("%.1f", BMIans);
		bmi = new JLabel("Your BMI score is " + formattedBMI + " | Weight Classification = " + classification, SwingConstants.CENTER);

		bmi.setAlignmentX(Component.CENTER_ALIGNMENT);
		progressBarPanel.add(bmi);
		progressBarPanel.add(Box.createVerticalGlue());

		JLabel lspace = new JLabel("        ");
		JLabel rspace = new JLabel("        ");
		this.add(lspace, BorderLayout.WEST);
		this.add(rspace, BorderLayout.EAST);

		this.addComponentListener(this);
	}

	public void componentShown(ComponentEvent e) {
		CurrentUser currentUser = CurrentUser.getInstance();
		startNumLabel.setText("" + connection.getStartWeight(currentUser.getLoggedInUser()));
		currentNumLabel.setText("" + connection.getCurrentWeight(currentUser.getLoggedInUser()));
		targetNumLabel.setText("" + connection.getTargetWeight(currentUser.getLoggedInUser()));

		double starting = connection.getStartWeight(currentUser.getLoggedInUser());
		double current = connection.getCurrentWeight(currentUser.getLoggedInUser());
		double target = connection.getTargetWeight(currentUser.getLoggedInUser());

		double denom = target - starting;
		double numer = current - starting;
		double percentage = (numer / denom) * 100;

		weightProgress.setValue((int) percentage);

		double BMIans = 703 * (current / Math.pow(connection.getHeight(currentUser.getLoggedInUser()), 2));
		String classification = bmiClassification(BMIans);
		String formattedBMI = String.format("%.1f", BMIans);
		bmi.setText("Your BMI score is " + formattedBMI + " | Weight Classification = " + classification);
	}

	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e) {}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}

	public static String bmiClassification(double bmi) {
		String ans = "";
		if (bmi < 18.5)
			ans = "underweight";
		else if (bmi >= 18.5 && bmi <= 24.9)
			ans = "normal weight";
		else if (bmi >= 25 && bmi <= 29.9)
			ans = "overweight";
		else
			ans = "obese";
		return ans;
	}
}
