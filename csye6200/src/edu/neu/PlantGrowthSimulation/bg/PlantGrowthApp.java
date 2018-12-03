package edu.neu.PlantGrowthSimulation.bg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.neu.PlantGrowthSimulation.ui.AboutDialog;
import edu.neu.PlantGrowthSimulation.ui.BGApp;
import edu.neu.PlantGrowthSimulation.ui.BGCanvas;
import edu.neu.PlantGrowthSimulation.ui.BGRuleView;
import edu.neu.PlantGrowthSimulation.ui.BGStatusBar;
import net.java.dev.designgridlayout.DesignGridLayout;

public class PlantGrowthApp extends BGApp {

	private static Logger log = Logger.getLogger(PlantGrowthApp.class.getName());
	private static int MAX_GENERATIONS = 15;

	private BGGenerationSet generations; 
	private HashMap<Integer, BGRule> rules;
	private int ruleCount; //counts number of rules
	private Thread growThread; //simulation thread
	
	private String[] genNumbers; //numbers in generation combo box
	private ArrayList<String> ruleNames; // rule names in rule selection combo box
	private String[] rNames;

	//Panels on the frame
	protected JPanel mainPanel = null;
	protected JPanel optionsPanel = null;
	private BGCanvas bgPanel = null;
	private BGStatusBar statusPanel = null;
	
	//Components on options panel
	protected JLabel title = null;
	protected JButton resetBtn = null;
	protected JLabel ruleLabel = null;
	protected JLabel genLabel = null;
	protected JComboBox<String> ruleList;
	protected JComboBox<String> generationList;
	protected JButton pauseBtn = null;
	protected JButton viewRulesBtn = null;

	public PlantGrowthApp() {
		frame.setSize(500, 400); // initial Frame size
		frame.setTitle("Plant Growth App");

		menuMgr.createDefaultActions(); // Set up default menu items

		showUI(); // Cause the Swing Dispatch thread to display the JFrame
	}

	public void run() {
	}

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

