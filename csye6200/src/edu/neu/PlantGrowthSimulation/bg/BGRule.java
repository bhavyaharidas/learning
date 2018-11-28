package edu.neu.PlantGrowthSimulation.bg;

import java.util.HashMap;

public class BGRule {

	private HashMap<Double, Double[]> angleLookUp;

	public HashMap<Double, Double[]> getAngleLookUp() {
		return angleLookUp;
	}

	public BGRule() {
		angleLookUp = new HashMap<Double, Double[]>();
		angleLookUp.put(0.0, new Double[] { 45.0 });
		angleLookUp.put(45.0, new Double[] { 0.0, 45.0, 90.0 });
		angleLookUp.put(90.0, new Double[] { 45.0, 90.0, 135.0 });
		angleLookUp.put(135.0, new Double[] { 90.0, 135.0, 180.0 });
		angleLookUp.put(180.0, new Double[] { 135.0 });
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

}
