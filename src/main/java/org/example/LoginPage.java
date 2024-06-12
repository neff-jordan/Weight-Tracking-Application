package org.example;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class LoginPage implements ActionListener{

	JFrame frame = new JFrame();
	JButton loginButton = new JButton("Login");
	JButton createButton = new JButton("Create Account");
	JButton resetButton = new JButton("Reset");
	JTextField userIDField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIDLabel = new JLabel("username:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	HashMap<String,String> logininfo = new HashMap<String,String>();

	LoginPage(HashMap<String,String> loginInfoOriginal){

		logininfo = loginInfoOriginal;

		userIDLabel.setBounds(113,170,75,25);			/////////
		userPasswordLabel.setBounds(113,220,75,25);	/////////

		messageLabel.setBounds(125,250,250,35);
		messageLabel.setFont(new Font(null,Font.ITALIC,25));

		userIDField.setBounds(188,170,200,25);			/////////
		userPasswordField.setBounds(188,220,200,25);	/////////


		loginButton.setBounds(65,320,125,50);
		createButton.setBounds(195,320,125,50);
		resetButton.setBounds(325,320,125,50);

		loginButton.setFocusable(false);
		createButton.setFocusable(false);
		resetButton.setFocusable(false);

		loginButton.addActionListener(this);
		createButton.addActionListener(this);
		resetButton.addActionListener(this);

		frame.add(userIDLabel);
		frame.add(userPasswordLabel);
		frame.add(messageLabel);
		frame.add(userIDField);
		frame.add(userPasswordField);
		frame.add(loginButton);
		frame.add(resetButton);
		frame.add(createButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(520, 640);
		frame.setMinimumSize(new Dimension(520, 640));
		frame.setMaximumSize(new Dimension(520, 640));
		frame.setLayout(null);
		frame.setTitle("Login");
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==resetButton) {
			userIDField.setText("");
			userPasswordField.setText("");
		}

		if(e.getSource()==createButton) {
			CreateAccount newAccount = new CreateAccount();
			frame.dispose();
		}

		if(e.getSource()==loginButton) {

			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			 // if database contains this username
			if(logininfo.containsKey(userID)) {
				if(logininfo.get(userID).equals(password)) {
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login successful");
					frame.dispose();
					HomePage welcomePage = new HomePage(userID);
				}
				else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Wrong password");
				}

			}
			else {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("username not found");
			}
		}
	}
}