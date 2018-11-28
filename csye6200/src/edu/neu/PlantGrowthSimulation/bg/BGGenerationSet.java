package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Timer;

public class BGGenerationSet {

	ArrayList<String> plantNames;
	ArrayList<BGGeneration> plants;

	public BGGenerationSet(ArrayList<String> plantNames) {
		this.plantNames = plantNames;
	}

	public void createPlants() {
		plants = new ArrayList<BGGeneration>();
		int i = 100;
		for (String name : plantNames) {
			BGGeneration plant = new BGGeneration(name);
			BGStem stem = new BGStem(new int[] { i, 0 }, 5, 90);
			plant.setFirstGen(stem);
			plant.addToStemFamily(stem);
			plants.add(plant);
			plant.start();
			i += 100;
		}
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

}
