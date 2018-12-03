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
 * @author bhavya
 */
public class BGCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(BGCanvas.class.getName());
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
		log.info("Drawing Canvas " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();

		g2d.setColor(Color.BLACK); // set background to black
		g2d.fillRect(0, 0, size.width, size.height);

		referenceX = size.width / 2;
		referenceY = 600; // defining ground level

		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, referenceY, size.width, size.height);

		if (baseStem != null && !reset) {
			paintLine(g2d, 10 * baseStem.getStartLoc()[0], referenceY - baseStem.getStartLoc()[1],
					10 * baseStem.getStartLoc()[0], referenceY - (baseStem.getLength() * 10), Color.GREEN);
			drawGeneration(baseStem, g2d);
		} else if (reset) { // reset button set to true upon pressing button
			reset = false;
			baseStem = null;
			repaint();
		}
	}

	/**
	 * @param reset - sets the reset boolean
	 */
	public void setReset(boolean reset) {
		this.reset = reset;
	}

	/**
	 * Convenience method that paints a stem. Recursively traverses through each
	 * stem till it reaches an end.
	 * 
	 * @param stem - stem to be painted
	 * @param g2d  - graphics object
	 */
	private void drawGeneration(BGStem stem, Graphics2D g2d) {
		if (stem.hasChildren()) {
			for (BGStem child : stem.getChildStem()) {
				// calculating x coord to plot on canvas by multiplying each parameter with 10
				int endx = 10 * child.getStartLoc()[0]
						+ (int) (child.getLength() * 10 * Math.cos(Math.toRadians(child.getDirection())));
				// calculating y coord to plot on canvas by multiplying each parameter with 10
				// and subtracting it from ground level.
				int endy = referenceY - (10 * child.getStartLoc()[1]
						+ (int) (child.getLength() * 10 * Math.sin(Math.toRadians(child.getDirection()))));
				paintLine(g2d, 10 * child.getStartLoc()[0], referenceY - child.getStartLoc()[1] * 10, endx, endy,
						Color.GREEN);
				drawGeneration(child, g2d); // since it has children, repeatt the same process again.
			}
		}
	}

	/**
	 * Paints a line in specified color from the start to end point
	 * 
	 * @param g2d
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @param color
	 */
	private void paintLine(Graphics2D g2d, int startx, int starty, int endx, int endy, Color color) {
		g2d.setColor(color);
		g2d.drawLine(startx, starty, endx, endy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * Invoked upon notification from observable class BGGeneration
	 */
	@Override
	public void update(Observable arg0, Object generation) {
		BGGeneration gen;
		if (generation instanceof Integer) { //The object passes will be integer 0 upon button click event of Reset
			reset = true;
			this.repaint();
		} else {
			gen = (BGGeneration) generation; // Cast the object to BGGeneration
			baseStem = gen.getFirstGen();
			this.revalidate();
			this.repaint();
		}
	}

}
