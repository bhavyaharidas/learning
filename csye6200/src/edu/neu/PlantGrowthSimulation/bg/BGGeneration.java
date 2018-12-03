package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Collections;


public class BGGeneration {

	private static int idCount; // Counter that sets the id
	private int specimenId;
	private double totalLength = 0; // total length of a generation
	private double totalWidth = 0; // total width of a generation
	private static BGStem firstGen = new BGStem(); // base stem
	private ArrayList<BGStem> stemFamily; // a master list of all stems
	private BGRule rule; // rule that governs plant growth
	
	

	
	/**
	 * BG Generation Constructor
	 * @param rule - passes the BGRule instance that governs plant growth
	 * Specimen Id is incremented and assigned
	 */
	public BGGeneration(BGRule rule) {
		idCount++;
		this.specimenId = idCount;
		this.rule = rule;
		this.stemFamily = new ArrayList<BGStem>();
	}

	/**
	 * @return -base stem of the generation
	 */
	public static BGStem getFirstGen() {
		return firstGen;
	}

	public void setFirstGen(BGStem firstGen) {
		this.firstGen = firstGen;
	}

	/**
	 * @return - the master list of all stems in the generation
	 */
	public ArrayList<BGStem> getStemFamily() {
		return stemFamily;
	}

	/**
	 * @param stem - adds the stem to master stem list
	 */
	public void addToStemFamily(BGStem stem) {
		this.stemFamily.add(stem);
	}

	/**
	 * Grows the generation by 1 level
	 */
	public void grow() {
		growStem(firstGen); //Invoking method that recursively finds the end point and grows a new set of stems there
		calculateDimensions(); //Re-calculating length and width
	}

	/**
	 * @param Recursively finds the end stem and creates a new stem at that point.
	 */
	public void growStem(BGStem child) {
		if (child.hasChildren()) {
			for (BGStem stem : child.getChildStem()) {
				growStem(stem); //Calling itself since it is not end point yet
			}
		} else {
			createGeneration(child); // Invoking method to grow new set of stems
		}
	}

	/**
	 * Helper method to print details of generation on console
	 */
	public void printGeneration() {
		System.out.println(toString());
		System.out.println(printHeader());
		for (BGStem stem : stemFamily) {
			System.out.println(stem.toString());
		}
	}

	public String toString() {
		String p = "\n\nSpecimen Id = " + specimenId + "\nLength - " + totalLength
				+ "\nWidth - " + totalWidth + "\nTotal Stem count - " + stemFamily.size();

		return p;
	}

	/**
	 * @param stem - the parent stem on which child stems will be created. 
	 * Method makes use of the rule assigned to the Generation to assign angles and length to new child stems.
	 */
	private void createGeneration(BGStem stem) {

		//Calculating starting locations for new stems based on parent stem's location, angle and length
		int x = stem.getStartLoc()[0] + (int) (stem.getLength() * Math.cos(Math.toRadians(stem.getDirection()))); 
		int y = stem.getStartLoc()[1] + (int) (stem.getLength() * Math.sin(Math.toRadians(stem.getDirection())));
		int[] newStartLoc = new int[] { x, y }; // New start location

		//Invoking methods in the prescribed rule to get the length and angles of the new set of stems
		int length = rule.lengthLookup(totalLength, totalWidth, stem.getLength()); // New length
		Double[] angles = rule.getAngleLookUp().get(stem.getDirection()); // New set of angles

		//for each angle returned, grow stem
		for (double angle : angles) {
			BGStem newStem = new BGStem(newStartLoc, length, angle);
			stem.addChildStem(newStem);
			this.stemFamily.add(newStem);
		}
	}

	/**
	 * Helper method for console output
	 * @return Header for printing out to console
	 */
	private String printHeader() {
		return String.format("%0" + 105 + "d", 0).replace("0", "*")
				+ "\n" + String.format("%10s %2$13c %3$15s %4$13c %5$15s %6$13c %7$5s", "Stem Id", '|',
						"Start Location", '|', "Direction", '|', "Length")
				+ "\n" + String.format("%0" + 105 + "d", 0).replace("0", "*");
	}

	/**
	 * Calculates and stores the total height and width of the generation.
	 */
	private void calculateDimensions() {
		ArrayList<Integer> xcoords = new ArrayList<Integer>(); // to store all open x end points
		ArrayList<Integer> ycoords = new ArrayList<Integer>(); // to store all open y end points
		//Storing coordinates of all end point stems
		for (BGStem stem : stemFamily) {
			if (!stem.hasChildren()) {
				xcoords.add(stem.getStartLoc()[0] + (int) (stem.getLength() * Math.cos(stem.getDirection())));
				ycoords.add(stem.getStartLoc()[1] + (int) (stem.getLength() * Math.sin(stem.getDirection())));
			}
		}
		//Width = rightmost point - leftmost point
		//Length = largest y value from ground
		this.totalWidth = Collections.max(xcoords) - Collections.min(xcoords);
		this.totalLength = Collections.max(ycoords);
	}

}
