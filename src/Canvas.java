import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	private static final long serialVersionUID = 831128243993452850L;
	
	public static final double FACTOR = 100;
	
	private Matrix[] initPoints = new Matrix[] { //The points forming the cube
			new Matrix(new double[][] {{0}, {0}, {0}}),
			new Matrix(new double[][] {{1}, {0}, {0}}),
			new Matrix(new double[][] {{0}, {1}, {0}}),
			new Matrix(new double[][] {{0}, {0}, {1}}),
			new Matrix(new double[][] {{1}, {1}, {0}}),
			new Matrix(new double[][] {{1}, {0}, {1}}),
			new Matrix(new double[][] {{0}, {1}, {1}}),
			new Matrix(new double[][] {{1}, {1}, {1}})
	};
	
	private Matrix adjacency = new Matrix(new double[][] { //Specifies where to draw lines to draw the cube on the screen
			{0, 1, 1, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 0, 0},
			{0, 0, 0, 0, 1, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0, 0, 0, 0}
	});
	
	private Matrix[] calcPoints = new Matrix[initPoints.length];
	
	private Matrix eye = new Matrix(new double[][] {{0}, {0}, {1.5}}); //The position of the observer
	
	private double angleY = 0;
	private double angleX = 0;
	private double angleZ = 0;
	private double xSpeed = 0;
	private double ySpeed = 0;
	private double zSpeed = 0;
	private Matrix rotationY;
	private Matrix rotationX;
	private Matrix rotationZ;
	
	public Canvas() {
		//Start an infinite loop to render the rotating cube
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					updatePosition();
					repaint();
					
					try {
						Thread.sleep(17); //Will result in a bit less than 60fps
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private void updatePosition() {
		angleY += xSpeed; //Math.PI / 120 * 2;
		angleZ += ySpeed; //Math.PI / 270 * 2;
		angleX += zSpeed; //Math.PI / 180 * 2;
		
		//The 3 rotations matrices
		rotationY = new Matrix(new double[][] {
				{Math.cos(angleY), 0, -Math.sin(angleY)},
				{0, 1, 0},
				{Math.sin(angleY), 0, Math.cos(angleY)}
		});
		
		rotationX = new Matrix(new double[][] {
				{1, 0, 0},
				{0, Math.cos(angleX), -Math.sin(angleX)},
				{0, Math.sin(angleX), Math.cos(angleX)}		
		});
		
		rotationZ = new Matrix(new double[][] {
				{Math.cos(angleZ), -Math.sin(angleZ), 0},
				{Math.sin(angleZ), Math.cos(angleZ), 0},
				{0, 0, 1}
		});
		
		for(int i = 0; i < initPoints.length; i++) { //Calculate the projected points
			calcPoints[i] = initPoints[i].subtract(eye);
			calcPoints[i] = rotationY.multiply(calcPoints[i]);
			calcPoints[i] = rotationX.multiply(calcPoints[i]);
			calcPoints[i] = rotationZ.multiply(calcPoints[i]);
			calcPoints[i] = calcPoints[i].subtract(eye);	
			
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //Add antialiasing to smooth the lines of the cube
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2.translate(getWidth() / 2, getHeight() / 2); //Center the frame
		
		//g2.fillOval(-2, -2, 4, 4); //Draw the (0, 0) for reference
		
		for(int i = 0; i < adjacency.getNbRows(); i++) {
			for(int j = 0; j < adjacency.getNbColumns(); j++) {
				if(adjacency.getEntry(i, j) == 1) {
					
					/*if(calcPoints[i].getEntry(3, 1) > 0.05 || calcPoints[j].getEntry(3, 1) > 0.05)
						g2.setColor(Color.RED);
					else*/
						g2.setColor(Color.BLACK);
					
					g2.drawLine((int) (calcPoints[i].getEntry(0, 0) * FACTOR), (int) (calcPoints[i].getEntry(1, 0) * FACTOR),
							(int) (calcPoints[j].getEntry(0, 0) * FACTOR), (int) (calcPoints[j].getEntry(1, 0) * FACTOR));
				}
			}
		}
		
	}
	
	public void setXSpeed(double speed) {
		this.xSpeed = speed;
	}
	
	public void setYSpeed(double speed) {
		this.ySpeed = speed;
	}
	
	public void setZSpeed(double speed) {
		this.zSpeed = speed;
	}
}
