package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends Layout implements ActionListener {

	private JTextField userIDField = new JTextField();
	private JPasswordField userPasswordField = new JPasswordField();
	private JLabel userIDLabel = new JLabel("username:");
	private JLabel userPasswordLabel = new JLabel("password:");
	private JLabel messageLabel = new JLabel();
	private JButton loginButton = new JButton("Login");
	private JButton createButton = new JButton("Create Account");
	private JButton resetButton = new JButton("Reset");
	SQLiteConnection connection = SQLiteConnection.getInstance();


	public LoginPage(CardLayout cardLayout, JPanel cardPanel, SQLiteConnection connection) {
		super(cardLayout, cardPanel);
		this.connection = connection;

		// Set layout to null for absolute positioning
		this.setLayout(null);

		userIDLabel.setBounds(113, 170, 75, 25);
		userPasswordLabel.setBounds(113, 220, 75, 25);
		messageLabel.setBounds(125, 250, 250, 35);
		messageLabel.setFont(new Font(null, Font.ITALIC, 25));
		userIDField.setBounds(188, 170, 200, 25);
		userPasswordField.setBounds(188, 220, 200, 25);
		loginButton.setBounds(65, 320, 125, 50);
		createButton.setBounds(195, 320, 125, 50);
		resetButton.setBounds(325, 320, 125, 50);

		loginButton.setFocusable(false);
		createButton.setFocusable(false);
		resetButton.setFocusable(false);

		loginButton.addActionListener(this);
		createButton.addActionListener(this);
		resetButton.addActionListener(this);

		this.add(userIDLabel);
		this.add(userPasswordLabel);
		this.add(messageLabel);
		this.add(userIDField);
		this.add(userPasswordField);
		this.add(loginButton);
		this.add(createButton);
		this.add(resetButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			userIDField.setText("");
			userPasswordField.setText("");
		}

		if (e.getSource() == createButton) {
			cardLayout.show(cardPanel, "Create Account");
		}

		if (e.getSource() == loginButton) {
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			SQLiteConnection connect = SQLiteConnection.getInstance();

			if (connect.containsUsername(userID)) {
				if (connect.getPassword(userID).equals(password)) {
					CurrentUser.setInstance(userID);
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login successful");

					// Switch to HomePage
					HomePage homePage = new HomePage(cardLayout, cardPanel, userID, connection);
					cardPanel.add(homePage, "Home");
					//cardLayout.show(cardPanel, "Home");

					CurrentUser currentUser = CurrentUser.getInstance();
					WeighInPage weighInPage = new WeighInPage(cardLayout,cardPanel,currentUser.getLoggedInUser(),connection);
					GraphPage graphPage = new GraphPage(cardLayout,cardPanel,currentUser.getLoggedInUser(), connection);
					HistoryPage historyPage = new HistoryPage(cardLayout,cardPanel,currentUser.getLoggedInUser(),connection);

					cardPanel.add(weighInPage,"Weigh-In");
					cardPanel.add(graphPage, "Graph");
					cardPanel.add(historyPage,"History");

					switchToCard("Home");

				} else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Wrong password");
				}
			} else {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Username not found");
			}
		}
	}
	public void switchToCard(String cardName) {
		cardLayout.show(cardPanel, cardName);
		cardPanel.revalidate();
		cardPanel.repaint();
	}

}
