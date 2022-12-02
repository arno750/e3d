package e3d.bs;

import java.awt.Point;

import e3d.gui.Controller;

/**
 * @author Arnaud Wieland
 * 
 */
public class Toolbox {

	/**
	 * @param value
	 * @return
	 */
	public static double getAngle(double value) {
		if (value > 1.0)
			return 0;
		if (value < -1.0)
			return 2 * Math.PI;
		return Math.acos(value);
	}

	/**
	 * @param p
	 * @param focal
	 * @param resolution
	 * @return
	 */
	public static Point getScreenProjection(Point3D p, double focal, double resolution) {

		Point pp = new Point();
		double divisor = (p.z != 0) ? p.z : 1;
		pp.x = (int) (p.x * Controller.context.focal / divisor * resolution);
		pp.y = (int) (p.y * Controller.context.focal / divisor * resolution);

		return pp;
	}

	/**
	 * @param deg
	 * @return
	 */
	public static double rad(double deg) {
		return deg / 180.0 * Math.PI;
	}
}
