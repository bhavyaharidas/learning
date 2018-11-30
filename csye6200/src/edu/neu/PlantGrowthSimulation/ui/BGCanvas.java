package edu.neu.PlantGrowthSimulation.ui;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import edu.neu.PlantGrowthSimulation.bg.BGGeneration;
import edu.neu.PlantGrowthSimulation.bg.BGStem;
import edu.neu.csye6200.inherit.MeterManager;

/**
 * A sample canvas that draws a rainbow of lines
 * 
 * @author MMUNSON
 */
public class BGCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(BGCanvas.class.getName());
	private int lineSize = 20;
	private Color col = null;
	private int counter = 0;
	private String msg = "Hi";
	private BGStem baseStem = null;
	private int referenceX = 0;
	private int referenceY = 600;
	private boolean reset = false;

	private static BGCanvas instance = null; // The single copy

	/**
	 * CellAutCanvas constructor
	 */
	private BGCanvas() {
		col = Color.WHITE;
	}

	public static BGCanvas instance() {
		if (instance == null)
			instance = new BGCanvas(); // Build if needed
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
	 * Draw the CA graphics panel
	 * 
	 * @param g
	 */
	public void drawBG(Graphics g) {
		log.info("Drawing BG " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, size.width, size.height);

		referenceX = size.width / 2;
		referenceY = 600;

		g2d.setColor(Color.RED);
		g2d.drawString(msg, 10, 15);

		/*
		 * int maxRows = size.height / lineSize; int maxCols = size.width / lineSize;
		 * for (int j = 0; j < maxRows; j++) { for (int i = 0; i < maxCols; i++) { int
		 * redVal = validColor(i*5); int greenVal = validColor(255-j*5); int blueVal =
		 * validColor((j*5)-(i*2)); col = new Color(redVal, greenVal, blueVal);
		 */
		// Draw box, one pixel less to create a black outline
		int startx = 0;
		int starty = 600;
		int endx = size.width;
		int endy = 600;
		paintLine(g2d, startx, starty, endx, endy, Color.GREEN);
		if (baseStem != null && !reset) {
			paintLine(g2d, 10 * baseStem.getStartLoc()[0], referenceY - baseStem.getStartLoc()[1], 10 * baseStem.getStartLoc()[0], referenceY - (baseStem.getLength() * 10), Color.GREEN);
			drawGeneration(baseStem,g2d);
		}
		/*
		 * } }
		 */
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	private void drawGeneration(BGStem stem, Graphics2D g2d) {
		if(stem.hasChildren()) {
			for (BGStem child : stem.getChildStem()) {
				int endx = 10 * child.getStartLoc()[0] + (int) (child.getLength()*10 * Math.cos(Math.toRadians(child.getDirection())));
				int endy = referenceY - (10 * child.getStartLoc()[1] + (int) (child.getLength()*10 * Math.sin(Math.toRadians(child.getDirection()))));
				paintLine(g2d, 10 * child.getStartLoc()[0], referenceY - child.getStartLoc()[1] * 10,endx , endy, Color.GREEN);
				drawGeneration(child, g2d);
			}
		}
	}


	/*
	 * A local routine to ensure that the color value is in the 0 to 255 range.
	 */
	private int validColor(int colorVal) {
		if (colorVal > 255)
			colorVal = 255;
		if (colorVal < 0)
			colorVal = 0;
		return colorVal;
	}

	/**
	 * A convenience routine to set the color and draw a line
	 * 
	 * @param g2d    the 2D Graphics context
	 * @param startx the line start position on the x-Axis
	 * @param starty the line start position on the y-Axis
	 * @param endx   the line end position on the x-Axis
	 * @param endy   the line end position on the y-Axis
	 * @param color  the line color
	 */
	private void paintLine(Graphics2D g2d, int startx, int starty, int endx, int endy, Color color) {
		g2d.setColor(color);
		g2d.drawLine(startx, starty, endx, endy);
	}

	@Override
	public void update(Observable arg0, Object generation) {
		BGGeneration gen;
		gen = (BGGeneration) generation;
		baseStem = gen.getFirstGen();
		this.revalidate();
		this.repaint();
	}

}
