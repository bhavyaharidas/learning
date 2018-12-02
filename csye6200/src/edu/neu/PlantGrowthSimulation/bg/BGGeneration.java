package edu.neu.PlantGrowthSimulation.bg;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTextArea;

import edu.neu.csye6200.sim.Flower;
import edu.neu.csye6200.sim.Stem;

public class BGGeneration {

	private boolean done = false;

	private static int idCount;
	private String typeName;
	private int specimenId;
	private double totalLength = 0;
	private double totalWidth = 0;
	private static BGStem firstGen = new BGStem();
	private ArrayList<BGStem> stemFamily;
	private BGRule rule;

	public BGGeneration(String name, BGRule rule) {
		this.typeName = name;
		idCount++;
		specimenId = idCount;
		this.rule = rule;
		this.stemFamily = new ArrayList<BGStem>();
	}

	public static BGStem getFirstGen() {
		return firstGen;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setFirstGen(BGStem firstGen) {
		this.firstGen = firstGen;
	}

	public ArrayList<BGStem> getStemFamily() {
		return stemFamily;
	}

	public void addToStemFamily(BGStem stem) {
		this.stemFamily.add(stem);
	}

	public void grow() {
		growStem(firstGen);
		calculateDimensions();
	}

	public void growStem(BGStem child) {
		if (child.hasChildren()) {
			for (BGStem stem : child.getChildStem()) {
				growStem(stem);
			}
		} else {
			createGeneration(child);
		}
	}

	public void printGeneration() {
		System.out.println(toString());
		System.out.println(printHeader());
		for (BGStem stem : stemFamily) {
			System.out.println(stem.toString());
		}
	}

	public String toString() {
		String p = "\n\nName - " + typeName + "\nSpecimen Id = " + specimenId + "\nLength - " + totalLength
				+ "\nWidth - " + totalWidth + "\nTotal Stem count - " + stemFamily.size();

		return p;
	}

	private void createGeneration(BGStem stem) {

		int x = stem.getStartLoc()[0] + (int) (stem.getLength() * Math.cos(Math.toRadians(stem.getDirection())));
		int y = stem.getStartLoc()[1] + (int) (stem.getLength() * Math.sin(Math.toRadians(stem.getDirection())));
		int[] newStartLoc = new int[] { x, y }; // New start location

		int length = rule.lengthLookup(totalLength, totalWidth, stem.getLength()); // New length
		Double[] angles = rule.getAngleLookUp().get(stem.getDirection()); // New set of angles

		for (double angle : angles) {
			BGStem newStem = new BGStem(newStartLoc, length, angle);
			stem.addChildStem(newStem);
			this.stemFamily.add(newStem);
		}
	}

	private String printHeader() {
		return String.format("%0" + 105 + "d", 0).replace("0", "*")
				+ "\n" + String.format("%10s %2$13c %3$15s %4$13c %5$15s %6$13c %7$5s", "Stem Id", '|',
						"Start Location", '|', "Direction", '|', "Length")
				+ "\n" + String.format("%0" + 105 + "d", 0).replace("0", "*");
	}

	private void calculateDimensions() {
		ArrayList<Integer> xcoords = new ArrayList<Integer>(); // to store all open x end points
		ArrayList<Integer> ycoords = new ArrayList<Integer>(); // to store all open y end points
		for (BGStem stem : stemFamily) {
			if (!stem.hasChildren()) {
				xcoords.add(stem.getStartLoc()[0] + (int) (stem.getLength() * Math.cos(stem.getDirection())));
				ycoords.add(stem.getStartLoc()[1] + (int) (stem.getLength() * Math.sin(stem.getDirection())));
			}
		}
		this.totalWidth = Collections.max(xcoords) - Collections.min(xcoords);
		this.totalLength = Collections.max(ycoords);
	}

}
