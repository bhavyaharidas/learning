package edu.neu.PlantGrowthSimulation.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import edu.neu.PlantGrowthSimulation.bg.BGGeneration;

public class BGStatusBar extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static BGStatusBar instance = null; // The single copy

	private int widthCounter;
	private int totalGens = 15;

	public static BGStatusBar instance() {
		if (instance == null)
			instance = new BGStatusBar(); // Build if needed
		return instance; // Return the single copy
	}

	/**
	 * The UI thread calls this method when the screen changes, or in response to a
	 * user initiated call to repaint();
	 */
	public void paint(Graphics g) {
		drawBG(g); // Our Added-on drawing
	}

	/**
	 * Paints the rectangle indicating the progress of growth
	 * 
	 * @param g
	 */
	public void drawBG(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();

		int width = (size.width / totalGens) * widthCounter;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, size.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * Invoked upon notification from observable class BGGeneration
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Integer) {
			widthCounter = 0;
		} else {
			widthCounter++;
		}
		this.repaint();
	}

}
