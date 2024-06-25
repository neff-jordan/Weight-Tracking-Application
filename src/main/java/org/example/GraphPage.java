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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GraphPage extends Layout implements ComponentListener {

    private SQLiteConnection connection;
    private String username;
    private JPanel abstractPanel;
    private ChartPanel chartPanel;

    GraphPage(CardLayout cardLayout, JPanel cardPanel, String username, SQLiteConnection connection) {
        super(cardLayout, cardPanel);
        this.username = username;
        this.connection = connection;

        abstractPanel = new JPanel(new BorderLayout());
        this.add(abstractPanel, BorderLayout.CENTER);

        this.addComponentListener(this);
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

        // Temporary fix to hide the x-axis tick labels
        ValueAxis domainAxis = plot.getDomainAxis();
        if (domainAxis instanceof NumberAxis) {
            NumberAxis numberAxis = (NumberAxis) domainAxis;
            numberAxis.setTickLabelsVisible(false); // Hide the x-axis tick labels
        }

        return chart;
    }

    private void refreshGraph() {
        List<Point> points = getWeighInData(username);
        JFreeChart scatterPlot = createChart(points);

        if (chartPanel != null) {
            abstractPanel.remove(chartPanel);
        }

        chartPanel = new ChartPanel(scatterPlot);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setMouseWheelEnabled(true);
        abstractPanel.add(chartPanel, BorderLayout.CENTER);

        abstractPanel.revalidate();
        abstractPanel.repaint();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        refreshGraph();
    }

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentResized(ComponentEvent e) {}
}
