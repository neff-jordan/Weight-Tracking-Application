package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount extends Layout implements ActionListener {

    private JTextField first = new JTextField();
    private JTextField last = new JTextField();
    private JTextField user = new JTextField();
    private JPasswordField pass = new JPasswordField();
    private JTextField inches = new JTextField();
    private JTextField lbs = new JTextField();
    private JTextField target = new JTextField();

    private JLabel firstname = new JLabel("firstname: ");
    private JLabel lastname = new JLabel("lastname: ");
    private JLabel username = new JLabel("username: ");
    private JLabel password = new JLabel("password: ");
    private JLabel height = new JLabel("height (inches): ");
    private JLabel weight = new JLabel("weight (lbs): ");
    private JLabel targetWeight = new JLabel("target weight (lbs): ");

    private JButton submit = new JButton("Submit");
    private JButton back = new JButton("Back");
    SQLiteConnection connection = SQLiteConnection.getInstance();


    public CreateAccount(CardLayout cardLayout, JPanel cardPanel, SQLiteConnection connection) {
        super(cardLayout, cardPanel);
        this.connection = connection;

        this.setLayout(null);

        firstname.setBounds(98, 50, 100, 25);
        lastname.setBounds(98, 100, 100, 25);
        username.setBounds(98, 150, 100, 25);
        password.setBounds(98, 200, 100, 25);
        height.setBounds(98, 250, 120, 25);
        weight.setBounds(98, 300, 120, 25);
        targetWeight.setBounds(98, 350, 120, 25);

        first.setBounds(223, 50, 200, 25);
        last.setBounds(223, 100, 200, 25);
        user.setBounds(223, 150, 200, 25);
        pass.setBounds(223, 200, 200, 25);
        inches.setBounds(223, 250, 200, 25);
        lbs.setBounds(223, 300, 200, 25);
        target.setBounds(223, 350, 200, 25);

        submit.setBounds(210, 450, 100, 25);
        submit.addActionListener(this);
        back.setBounds(210, 400, 100, 25);
        back.addActionListener(this);

        this.add(firstname);
        this.add(lastname);
        this.add(username);
        this.add(password);
        this.add(height);
        this.add(weight);
        this.add(targetWeight);
        this.add(first);
        this.add(last);
        this.add(user);
        this.add(pass);
        this.add(inches);
        this.add(lbs);
        this.add(target);
        this.add(submit);
        this.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            cardLayout.show(cardPanel, "Login");
        }

        if (e.getSource() == submit) {
            // Check to see if the fields are filled
            String firstNameText = first.getText();
            String lastNameText = last.getText();
            String usernameText = user.getText();
            String heightText = inches.getText();   // Needs exception check here to make sure the input is a number
            String weightText = lbs.getText();      // Needs exception check here to make sure the input is a number
            String passwordText = new String(pass.getPassword());
            String targetWeightText = target.getText();

            if (firstNameText.isEmpty() || lastNameText.isEmpty() || usernameText.isEmpty() || heightText.isEmpty() || weightText.isEmpty() || passwordText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                int convertedHeight = Integer.parseInt(heightText);
                int convertedWeight = Integer.parseInt(weightText);
                int convertedTargetWeight = Integer.parseInt(targetWeightText);

                SQLiteConnection connection = SQLiteConnection.getInstance();
                for (String usernames : connection.getAllUsers()) {
                    if (usernames.equals(usernameText)) {
                        JOptionPane.showMessageDialog(this, "Error! Username already taken.");
                        return;
                    }
                }

                User newUser = new User(firstNameText, lastNameText, usernameText, passwordText, convertedHeight, convertedWeight, convertedTargetWeight, convertedWeight);
                connection.addUser(newUser);

                JOptionPane.showMessageDialog(this, "Account created successfully!");
                cardLayout.show(cardPanel, "Login");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Height, weight, and target weight must be numbers.");
            }
        }
    }
}