	/* (non-Javadoc)
	 * @see edu.neu.PlantGrowthSimulation.ui.BGApp#getMainPanel()
	 * Returns the main panel that contains - options panel, canvas and status bar
	 */
	@Override
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		//Canvas where plant growth is displayed
		bgPanel = BGCanvas.instance();
		Border raisedbevel = BorderFactory.createLineBorder(Color.WHITE);
		Border loweredbevel = BorderFactory.createLineBorder(Color.GRAY);
		bgPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));

		// Panel wherer status will be displayed
		statusPanel = BGStatusBar.instance();
		statusPanel.setPreferredSize(new Dimension(bgPanel.getSize().width, 30));
		
		mainPanel.add(BorderLayout.CENTER, bgPanel);
		mainPanel.add(BorderLayout.WEST, getOptionsPanel());
		mainPanel.add(BorderLayout.SOUTH, statusPanel);

		return mainPanel;
	}

	/**
	 * @return - options panel
	 */
	public JPanel getOptionsPanel() {
		createDefaultRule(); // creating the 2 default rules
		
		//defining panel for option selection
		optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.BLACK);
		Border raisedbevel = BorderFactory.createLineBorder(Color.WHITE);
		Border loweredbevel = BorderFactory.createLineBorder(Color.GRAY);

		optionsPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		DesignGridLayout pLayout = new DesignGridLayout(optionsPanel);

		//Adding components to the options panel
		title = new JLabel("PLANT GROWTH APP"); //title
		title.setForeground(Color.WHITE);

		viewRulesBtn = new JButton("View Rules"); //view rules button
		viewRulesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewRules();
			}
		});

		ruleLabel = new JLabel("Rule"); // label indicating rule selection
		ruleLabel.setForeground(Color.WHITE);

		genLabel = new JLabel("Number of Generations"); //label indicating #generation selection
		genLabel.setForeground(Color.WHITE);

		rNames = new String[rules.size()];
		int j = 0;
		for (String name : ruleNames) {
			rNames[j] = name;
			j++;
		}
		ruleList = new JComboBox<String>(rNames); //Combo box to select rule

		genNumbers = new String[MAX_GENERATIONS];
		for (int i = 0; i < MAX_GENERATIONS; i++) {
			genNumbers[i] = Integer.toString(i + 1);
		}
		generationList = new JComboBox<String>(genNumbers); // combo box to select generation limit

		final JButton startBtn = new JButton("Start"); // start button declared final to control from action events
		startBtn.setEnabled(true);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null && growThread.isAlive()) {
					growThread.resume();
				} else {
					generations = new BGGenerationSet(rules.get(ruleList.getSelectedIndex()), new int[] { 50, 0 },
							Integer.parseInt((String) generationList.getSelectedItem()));
					growThread = new Thread(generations);
					growThread.start();
				}
				JButton source = (JButton) arg0.getSource();
				source.setText("Start");
				source.setEnabled(false);
			}
		});

		pauseBtn = new JButton("Pause"); // Pause button
		// pauseBtn.setEnabled(false);
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null) {
					growThread.suspend();
					startBtn.setText("Resume"); // change start button text to "Resume"
					startBtn.setEnabled(true);
				}
			}
		});

		resetBtn = new JButton("Reset"); // reset button
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null) {
					growThread.stop();
					growThread = null;
					generations.removeGenerations();
					startBtn.setEnabled(true);
				}
			}
		});

		pLayout.row().center().add(title);
		for (int i = 0; i < 5; i++) {
			pLayout.row().center().add(new JLabel("")); // Adding empty rows using empty JLabel
		}

		pLayout.row().center().add(viewRulesBtn);
		for (int i = 0; i < 5; i++) {
			pLayout.row().center().add(new JLabel(""));
		}

		pLayout.row().center().add(ruleLabel, genLabel);
		pLayout.row().center().add(ruleList, generationList);
		for (int i = 0; i < 25; i++) {
			pLayout.row().center().add(new JLabel(""));
		}

		pLayout.row().center().add(startBtn);
		pLayout.emptyRow();
		pLayout.row().center().add(pauseBtn);
		pLayout.emptyRow();
		pLayout.row().center().add(resetBtn);

		return optionsPanel;
	}

	/* (non-Javadoc)
	 * @see edu.neu.PlantGrowthSimulation.ui.BGApp#showHelp()
	 * instantiates AboutDialog() frame 
	 */
	@Override
	public void showHelp() {
		new AboutDialog();
	}

	public static void main(String[] args) {
		PlantGrowthApp pgApp = new PlantGrowthApp();
		log.info("PlantGrowthApp started");
	}

	/**
	 * Helper method to create the 2 default rules and store them in rules HashMap.
	 */
	private void createDefaultRule() {
		rules = new HashMap<Integer, BGRule>();
		ruleNames = new ArrayList<String>();
		HashMap<Double, Double[]> angleLookUp1 = new HashMap<Double, Double[]>();
		int lengthFactor1 = 10; //constant length factor
		
		//inserting table values - key: parent angle, value: list of all possible child stem angles
		angleLookUp1.put(0.0, new Double[] { 45.0 });
		angleLookUp1.put(45.0, new Double[] { 0.0, 45.0, 90.0 });
		angleLookUp1.put(90.0, new Double[] { 45.0, 90.0, 135.0 });
		angleLookUp1.put(135.0, new Double[] { 90.0, 135.0, 180.0 });
		angleLookUp1.put(180.0, new Double[] { 135.0 });
		BGRule rule1 = new BGRule("Rule 1", angleLookUp1, lengthFactor1);
		rules.put(ruleCount, rule1);
		ruleNames.add(rule1.getName());
		
		ruleCount++;
		HashMap<Double, Double[]> angleLookUp2 = new HashMap<Double, Double[]>();
		int lengthFactor2 = 10;
		
		//inserting table values - key: parent angle, value: list of all possible child stem angles
		angleLookUp2.put(30.0, new Double[] { 60.0, 120.0 });
		angleLookUp2.put(60.0, new Double[] { 30.0, 90.0, 150.0 });
		angleLookUp2.put(90.0, new Double[] { 30.0, 90.0, 150.0 });
		angleLookUp2.put(120.0, new Double[] { 30.0, 90.0, 150.0 });
		angleLookUp2.put(150.0, new Double[] { 60.0, 120.0 });
		BGRule rule2 = new BGRule("Rule 2", angleLookUp2, lengthFactor2);
		rules.put(ruleCount, rule2);
		ruleNames.add(rule2.getName());
	}

	/**
	 * Method invoked by "View Rules" button click event
	 */
	private void viewRules() {
		BGRuleView ruleViewWindow = new BGRuleView();
		for (BGRule rule : rules.values()) {
			ruleViewWindow.writeText(rule.toString() + "\n\n"); // writes to the text area of BGRuleView
		}
	}
}
