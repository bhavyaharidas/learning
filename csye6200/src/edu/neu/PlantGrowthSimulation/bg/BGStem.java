package edu.neu.PlantGrowthSimulation.bg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A source code implementing Assignment 2 (b) of CSYE 6200 Filename: Stem.java
 * NUID: 001470055
 * 
 * @author - Bhavya Haridas
 */

public class BGStem {

	private static int idCount; // Counter that sets id

	private int id;
	private int[] startLoc; // Starting coordinates of stem
	private int length; // Length of stem
	private double direction; //angle

	private ArrayList<BGStem> childStem; //list of immediate list of child stems

	/**
	 * @param startLoc  - passes starting location of the stem
	 * @param length    - passes length of stem
	 * @param direction - passes the angle in degree in which the stem is to be
	 *                  grown.
	 */
	public BGStem(int[] startLoc, int length, double direction) {
		idCount++;
		this.id = idCount;
		this.startLoc = startLoc;
		this.length = length;
		this.direction = direction;
	}

	public BGStem() {

	}

	/**
	 * @return array denoting start location of stem
	 */
	public int[] getStartLoc() {
		return startLoc;
	}

	/**
	 * @return Length of stem
	 */
	public int getLength() {
		return length;
	}

	public double getDirection() {
		return direction;
	}

	/**
	 * @return - list of immediate child stems of the stem
	 */
	public List<BGStem> getChildStem() {
		return childStem;
	}

	/**
	 * @param adds childStem to the list of childstems
	 */
	public void addChildStem(BGStem childStem) {
		if (this.childStem == null) {
			this.childStem = new ArrayList<BGStem>();
		}
		this.childStem.add(childStem);
	}

	/**
	 * @return a boolean indicating if the stem is an end point
	 */
	public Boolean hasChildren() {
		return this.childStem != null;
	}

	public String toString() {
		return String.format("%10d %2$13c %3$15s %4$13c %5$15.2f %6$13c %7$5d", id, '|', Arrays.toString(startLoc), '|',
				direction, '|', length);
	}

}
