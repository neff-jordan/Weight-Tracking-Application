package org.example;

import java.sql.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HistoryPage extends Layout {

    public static double current;
    public static double prevCurrent;
    public static double target;
    public static double prevTarget;

    HistoryPage(String username, SQLiteConnection connection) {

        super();
        this.connection = connection;

        JPanel abstractPanel = new JPanel(new BorderLayout());
        frame.add(abstractPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Retrieve the latest recorded values for weigh-in and target
        //double lastWeighIn = getLastWeighIn(username, connection);
        //double lastTarget = getLastTarget(username);

        // Determine if the log should be for a weigh-in or a target change
        if(current == prevCurrent && target == prevTarget) {
            // do nothing
        } else if(current != prevCurrent) {
            connection.logUserChange(username, "Weigh-In", prevCurrent, current);
            prevCurrent = current;
        } else {
            connection.logUserChange(username, "Target  #", prevTarget, target);
            prevTarget = target;
        }

        List<String> list = connection.getUserChangeHistory(username);
        for (String userLog : list) {
            JLabel label = new JLabel(userLog);
            JLabel space = new JLabel("\n");
            panel.add(label);
            panel.add(space);
        }

        JScrollPane scroll = new JScrollPane(panel);
        abstractPanel.add(scroll, BorderLayout.CENTER);

        // Add spacing
        JLabel lspace = new JLabel("        ");
        JLabel rspace = new JLabel("        ");
        frame.add(lspace, BorderLayout.WEST);
        frame.add(rspace, BorderLayout.EAST);

        JLabel title = new JLabel("Weight Log", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 20));

        abstractPanel.add(title, BorderLayout.NORTH);
    }

    private double getLastWeighIn(String username, SQLiteConnection connection) {
        String sql = "SELECT old_value FROM user_history WHERE username = ? AND change_type = 'Weigh-In' ORDER BY change_date DESC LIMIT 1";
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("old_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value or handle appropriately
    }

    private double getLastTarget(String username) {
        String sql = "SELECT old_value FROM user_history WHERE username = ? AND change_type = 'Target #' ORDER BY change_date DESC LIMIT 1";
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("old_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value or handle appropriately
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }
}
