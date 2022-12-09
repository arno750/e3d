package e3d.base;

public class Point3D {

	public static final Point3D ORIGIN = new Point3D(0, 0, 0);
	public static final Point3D X_UNIT = new Point3D(1, 0, 0);
	public static final Point3D Y_UNIT = new Point3D(0, 1, 0);
	public static final Point3D Z_UNIT = new Point3D(0, 0, 1);

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
	public void multiply(Matrix m) {
		double tx = m.m00 * x + m.m10 * y + m.m20 * z + m.m30;
		double ty = m.m01 * x + m.m11 * y + m.m21 * z + m.m31;
		double tz = m.m02 * x + m.m12 * y + m.m22 * z + m.m32;
		this.x = tx;
		this.y = ty;
		this.z = tz;
	}

	/**
	 * @return
	 */
	public double getDistanceFromOrigin() {
		return getDistance(ORIGIN);
	}

	/**
	 * @param a
	 * @return
	 */
	public double getDistance(Point3D a) {
		double dx = x - a.x;
		double dy = y - a.y;
		double dz = z - a.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * @return
	 */
	public static Point3D getMiddle(Point3D a, Point3D b) {
		return new Point3D((a.x + b.x) / 2, (a.y + b.y) / 2, (a.z + b.z) / 2);
	}

	@Override
	public String toString() {
		return String.format("[%.1f, %.1f, %.1f]", x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point3D other = (Point3D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);
	}
}
