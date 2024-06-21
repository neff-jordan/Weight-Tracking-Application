package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GraphPage extends Layout {

    private SQLiteConnection connection;

    GraphPage(String username, SQLiteConnection connection) {

        super();
        this.connection = connection;
        JPanel abstractPanel = new JPanel(new BorderLayout());
        frame.add(abstractPanel, BorderLayout.CENTER);

        // Fetch user weigh-in data
        List<Point> points = getWeighInData(username);

        // Create the scatter plot
        JFreeChart scatterPlot = createChart(points);

        // Create and set up the panel
        ChartPanel chartPanel = new ChartPanel(scatterPlot);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setMouseWheelEnabled(true);



        abstractPanel.add(chartPanel, BorderLayout.CENTER);

        // Add spacing
        //JLabel lspace = new JLabel("        ");
        //JLabel rspace = new JLabel("        ");
        JLabel TOP = new JLabel("\n\n");
        abstractPanel.add(TOP, BorderLayout.NORTH);
        //abstractPanel.add(lspace, BorderLayout.WEST);
        //abstractPanel.add(rspace, BorderLayout.EAST);
    }

    private List<Point> getWeighInData(String username) {
        List<Point> points = new ArrayList<>();
        String sql = "SELECT julianday(change_date) - julianday((SELECT MIN(change_date) FROM user_history WHERE username = ? AND change_type = 'Weigh-In')) AS days, new_value AS weight FROM user_history WHERE username = ? AND change_type = 'Weigh-In' ORDER BY change_date";
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int days = resultSet.getInt("days");
                double weight = resultSet.getDouble("weight");
                points.add(new Point(days, (int) weight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    private JFreeChart createChart(List<Point> points) {
        XYSeries series = new XYSeries("Weight Progress Scatter Plot");

        for (Point point : points) {
            series.add(point.getX(), point.getY());
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Weight Progress",
                "Time (days)",
                "Weight (lbs)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);

        ///////////////////////////////////////////////////////////////////////////////
        // This is a temporary fix to hide the x-axis tick labels till I figure out
        // how to make it list the weeks/days properly
        ValueAxis domainAxis = plot.getDomainAxis();
        if (domainAxis instanceof NumberAxis) {
            NumberAxis numberAxis = (NumberAxis) domainAxis;
            numberAxis.setTickLabelsVisible(false); // Hide the x-axis tick labels
        }
        ///////////////////////////////////////////////////////////////////////////////

        return chart;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }
}
