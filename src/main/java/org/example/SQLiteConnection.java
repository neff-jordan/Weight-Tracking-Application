package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteConnection {
    private Connection connection;

    public SQLiteConnection() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            String url = "jdbc:sqlite:/Users/jordanneff/Desktop/Projects/Weight-Tracking-Application/src/main/resources/mydatabase.db"; // Replace with the path to your SQLite database file
            connection = DriverManager.getConnection(url);

            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }



    // CREATE operation
    public void addUser(User user) {
        String sql = "INSERT INTO users(firstName, lastName, username, password, height, weight, targetWeight, startWeight) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getHeight());
            statement.setInt(6, user.getWeight());
            statement.setInt(7,user.getTargetWeight());
            statement.setInt(8, user.getStartWeight());
            statement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add user.");
            e.printStackTrace();
        }
    }

    // READ operation
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("height"),
                        resultSet.getInt("weight"),
                        resultSet.getInt("targetWeight"),
                        resultSet.getInt("startWeight")
                );
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user.");
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE operation
    public void updateUser(User user) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, password = ?, height = ?, weight = ? WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getHeight());
            statement.setInt(5, user.getWeight());
            statement.setString(6, user.getUsername());
            statement.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to update user.");
            e.printStackTrace();
        }
    }

    // DELETE operation
    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to delete user.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection.");
            e.printStackTrace();
        }
    }

    // Check if a username exists
    public boolean containsUsername(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            // If resultSet has any rows, the username exists
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Failed to check if username exists.");
            e.printStackTrace();
        }
        return false;
    }

    public String getPassword(String username) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            // If resultSet has any rows, retrieve the password
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get password.");
            e.printStackTrace();
        }
        return null; // Return null if the username does not exist or an error occurred
    }

    // returns the list of users
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM users";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get all users.");
            e.printStackTrace();
        }
        return users;
    }

    // get start, current, and target weights
    public int getStartWeight(String username) {
        String sql = "SELECT startWeight FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("startWeight");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get start weight.");
            e.printStackTrace();
        }
        return -1; // Return -1 if there is an error or the user does not exist
    }

    public int getCurrentWeight(String username) {
        String sql = "SELECT weight FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("weight");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get current weight.");
            e.printStackTrace();
        }
        return -1; // Return -1 if there is an error or the user does not exist
    }

    public int getTargetWeight(String username) {
        String sql = "SELECT targetWeight FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("targetWeight");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get target weight.");
            e.printStackTrace();
        }
        return -1; // Return -1 if there is an error or the user does not exist
    }


}
