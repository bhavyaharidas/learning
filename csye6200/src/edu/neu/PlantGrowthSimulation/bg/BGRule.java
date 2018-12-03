package edu.neu.PlantGrowthSimulation.bg;

import java.util.HashMap;

public class BGRule {

	private String name; // name of the rule
	private HashMap<Double, Double[]> angleLookUp; // defines a table with the list of expected child stem angles for
													// every parent stem angle
	private int lengthFactor; // factor representing constant percentage decrease ini length over each
								// generation

	/**
	 * BGRule Constructor
	 * 
	 * @param name         - passes name of rule
	 * @param angleLookUp  - passes the list that maps parent angle to list of child
	 *                     stem angles
	 * @param lengthFactor - passes the required % decrease in length over each
	 *                     generation
	 */
	public BGRule(String name, HashMap<Double, Double[]> angleLookUp, int lengthFactor) {
		this.name = name;
		this.angleLookUp = angleLookUp;
		this.lengthFactor = lengthFactor;
	}

	/**
	 * @return Name of the rule
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The list that maps parent angle to list of child stem angles
	 */
	public HashMap<Double, Double[]> getAngleLookUp() {
		return angleLookUp;
	}

	/**
	 * @param totalLength      - total length of generation
	 * @param totalWidth       - total width of generation
	 * @param parentStemLength - parent stem length
	 * @return the length of the child stems based on total length, total width and
	 *         length factor.
	 */
	public int lengthLookup(double totalLength, double totalWidth, int parentStemLength) {
		int length = 0;
		// Incrementally subtracts length factor * stem length for 4 sections fo height
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

	public String toString() {
		String rule;
		rule = this.name + "\nLength Factor - " + this.lengthFactor + "\n" + getHeader() + "\n" + getParameters();
		return rule;
	}

	/**
	 * Helper method for printing details of rule
	 * 
	 * @return Header for Rule details
	 */
	private String getHeader() {
		return String.format("%0" + 105 + "d", 0).replace("0", "-") + "\n"
				+ String.format("%30s %2$10c %3$50s", "Parent Stem Angle", '|', "Child stem angles") + "\n"
				+ String.format("%0" + 105 + "d", 0).replace("0", "-");
	}

	/**
	 * Helper method for printing details of rule
	 * 
	 * @return - the details of a rule instance in a formatted string
	 */
	private String getParameters() {
		String params = "";
		for (Double parentAngle : angleLookUp.keySet()) {
			params += String.format("%30.2f", parentAngle) + "		";
			for (Double angle : angleLookUp.get(parentAngle)) {
				params += angle.toString() + "   ";
			}
			params += "\n";
		}
		return params;
	}

}
