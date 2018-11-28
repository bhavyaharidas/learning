package edu.neu.PlantGrowthSimulation.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.neu.csye6200.bg.CustomOutputStream;

public abstract class BGApp implements ActionListener {

	private JFrame frame = null; // Window
	private JPanel mainPanel = null; // Panel - Drawable Region
	private JButton startBtn = null;
	private JButton stopBtn = null;
	protected JTextField textfield = null;
	private JLabel label = null;
	private JScrollPane scrollPane;
	JTextArea textArea = new JTextArea();

	public BGApp(JTextArea textArea) {
		initGUI();
		this.textArea = textArea;
	}

	private void initGUI() {
		frame = new JFrame();
		frame.setTitle("BGAppUI");
		frame.setSize(400, 300); // Set the size to something reasonable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If we press close button, exit
		frame.setVisible(true);

		frame.setLayout(new BorderLayout());
		// frame.setLayeredPane(new BorderLayout());

		frame.add(getMainPanel(), BorderLayout.CENTER);
		scrollPane = new JScrollPane(mainPanel);
		//frame.add(scrollPane);
		
		
		BGPanel panel = new BGPanel();
		frame.add(panel);
	}

	// Returns a JPanel - a drawable region, that we'll draw into
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		
		textfield = new JTextField(); textfield.setColumns(10);
		textfield.setHorizontalAlignment(textfield.LEFT);
		 
		/*JTextArea textArea = new JTextArea(50, 10);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);*/
		
		label = new JLabel("Enter name of plant: ");
		label.setForeground(Color.white);
		
		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		mainPanel.add(label);
		mainPanel.add(textfield);
		mainPanel.add(startBtn);
		mainPanel.add(stopBtn);
		mainPanel.add(textArea);
		mainPanel.setBackground(Color.black);
		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Start")
			startAction();
		else if (e.getActionCommand() == "Stop")
			stopAction();
	}

	public void startAction() {
	}

	public void stopAction() {
	}

}
