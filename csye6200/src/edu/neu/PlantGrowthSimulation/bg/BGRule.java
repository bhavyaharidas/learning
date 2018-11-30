package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.HashMap;

public class BGRule {

	private String name;
	private HashMap<Double, Double[]> angleLookUp;

	public HashMap<Double, Double[]> getAngleLookUp() {
		return angleLookUp;
	}

	public BGRule(String name, HashMap<Double, Double[]> angleLookUp) {
		this.name = name;
		this.angleLookUp = angleLookUp;
	}

	public String getName() {
		return name;
	}

	public int lengthLookup(double totalLength, double totalWidth) {
		int length = 0;
		if (totalLength <= 15 || totalWidth <= 30)
			length = 5;
		else if ((totalLength > 15 && totalLength <= 17) || (totalWidth > 30 && totalWidth <= 40))
			length = 3;
		else if ((totalLength > 17 && totalLength <= 30) || (totalWidth > 40 && totalWidth <= 60))
			length = 1;
		else if (totalLength > 50 || totalWidth > 60)
			length = 0;
		return length;
	}

	public boolean timeToFlower(int generation) {
		return (generation % 2 == 0);
	}
	
	private String printHeader() {
		return String.format("%0" + 105 + "d", 0).replace("0", "-")
				+ "\n" + String.format("%10s %2$13c %3$15s %4$13c %5$15s %6$13c %7$5s", "Stem Id", '|',
						"Start Location", '|', "Direction", '|', "Length")
				+ "\n" + String.format("%0" + 105 + "d", 0).replace("0", "*");
	}

}
