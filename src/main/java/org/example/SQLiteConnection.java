
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Connect to the database
            String url = "jdbc:sqlite:/path/to/your/database.db"; // Replace with the path to your SQLite database file
            connection = DriverManager.getConnection(url);
            
            // Do something with the connection, e.g., execute queries
            
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // CRUD

}