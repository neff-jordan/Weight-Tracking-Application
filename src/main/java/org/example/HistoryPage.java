package org.example;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;

public class HistoryPage extends Layout {
    
    HistoryPage(String  username) {
        super();

        JPanel abstractPanel = new JPanel(new BorderLayout());
        frame.add(abstractPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel testLabel = new JLabel("Jordan Neff, Weight = 171, Date: 06/08/2024");
        panel.add(testLabel);
        for (int i = 0; i < 40; i++) {
            JLabel label = new JLabel("Label " + (i + 1));
            panel.add(label);
        }

        JScrollPane scroll = new JScrollPane(panel);
        abstractPanel.add(scroll, BorderLayout.CENTER);


        // add spacing
		JLabel lspace = new JLabel("        ");
		JLabel rspace = new JLabel("        ");
		frame.add(lspace, BorderLayout.WEST);
		frame.add(rspace, BorderLayout.EAST);

        JLabel title = new JLabel("Weight Log", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 20));

        abstractPanel.add(title, BorderLayout.NORTH);


    }

    public void actionPerformed(ActionEvent e) { 
        super.actionPerformed(e);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HistoryPage("");
        });
    }
     

}
