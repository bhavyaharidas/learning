package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;

import edu.neu.PlantGrowthSimulation.ui.BGCanvas;

public class BGGenerationSet extends Observable implements Runnable {

	private BGRule rule;
	private static ArrayList<BGGeneration> generations;
	private BGCanvas observer = null;
	private boolean done = false;
	private boolean running = false;
	private int[] startPoint;

	public BGGenerationSet(BGRule rule, int[] startPoint) {
		this.rule = rule;
		this.startPoint = startPoint;
		generations = new ArrayList<BGGeneration>();
		observer = BGCanvas.instance();
		this.addObserver(observer); // make a subscription
	}

	public ArrayList<BGGeneration> getGenerations() {
		return generations;
	}

	public void addGeneration(BGGeneration generation) {

		generations.add(generation);
		setChanged(); // Indicate that a generation has been added
		notifyObservers(generation);
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
			BGStem stem = new BGStem(startPoint, 5, 90);
			generation.setFirstGen(stem);
			generation.addToStemFamily(stem);
			while (!done) {
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
