package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Logger;

import edu.neu.PlantGrowthSimulation.ui.BGCanvas;
import edu.neu.PlantGrowthSimulation.ui.BGStatusBar;

public class BGGenerationSet extends Observable implements Runnable {

	private BGRule rule; //holds the rule that governs plant growth
	private static ArrayList<BGGeneration> generations; // a collections of all generations over time
	private BGCanvas canvasObserver = null; //singleton instance of BGCanvas
	private BGStatusBar statusObserver = null; //singleton instance of status bar
	private int[] startPoint; // Starting location of base stem
	private int genLimit; // Number of generations selected by the user
	
	private static Logger log = Logger.getLogger(BGGenerationSet.class.getName());
	
	private static int BASE_STEM_LENGTH = 5;

	/**
	 * @param rule - passes the BGRule instance selected by the user, that governs plant growth
	 * @param startPoint - passes the location at which the plant has to be grown.
	 * @param genLimit - passes the number of generations to be grown, selected by the user
	 */
	public BGGenerationSet(BGRule rule, int[] startPoint, int genLimit) {
		this.rule = rule;
		this.startPoint = startPoint;
		this.genLimit = genLimit;
		generations = new ArrayList<BGGeneration>();
		canvasObserver = BGCanvas.instance(); //getting the unique instance
		statusObserver = BGStatusBar.instance();
		this.addObserver(canvasObserver); // make a subscription
		this.addObserver(statusObserver); 
	}

	/**
	 * @return the list of all generations over time
	 */
	public ArrayList<BGGeneration> getGenerations() {
		return generations;
	}

	/**
	 * @param generation - adds a generation to the list of all generations and
	 * notifies all its observers about it.
	 */
	public void addGeneration(BGGeneration generation) {

		generations.add(generation);
		log.info("Added generation, notifying observers");
		setChanged(); // Indicate that a generation has been added
		notifyObservers(generation);
	}
	
	/**
	 * Removes all generations from the list of generations and 
	 * notifies all its observers.
	 */
	public void removeGenerations() {
		generations = null;
		log.info("Resetting generation set");
		setChanged();
		notifyObservers(0); // Notifies the observers upon reset event by sending integer 0.
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Overridden method of Runnable 
	 */
	@Override
	public void run() {
		//Method invokes procedures to create generation upto the number of generations specifies by the user
		if (generations.isEmpty()) { 
			BGGeneration generation = new BGGeneration(rule);
			BGStem stem = new BGStem(startPoint, BASE_STEM_LENGTH, 90); // base stem
			generation.setFirstGen(stem); 
			log.info("Created base stem");
			generation.addToStemFamily(stem);
			for(int i = 0; i < genLimit ; i++) {
				generation.grow(); // Invoking method to grow subsequent stems.
				//generation.printGeneration();
				addGeneration(generation);
				log.info("Created generation number - " + i + 1);
				try {
					Thread.sleep(3000L); // Delay growth by 3 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
