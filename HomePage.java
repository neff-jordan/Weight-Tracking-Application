import java.awt.*;
import javax.swing.*;

public class HomePage {

    JFrame frame = new JFrame();

    JButton homeButton = new JButton("Home");
    JButton weighInButton = new JButton("Weigh-In");
    JButton graphButton = new JButton("Graph");
    JButton historyButton = new JButton("History");

    JLabel startLabel = new JLabel("Starting Weight");
    JLabel currentLabel = new JLabel("Current Weight");
    JLabel targetLabel = new JLabel("Target Weight");
    JLabel startNumLabel = new JLabel("170");
    JLabel currentNumLabel = new JLabel("172");
    JLabel targetNumLabel = new JLabel("185");

    HomePage(String userID) {

		frame.setLayout(new BorderLayout());

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

		////////////////////////////////////
		// need a second container to store the content below the nav bar 
		////////////////////////////////////

		JPanel abstractContainer = new JPanel(new BorderLayout());
		frame.add(abstractContainer, BorderLayout.CENTER);

        JPanel progressPanel = new JPanel(new GridLayout(2,3)); 
        progressPanel.add(startLabel);
        progressPanel.add(currentLabel);
		progressPanel.add(targetLabel);
		progressPanel.add(startNumLabel);
        progressPanel.add(currentNumLabel);
        progressPanel.add(targetNumLabel);
		abstractContainer.add(progressPanel, BorderLayout.NORTH);


		// add progress bar - center 
		JProgressBar weightProgress = new JProgressBar(); 
		abstractContainer.add(weightProgress, BorderLayout.CENTER); 

		// 
		JLabel stats = new JLabel("This is a random stat: X%");
		abstractContainer.add(stats, BorderLayout.SOUTH);

		// add BMI calculation - south 
		JLabel bmi = new JLabel("Your current BMI score is 24.4"); 
		frame.add(bmi, BorderLayout.SOUTH);



        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 640);
        frame.setVisible(true);
		frame.setBackground(Color.black);
		frame.setTitle("Weight Tracker");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage("userID");
        });
    }
}
