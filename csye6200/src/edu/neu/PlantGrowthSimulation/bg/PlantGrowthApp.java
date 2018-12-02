package edu.neu.PlantGrowthSimulation.bg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import edu.neu.PlantGrowthSimulation.ui.BGApp;
import edu.neu.PlantGrowthSimulation.ui.BGCanvas;
import edu.neu.PlantGrowthSimulation.ui.BGRuleView;
import edu.neu.PlantGrowthSimulation.ui.BGStatusBar;
import edu.neu.PlantGrowthSimulation.ui.MenuManager;
import net.java.dev.designgridlayout.DesignGridLayout;

public class PlantGrowthApp extends BGApp {

	private static Logger log = Logger.getLogger(PlantGrowthApp.class.getName());
	private static int MAX_GENERATIONS = 15;

	private BGGenerationSet generations;
	private HashMap<Integer, BGRule> rules;
	private int ruleCount;
	private Thread growThread;

	protected JPanel mainPanel = null;
	protected JPanel optionsPanel = null;
	private BGCanvas bgPanel = null;
	private BGStatusBar statusPanel = null;
	protected JLabel title = null;
	protected JButton resetBtn = null;
	protected JButton createRuleBtn = null;
	protected JLabel ruleLabel = null;
	protected JLabel genLabel = null;
	protected JComboBox<String> ruleList;
	protected JComboBox<String> generationList;
	// protected final JButton startBtn = null;
	protected JButton pauseBtn = null;
	protected JButton viewRulesBtn = null;
	private int startLoc;

	private String[] genNumbers;
	private ArrayList<String> ruleNames;
	private String[] rNames;

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

	@Override
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		bgPanel = BGCanvas.instance();
		statusPanel = BGStatusBar.instance();
		statusPanel.setPreferredSize(new Dimension(bgPanel.getSize().width, 30));
		startLoc = bgPanel.getSize().width / 2;
		mainPanel.add(BorderLayout.CENTER, bgPanel);

		mainPanel.add(BorderLayout.WEST, getOptionsPanel());
		mainPanel.add(BorderLayout.SOUTH, statusPanel);

		return mainPanel;
	}

	public JPanel getOptionsPanel() {
		createDefaultRule();
		optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.BLACK);
		DesignGridLayout pLayout = new DesignGridLayout(optionsPanel);

		title = new JLabel("PLANT GROWTH APP");
		title.setForeground(Color.WHITE);

		createRuleBtn = new JButton("Create Rule");
		createRuleBtn.addActionListener(this);

		viewRulesBtn = new JButton("View Rules");
		viewRulesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewRules();
			}
		});

		ruleLabel = new JLabel("Rule");
		ruleLabel.setForeground(Color.WHITE);

		genLabel = new JLabel("Number of Generations");
		genLabel.setForeground(Color.WHITE);

		rNames = new String[rules.size()];
		int j = 0;
		for (String name : ruleNames) {
			rNames[j] = name;
			j++;
		}
		ruleList = new JComboBox<String>(rNames);

		genNumbers = new String[MAX_GENERATIONS];
		for (int i = 0; i < MAX_GENERATIONS; i++) {
			genNumbers[i] = Integer.toString(i + 1);
		}
		generationList = new JComboBox<String>(genNumbers);

		final JButton startBtn = new JButton("Start");
		startBtn.setEnabled(true);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null && growThread.isAlive()) {
					growThread.resume();
				} else {
					generations = new BGGenerationSet(rules.get(ruleList.getSelectedIndex()), new int[] { 50, 0 },
							Integer.parseInt((String) generationList.getSelectedItem()));
					generations.setRunning(true);
					growThread = new Thread(generations);
					growThread.start();
				}
				JButton source = (JButton) arg0.getSource();
				source.setText("Start");
				source.setEnabled(false);
			}
		});

		pauseBtn = new JButton("Pause"); // Allow the app to hear about button pushes
		// pauseBtn.setEnabled(false);
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (growThread != null) {
					growThread.suspend();
					startBtn.setText("Resume");
					startBtn.setEnabled(true);
				}
			}
		});

		resetBtn = new JButton("Reset"); // Allow the app to hear about button pushes
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

		pLayout.row().center().add(createRuleBtn, viewRulesBtn);
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

	public static void main(String[] args) {
		PlantGrowthApp pgApp = new PlantGrowthApp();
		log.info("PlantGrowthApp started");
	}

	private void createDefaultRule() {
		rules = new HashMap<Integer, BGRule>();
		ruleNames = new ArrayList<String>();
		HashMap<Double, Double[]> angleLookUp1 = new HashMap<Double, Double[]>();
		int lengthFactor1 = 10;
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
		angleLookUp2.put(30.0, new Double[] { 60.0, 120.0 });
		angleLookUp2.put(60.0, new Double[] { 30.0, 90.0, 150.0 });
		angleLookUp2.put(90.0, new Double[] { 30.0, 90.0, 150.0 });
		angleLookUp2.put(120.0, new Double[] { 30.0, 90.0, 150.0 });
		angleLookUp2.put(150.0, new Double[] { 60.0, 120.0 });
		BGRule rule2 = new BGRule("Rule 2", angleLookUp2, lengthFactor2);
		rules.put(ruleCount, rule2);
		ruleNames.add(rule2.getName());
	}

	private void viewRules() {
		BGRuleView rv = new BGRuleView();
		for (BGRule rule : rules.values()) {
			rv.writeText(rule.toString()+ "\n\n" );
		}
	}

}
