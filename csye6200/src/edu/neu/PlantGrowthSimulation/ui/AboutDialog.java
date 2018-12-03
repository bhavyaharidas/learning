package edu.neu.PlantGrowthSimulation.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AboutDialog implements WindowListener {

	protected JFrame ruleFrame = null;
	protected JPanel mainPanel = null;
	protected JTextArea textArea = null;

	public AboutDialog() {
		initGUI();
	}

	/**
	 * Initialize the Graphical User Interface
	 */
	public void initGUI() {
		System.out.println("View Rules");
		ruleFrame = new JFrame();
		ruleFrame.setSize(800, 400);
		ruleFrame.setTitle("About");

		ruleFrame.setResizable(true);

		// Permit the app to hear about the window opening
		ruleFrame.addWindowListener(this);

		ruleFrame.setLayout(new BorderLayout());
		ruleFrame.add(getMainPanel(), BorderLayout.CENTER);
		ruleFrame.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return - main panel of the about dialog
	 */
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLACK);

		String text = getAboutText();
		textArea = new JTextArea(text);
		textArea.setPreferredSize(new Dimension(100, 100));
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.white);

		mainPanel.add(BorderLayout.CENTER, textArea);
		return mainPanel;
	}

	/**
	 * Helper method that returns the contents of about text
	 * 
	 * @return A brief introduction to the working of the App
	 */
	private String getAboutText() {
		return "\n\nABOUT THE PROGRAM.. \n\nNature is an abundant source of comuptational scenarios and every action happening in nature is a result of its previous state."
				+ "\nPlant Growth App provides a simulation for the growth of a plant over time."
				+ "\n\nThe program lets you observe and analyse the growth of a tree in 2 different ways"
				+ "\n\n\n"
				+ "Step 1 - Start by clicking on 'View Rules' to go through the default rules defined in the system."
				+ "\nStep 2 - Select the rule which you wish to apply for plant growth fromt the drop down menu."
				+ "\nStep 3 - Select the maximum number of generations you would like the plant to grow."
				+ "\nStep 4 - Click on Start button to start the plant growth simulation."
				+ "\n\nThe status bar shows the progress of growth with respect to the maximum number of generations - 15."
				+ "\n\n Additionally, you may pause the simulation and observe plant growth at any point in growth by clicking on 'Pause'."
				+ "\n The 'Resume' button will continue the growth from the paused point."
				+ "\n\nYou may reset the simulation by clicking on 'Reset' button";
	}

}
