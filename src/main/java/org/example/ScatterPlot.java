package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScatterPlot extends JPanel {

    private List<Point> points;

    public ScatterPlot(List<Point> points) {
        this.points = points;
        this.setPreferredSize(new Dimension(600, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Clear the panel
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50); // X-axis
        g2d.drawLine(50, getHeight() - 50, 50, 50); // Y-axis

        // Draw points
        g2d.setColor(Color.BLUE);
        for (Point point : points) {
            int x = (int) point.getX() + 50; // Adjust for axis offset
            int y = getHeight() - (int) point.getY() - 50; // Adjust for axis offset
            g2d.fillOval(x - 3, y - 3, 6, 6); // Draw point
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Sample data points
            List<Point> points = new ArrayList<>();
            points.add(new Point(100, 200));
            points.add(new Point(200, 250));
            points.add(new Point(300, 300));
            points.add(new Point(400, 350));
            points.add(new Point(500, 400));

            // Create the scatter plot panel
            ScatterPlot scatterPlot = new ScatterPlot(points);

            // Create and configure the frame
            JFrame frame = new JFrame("Custom Scatter Plot");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(scatterPlot, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
}
