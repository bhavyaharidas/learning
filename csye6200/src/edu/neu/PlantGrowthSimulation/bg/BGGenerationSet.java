package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;

import edu.neu.PlantGrowthSimulation.ui.BGCanvas;

public class BGGenerationSet extends Observable implements Runnable {

	private ArrayList<BGGeneration> plants;
	private BGCanvas observer = null;

	public BGGenerationSet() {
		plants = new ArrayList<BGGeneration>();
		observer = BGCanvas.instance();
		this.addObserver(observer);     // make a subscription
	}

	
	public ArrayList<BGGeneration> getPlants() {
		return plants;
	}

	public void addGeneration(BGGeneration generation) {
		
		plants.add(generation);
		setChanged(); // Indicate that a generation has been added
		notifyObservers(generation);
	}



	public void createPlants() {
		int i = 100;
			BGGeneration plant = new BGGeneration("Name");
			BGStem stem = new BGStem(new int[] { i, 0 }, 5, 90);
			plant.setFirstGen(stem);
			plant.addToStemFamily(stem);
			plants.add(plant);
			plant.start();
			i += 100;
	}

	public void setDone(boolean done) {
		for(BGGeneration plant : plants) {
			plant.setDone(true);		
		}
	}
	/*
	 * public void growPlants() { if(plants != null) { for(BGGeneration plant :
	 * plants) { plant.grow(); } } }
	 * 
	 * public void printPlants() { if(plants != null) { for(BGGeneration plant :
	 * plants) { plant.printGeneration(); } }
	 */


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
