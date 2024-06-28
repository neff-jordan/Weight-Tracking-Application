package org.example;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteConnection {
    private static SQLiteConnection instance;
    private Connection connection;
    private String url = "jdbc:sqlite:src/main/resources/mydatabase.db";


    private SQLiteConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    public static synchronized SQLiteConnection getInstance() {
        if (instance == null) {
            instance = new SQLiteConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    //////////////////////////////////////////////////////////////////////
    // for users table which interfaces with most classes
    //////////////////////////////////////////////////////////////////////

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

    public boolean setStartWeight(String username, double newStart) {
        String sql = "UPDATE users SET startWeight = ? WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, newStart);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true; // Successfully updated
            }
        } catch (SQLException e) {
            System.out.println("Failed to set current weight.");
            e.printStackTrace();
        }
        return false; // Return false if there is an error or the user does not exist
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

    public boolean setCurrentWeight(String username, double newCurrent) {
        String sql = "UPDATE users SET weight = ? WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, newCurrent);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true; // Successfully updated
            }
        } catch (SQLException e) {
            System.out.println("Failed to set current weight.");
            e.printStackTrace();
        }
        return false; // Return false if there is an error or the user does not exist
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

    public boolean setTargetWeight(String username, double newCurrent) {
        String sql = "UPDATE users SET targetWeight = ? WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, newCurrent);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true; // Successfully updated
            }
        } catch (SQLException e) {
            System.out.println("Failed to set current weight.");
            e.printStackTrace();
        }
        return false; // Return false if there is an error or the user does not exist
    }

    public int getHeight(String username) {
        String sql = "SELECT height FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("height");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get height.");
            e.printStackTrace();
        }
        return -1;
    }


    //////////////////////////////////////////////////////////////////////
    // for user_history table which interfaces with the HistoryPage class
    //////////////////////////////////////////////////////////////////////


    public void logUserChange(String username, String changeType, double oldValue, double newValue) {
        String sql = "INSERT INTO user_history(username, change_type, old_value, new_value) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, changeType);
            statement.setDouble(3, oldValue);
            statement.setDouble(4, newValue);
            statement.executeUpdate();
            System.out.println("User change logged successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to log user change.");
            e.printStackTrace();
        }
    }

    public List<String> getUserChangeHistory(String username) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT change_type, old_value, new_value, change_date FROM user_history WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            while (resultSet.next()) {
                String changeType = resultSet.getString("change_type");
                double oldValue = resultSet.getDouble("old_value");
                double newValue = resultSet.getDouble("new_value");
                Timestamp changeDate = resultSet.getTimestamp("change_date");
                String formattedDate = dateFormat.format(changeDate);
                history.add(String.format("Change Type: %s | Old: %.1f | New: %.1f | Date: %s",
                        changeType, oldValue, newValue, formattedDate));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user change history.");
            e.printStackTrace();
        }
        return history;
    }
}











