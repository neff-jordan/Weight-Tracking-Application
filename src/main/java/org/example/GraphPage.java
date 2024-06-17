package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPage extends Layout {

    private ScatterPlot scatterPlot;

    GraphPage(String username, SQLiteConnection connection) {
        super(); // Call the constructor of the superclass (Layout)

        // Modify buttons' actions if needed
        graphButton.addActionListener(this); // Assuming graphButton is declared in the Layout class

        // Sample data points - will replace with users weigh-in data and then perform regression 
        List<Point> points = new ArrayList<>();
        points.add(new Point(100, 200));
        points.add(new Point(105, 200));
        points.add(new Point(110, 200));
        points.add(new Point(113, 205));
        points.add(new Point(200, 250));
        points.add(new Point(300, 300));
        points.add(new Point(400, 350));
        points.add(new Point(500, 400));

        // Create the scatter plot panel
        scatterPlot = new ScatterPlot(points);

        // Add scatter plot panel to the content pane
        frame.add(scatterPlot, BorderLayout.CENTER);

        JLabel weight = new JLabel("Weight (lbs)", SwingConstants.CENTER);
        frame.add(weight, BorderLayout.WEST);

        JLabel time = new JLabel("Time (days)", SwingConstants.CENTER);
        frame.add(time, BorderLayout.SOUTH);
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e); // Call the superclass's actionPerformed method
    }


}
