package e3d.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import e3d.bs.Matrix;
import e3d.bs.Point3D;
import e3d.bs.Scene;
import e3d.bs.Surface;
import e3d.bs.Volume;

/**
 * @author Arnaud Wieland
 * 
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static int WIDTH = 400;
	static int HEIGHT = 300;
	static int HALF_WIDTH = WIDTH >> 1;
	static double RESOLUTION = 20;
	static Point3D X_AXIS = new Point3D(1, 0, 0);
	static Point3D Y_AXIS = new Point3D(0, 0, 1);

	/**
	 */
	public void initialize() {
		Dimension dimension = new Dimension(WIDTH, HEIGHT);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		graphics.setColor(Color.blue);
		Point o = getMapProjection(Point3D.ORIGIN);
		Point oy = getMapProjection(Y_AXIS);
		graphics.drawLine(o.x, o.y, oy.x, oy.y);

		graphics.setColor(Color.red);
		o = getMapProjection(Matrix.multiply(Controller.context.transform,
				Point3D.ORIGIN));
		Point ox = getMapProjection(Matrix.multiply(
				Controller.context.transform, X_AXIS));
		oy = getMapProjection(Matrix.multiply(Controller.context.transform,
				Y_AXIS));
		graphics.drawLine(o.x, o.y, ox.x, ox.y);
		graphics.drawLine(o.x, o.y, oy.x, oy.y);

		graphics.setColor(Color.lightGray);
		Scene scene = Controller.scene;
		for (Volume volume : scene.getVolumes()) {
			for (Surface surface : volume.getSurfaces()) {
				Point a = getMapProjection(Matrix.multiply(
						Controller.context.transform, surface.a.p));
				Point b = getMapProjection(Matrix.multiply(
						Controller.context.transform, surface.b.p));
				Point c = getMapProjection(Matrix.multiply(
						Controller.context.transform, surface.c.p));

				graphics.drawLine(a.x, a.y, b.x, b.y);
				graphics.drawLine(b.x, b.y, c.x, c.y);
				graphics.drawLine(c.x, c.y, a.x, a.y);
			}
		}
	}

	/**
	 * @param p
	 * @return
	 */
	public static Point getMapProjection(Point3D p) {
		Point pp = new Point();
		pp.x = HALF_WIDTH + (int) (p.x * RESOLUTION);
		pp.y = HEIGHT - (int) (p.z * RESOLUTION);
		return pp;
	}
}
