package edu.neu.PlantGrowthSimulation.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BGRuleView implements WindowListener {
	
	protected JFrame ruleFrame = null;
	protected JPanel mainPanel = null;
	protected JTextArea textArea = null;

	public BGRuleView() {
		initGUI();
	}
	
	public void initGUI() {
		System.out.println("View Rules");
		ruleFrame = new JFrame();
		ruleFrame.setSize(500, 400);
		ruleFrame.setTitle("Rules");

		ruleFrame.setResizable(true);
		
		// Permit the app to hear about the window opening
		ruleFrame.addWindowListener(this); 
		
		ruleFrame.setLayout(new BorderLayout());
		ruleFrame.add(getMainPanel(),BorderLayout.NORTH); 
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
	
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		textArea = new JTextArea();
		mainPanel.add(BorderLayout.SOUTH, textArea);
		return mainPanel;
	}
	
	public void writeText(String text) {
		textArea.append(text);
	}

}
