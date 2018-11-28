package edu.neu.PlantGrowthSimulation.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyAppUI implements ActionListener{

	private Logger log = Logger.getLogger(MyAppUI.class.getName());
	private JFrame frame = null;   // Window
	private JPanel mainPanel = null; // Panel - Drawable Region
	private JButton startBtn = null;
	private JButton stopBtn = null;
	
	// Constructor
	
	public MyAppUI(){
		log.info("App Started");
		initGUI();
	}
	
	
	private void initGUI() {
		frame = new JFrame();
		frame.setTitle("MyAppUI");
		frame.setSize(400,300);  // Set the size to something reasonable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If we press close button, exit
		frame.setVisible(true);

		frame.setLayout(new BorderLayout());
		//frame.setLayeredPane(new BorderLayout());
		
		frame.add(getMainPanel(),BorderLayout.CENTER);
	}
	
	// Returns a JPanel - a drawable region, that we'll draw into
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		mainPanel.add(startBtn);
		mainPanel.add(stopBtn);
		mainPanel.setBackground(Color.black);
		return mainPanel;
	}
	
	public static void main(String[] args) {
		
		MyAppUI myApp = new MyAppUI();
		System.out.println("MyAppUI is exiting");
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		log.info("We received an ActionEvent " + arg0);
		
	}
}
