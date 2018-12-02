package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.HashMap;

public class BGRule {

	private String name;
	private HashMap<Double, Double[]> angleLookUp;
	private int lengthFactor;

	public HashMap<Double, Double[]> getAngleLookUp() {
		return angleLookUp;
	}

	public BGRule(String name, HashMap<Double, Double[]> angleLookUp, int lengthFactor) {
		this.name = name;
		this.angleLookUp = angleLookUp;
		this.lengthFactor = lengthFactor;
	}

	public String getName() {
		return name;
	}
	
	/*public int lengthLookup(int parentStemLength) {
		return parentStemLength - (parentStemLength * lengthFactor) / 100;
	}*/

	public int lengthLookup(double totalLength, double totalWidth, int parentStemLength) {
		int length = 0;
		if (totalLength <= 15 || totalWidth <= 30)
			length = parentStemLength - (parentStemLength * lengthFactor) / 100;
		else if ((totalLength > 15 && totalLength <= 17) || (totalWidth > 30 && totalWidth <= 40))
			length = parentStemLength - (parentStemLength * lengthFactor * 2) / 100;
		else if ((totalLength > 17 && totalLength <= 30) || (totalWidth > 40 && totalWidth <= 60))
			length = parentStemLength - (parentStemLength * lengthFactor * 3) / 100;
		else if (totalLength > 50 || totalWidth > 60)
			length = 0;
		return length;
	}

	public boolean timeToFlower(int generation) {
		return (generation % 2 == 0);
	}
	
	public String toString() {
		String rule;
		rule = this.name + "\nLength Factor - " + this.lengthFactor + "\n" + getHeader() + "\n" + getParameters();
		return rule;
	}
	
	private String getHeader() {
		return String.format("%0" + 105 + "d", 0).replace("0", "-")
				+ "\n" + String.format("%30s %2$10c %3$50s", "Parent Stem Angle", '|',
						"Child stem angles")
				+ "\n" + String.format("%0" + 105 + "d", 0).replace("0", "-");
	}
	
	private String getParameters(){
		String params = "";
		for(Double parentAngle : angleLookUp.keySet()) {
			params += String.format("%30.2f", parentAngle) + "		";
			for(Double angle : angleLookUp.get(parentAngle)) {
				params += angle.toString() + "   ";
			}
			params += "\n";
		}
		return params;
	}

}
