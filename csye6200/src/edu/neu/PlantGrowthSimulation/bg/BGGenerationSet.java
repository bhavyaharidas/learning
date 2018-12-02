package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;

import edu.neu.PlantGrowthSimulation.ui.BGCanvas;
import edu.neu.PlantGrowthSimulation.ui.BGStatusBar;

public class BGGenerationSet extends Observable implements Runnable {

	private BGRule rule;
	private static ArrayList<BGGeneration> generations;
	private BGCanvas canvasObserver = null;
	private BGStatusBar statusObserver = null;
	private boolean done = false;
	private boolean running = false;
	private int[] startPoint;
	private int genLimit;
	
	private static int BASE_STEM_LENGTH = 5;

	public BGGenerationSet(BGRule rule, int[] startPoint, int genLimit) {
		this.rule = rule;
		this.startPoint = startPoint;
		this.genLimit = genLimit;
		generations = new ArrayList<BGGeneration>();
		canvasObserver = BGCanvas.instance();
		statusObserver = BGStatusBar.instance();
		this.addObserver(canvasObserver); // make a subscription
		this.addObserver(statusObserver); 
	}

	public ArrayList<BGGeneration> getGenerations() {
		return generations;
	}

	public void addGeneration(BGGeneration generation) {

		generations.add(generation);
		setChanged(); // Indicate that a generation has been added
		notifyObservers(generation);
	}
	
	public void removeGenerations() {
		generations = null;
		setChanged();
		notifyObservers(0);
	}

	public void setDone(boolean done) {
		for (BGGeneration generation : generations) {
			generation.setDone(true);
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		if (generations.isEmpty()) {
			BGGeneration generation = new BGGeneration("Name", rule);
			BGStem stem = new BGStem(startPoint, BASE_STEM_LENGTH, 90);
			generation.setFirstGen(stem);
			generation.addToStemFamily(stem);
			for(int i = 0; i < genLimit ; i++) {
				generation.grow();
				// generation.printGeneration();
				addGeneration(generation);
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(done) {
				addGeneration(new BGGeneration("", rule));
			}
		}

	}

}
