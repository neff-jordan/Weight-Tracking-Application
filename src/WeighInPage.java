package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WeighInPage extends Layout {
    
    WeighInPage(String userID) { 

        // inherit GUI from parent 
        super();

        JPanel abstractContainer = new JPanel(new BorderLayout());
		frame.add(abstractContainer, BorderLayout.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        JPanel bot = new JPanel(new BorderLayout());
        abstractContainer.add(top, BorderLayout.NORTH);
        abstractContainer.add(bot, BorderLayout.CENTER);

        JLabel currentWeight = new JLabel("Enter Current Weight: ", SwingConstants.CENTER);
        top.add(currentWeight, BorderLayout.NORTH);
        JTextArea userInput = new JTextArea();
        top.add(userInput, BorderLayout.CENTER);
        JButton userSubmit = new JButton("Enter"); 
        top.add(userSubmit, BorderLayout.SOUTH);

        JLabel targetWeight = new JLabel("Enter New Target Weight: ", SwingConstants.CENTER);
        bot.add(targetWeight, BorderLayout.NORTH);
        JTextArea userInput2 = new JTextArea();
        bot.add(userInput2, BorderLayout.CENTER);
        JButton userSubmit2 = new JButton("Enter"); 
        bot.add(userSubmit2, BorderLayout.SOUTH);

        // add spacing
		JLabel lspace = new JLabel("        ");
		JLabel rspace = new JLabel("        ");
		frame.add(lspace, BorderLayout.WEST);
		frame.add(rspace, BorderLayout.EAST);

    }

    // set up action listener 
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WeighInPage("userID");
        });
    }
     

}
