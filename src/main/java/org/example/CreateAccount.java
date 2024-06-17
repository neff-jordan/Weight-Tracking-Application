package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CreateAccount implements ActionListener {
    
    JFrame frame = new JFrame();

    JLabel firstname = new JLabel("firstname: ");
    JLabel lastname = new JLabel("lastname: ");
    JLabel username = new JLabel("username: ");
    JLabel password = new JLabel("password: ");
    JLabel height = new JLabel("height (inches): ");
    JLabel weight = new JLabel("weight (lbs): ");
    JLabel targetWeight = new JLabel("target weight (lbs): ");
    //JLabel startWeight = new JLabel("starting (lbs): ");

    JTextField first = new JTextField();
    JTextField last = new JTextField();
    JTextField user = new JTextField();
    JPasswordField pass = new JPasswordField(); 
    JTextField inches = new JTextField();
    JTextField lbs = new JTextField();
    JTextField target = new JTextField();
    //JTextField start = new JTextField();


    JButton submit = new JButton("submit");
    JButton back = new JButton("back");

    CreateAccount() { 

        firstname.setBounds(98, 50, 100, 25);
        lastname.setBounds(98, 100, 100, 25);
        username.setBounds(98, 150, 100, 25);
        password.setBounds(98, 200, 100, 25); 
        height.setBounds(98, 250, 120, 25);
        weight.setBounds(98, 300, 120, 25);
        targetWeight.setBounds(98,350,120,25);

        first.setBounds(223, 50, 200, 25);
        last.setBounds(223, 100, 200, 25);
        user.setBounds(223, 150, 200, 25);
        pass.setBounds(223, 200, 200, 25); 
        inches.setBounds(223, 250, 200, 25);
        lbs.setBounds(223, 300, 200, 25);
        target.setBounds(223,350,200,25);

        submit.setBounds(210, 450, 100, 25);
        submit.addActionListener(this);
        back.setBounds(210,400,100,25);
        back.addActionListener(this);

        frame.add(firstname);
        frame.add(lastname);
        frame.add(username);
        frame.add(password); 
        frame.add(height);
        frame.add(weight);
        frame.add(targetWeight);
        frame.add(first);
        frame.add(last);
        frame.add(user);
        frame.add(pass); 
        frame.add(inches);
        frame.add(lbs);
        frame.add(target);
        frame.add(submit);
        frame.add(back);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 640);
		frame.setMinimumSize(new Dimension(520, 640));
        frame.setMaximumSize(new Dimension(520, 640));
        frame.setLayout(null);
        frame.setTitle("Create Account");
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) { 

        if(e.getSource()==back) {
            frame.dispose();
            LoginPage goBackToLogin = new LoginPage();
        }

        if(e.getSource()==submit) { 

            // check to see if the fields are filled
            String firstNameText = first.getText();
            String lastNameText = last.getText();
            String usernameText = user.getText();
            String heightText = inches.getText();   // needs exception check here to make sure the input is a number
            String weightText = lbs.getText();      // needs exception check here to make sure the input is a number
            String passwordText = new String(pass.getPassword());
            String targetWeightText = target.getText();

            if (firstNameText.isEmpty() || lastNameText.isEmpty() || usernameText.isEmpty() || heightText.isEmpty() || weightText.isEmpty() || passwordText.isEmpty()) {

                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                return;
            }


            SQLiteConnection connection = SQLiteConnection.getInstance();
            // rewrite these first few lines and replace with database calls.
            for(String usernames : connection.getAllUsers()) {
                if(usernames.equals(usernameText)) { 
                    JOptionPane.showMessageDialog(frame, "Error! Username already taken.");
                    return;
                } else {
                    int convertedHeight = Integer.parseInt(heightText);
                    int convertedWeight = Integer.parseInt(weightText);
                    int convertedTargetWeight = Integer.parseInt(targetWeightText);                                                                            // the init and starting wieght will be the same upon account creation
                    User newUser = new User(firstNameText, lastNameText, usernameText, passwordText, convertedHeight, convertedWeight, convertedTargetWeight, convertedWeight);
                    //SQLiteConnection connect = new SQLiteConnection();
                    connection.addUser(newUser);
                }
            }
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            LoginPage goBackToLogin = new LoginPage();
        }
    }

    //public static void main(String[] args) { SwingUtilities.invokeLater(() -> { new CreateAccount();});}
    
}