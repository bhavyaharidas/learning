package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextArea;

import edu.neu.PlantGrowthSimulation.ui.BGApp;

public class PlantGrowthApp extends BGApp {

	public PlantGrowthApp(JTextArea textArea) {
		super(textArea);

	}

	ArrayList<BGRule> rules = new ArrayList<BGRule>();
	private BGGenerationSet plants;

	public void run() {
	}

	@Override
	public void startAction() {
		plants = new BGGenerationSet(new ArrayList<String>(Arrays.asList(textfield.getText().split(","))));
		plants.createPlants();
	}

	@Override
	public void stopAction() {
		plants.setDone(true);
	}

	public static void main(String[] args) {
		JTextArea textArea = new JTextArea();
		PlantGrowthApp app = new PlantGrowthApp(textArea);
	}

}
