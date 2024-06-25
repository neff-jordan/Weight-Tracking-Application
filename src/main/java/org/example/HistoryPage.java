package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HistoryPage extends Layout implements ComponentListener {

    public static double current;
    public static double prevCurrent;
    public static double target;
    public static double prevTarget;
    private String username;
    private JPanel panel;
    private JScrollPane scroll;
    private SQLiteConnection connection = SQLiteConnection.getInstance();

    HistoryPage(CardLayout cardLayout, JPanel cardPanel, String username, SQLiteConnection connection) {
        super(cardLayout, cardPanel);
        this.username = username;

        JPanel abstractPanel = new JPanel(new BorderLayout());
        this.add(abstractPanel, BorderLayout.CENTER);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create the scroll pane and add the panel to it
        scroll = new JScrollPane(panel);
        abstractPanel.add(scroll, BorderLayout.CENTER);

        // Add spacing
        JLabel lspace = new JLabel("        ");
        JLabel rspace = new JLabel("        ");
        this.add(lspace, BorderLayout.WEST);
        this.add(rspace, BorderLayout.EAST);

        JLabel title = new JLabel("Weight Log", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        abstractPanel.add(title, BorderLayout.NORTH);

        this.addComponentListener(this);
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

    private void refreshHistory() {
        panel.removeAll();

        if (current == prevCurrent && target == prevTarget) {
            // do nothing
        } else if (current != prevCurrent) {
            connection.logUserChange(username, "Weigh-In", prevCurrent, current);
            prevCurrent = current;
        } else {
            connection.logUserChange(username, "Target #", prevTarget, target);
            prevTarget = target;
        }

        List<String> list = connection.getUserChangeHistory(username);
        for (String userLog : list) {
            JLabel label = new JLabel(userLog);
            JLabel space = new JLabel("\n");
            panel.add(label);
            panel.add(space);
        }

        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        refreshHistory();
    }

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentResized(ComponentEvent e) {}
}
