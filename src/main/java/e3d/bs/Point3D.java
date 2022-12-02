package e3d.bs;

/**
 * @author Arnaud Wieland
 *
 */
public class Point3D {

	public static Point3D ORIGIN = new Point3D(0, 0, 0);

	public double x, y, z;

	/**
	 * 
	 */
	public Point3D() {
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @param p
	 */
	public Point3D(Point3D p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	/**
	 * @param p
	 * @param v
	 */
	public Point3D(Point3D p, Vector3D v) {
		this.x = p.x + v.i;
		this.y = p.y + v.j;
		this.z = p.z + v.k;
	}

	/**
	 * @param m
	 */
	public void apply(Matrix m) {
		double tx = m.m00 * x + m.m01 * y + m.m02 * z + m.m03;
		double ty = m.m10 * x + m.m11 * y + m.m12 * z + m.m31;
		double tz = m.m20 * x + m.m21 * y + m.m22 * z + m.m32;
		x = tx;
		y = ty;
		z = tz;
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getDistance(Point3D a, Point3D b) {
		double dx = b.x - a.x;
		double dy = b.y - a.y;
		double dz = b.z - a.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * @param p
	 * @return
	 */
	public static double getDistance(Point3D p) {
		return Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
	}

	/**
	 * @return
	 */
	public static Point3D getMiddle(Point3D a, Point3D b) {
		return new Point3D((a.x + b.x) / 2, (a.y + b.y) / 2, (a.z + b.z) / 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%.1f, %.1f, %.1f]", x, y, z);
	}
}
