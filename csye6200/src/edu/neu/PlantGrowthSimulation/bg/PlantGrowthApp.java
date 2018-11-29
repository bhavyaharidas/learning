package edu.neu.PlantGrowthSimulation.bg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.neu.PlantGrowthSimulation.ui.BGApp;
import edu.neu.PlantGrowthSimulation.ui.BGCanvas;
import net.java.dev.designgridlayout.DesignGridLayout;

public class PlantGrowthApp extends BGApp {
	
	private static Logger log = Logger.getLogger(PlantGrowthApp.class.getName());
	private static int MAX_GENERATIONS = 15;
	
	protected JPanel mainPanel = null;
	protected JPanel optionsPanel = null;
	protected JLabel title = null;
	protected JButton resetBtn = null;
	protected JButton createRuleBtn = null;
	protected JLabel ruleLabel = null;
	protected JLabel genLabel = null;
	protected JComboBox<String> ruleList = null;
	protected JComboBox<String> generationList = null;
	protected JButton startBtn = null;
	protected JButton pauseBtn = null;
	protected JButton viewRulesBtn = null;
	private BGCanvas bgPanel = null;
	
	private String[] generations;

	public PlantGrowthApp() {
		frame.setSize(500, 400); // initial Frame size
		frame.setTitle("Plant Growth App");
		
		menuMgr.createDefaultActions(); // Set up default menu items
		
    	showUI(); // Cause the Swing Dispatch thread to display the JFrame
	}

	ArrayList<BGRule> rules = new ArrayList<BGRule>();
	private BGGenerationSet plants;

	public void run() {
	}

	/*@Override
	public void startAction() {
		plants = new BGGenerationSet(new ArrayList<String>(Arrays.asList(textfield.getText().split(","))));
		plants.createPlants();
	}

	@Override
	public void stopAction() {
		plants.setDone(true);
	}*/

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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

	@Override
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	mainPanel.add(BorderLayout.WEST, getOptionsPanel());
    	
    	bgPanel = BGCanvas.instance();
    	mainPanel.add(BorderLayout.CENTER, bgPanel);
    	
    	return mainPanel;
	}
	
	public JPanel getOptionsPanel() {
		optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.BLACK);
		DesignGridLayout pLayout = new DesignGridLayout(optionsPanel);
    	
    	title = new JLabel("PLANT GROWTH APP");
    	title.setForeground(Color.WHITE);
    	
    	createRuleBtn = new JButton("Create Rule");
    	createRuleBtn.addActionListener(this);
    	
    	viewRulesBtn = new JButton("View Rules");
    	viewRulesBtn.addActionListener(this);
    	
    	ruleLabel = new JLabel("Rule");
    	ruleLabel.setForeground(Color.WHITE);
    	
    	genLabel = new JLabel("Number of Generations");
    	genLabel.setForeground(Color.WHITE);
    	
    	ruleList = new JComboBox<String>();
    	generations = new String[MAX_GENERATIONS];
    	for(int i = 0; i < MAX_GENERATIONS ; i ++) {
    		generations[i] = Integer.toString(i + 1);
    	}
    	generationList = new JComboBox<String>(generations);
    	
    	startBtn = new JButton("Start");
    	startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> names = new ArrayList<String>();
				names.add("Cherry");
				plants = new BGGenerationSet();
				plants.createPlants();
			}
		});
    	
    	pauseBtn = new JButton("Pause"); // Allow the app to hear about button pushes
    	pauseBtn.addActionListener(this);
    	pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				plants.setDone(true);
			}
		});
    	
    	resetBtn = new JButton("Reset"); // Allow the app to hear about button pushes
    	resetBtn.addActionListener(this);
    	
    	pLayout.row().center().add(title);
		for(int i = 0 ; i < 5; i++) {
			pLayout.row().center().add(new JLabel(""));  // Adding empty rows using empty JLabel
		}
		
		pLayout.row().center().add(createRuleBtn,viewRulesBtn);
		for(int i = 0 ; i < 5; i++) {
			pLayout.row().center().add(new JLabel(""));
		}
		
		pLayout.row().center().add(ruleLabel,genLabel);
		pLayout.row().center().add(ruleList, generationList);
		for(int i = 0 ; i < 25; i++) {
			pLayout.row().center().add(new JLabel(""));
		}
		
		pLayout.row().center().add(startBtn);
		pLayout.emptyRow();
		pLayout.row().center().add(pauseBtn);
		pLayout.emptyRow();
		pLayout.row().center().add(resetBtn);

    	return optionsPanel;
    }
	
	public static void main(String[] args) {
		PlantGrowthApp pgApp = new PlantGrowthApp();
		log.info("PlantGrowthApp started");
	}

}
