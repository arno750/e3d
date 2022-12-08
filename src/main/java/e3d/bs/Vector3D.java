package e3d.bs;

/**
 * @author Arnaud Wieland
 * 
 */
public class Vector3D {

	public static Vector3D I_AXIS = new Vector3D(1, 0, 0);
	public static Vector3D J_AXIS = new Vector3D(0, 1, 0);
	public static Vector3D K_AXIS = new Vector3D(0, 0, 1);

	public double i, j, k;

	/**
	 * 
	 */
	public Vector3D() {
	}

	/**
	 * @param i
	 * @param j
	 * @param k
	 */
	public Vector3D(double i, double j, double k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}

	/**
	 * @param a
	 * @param b
	 */
	public Vector3D(Point3D a, Point3D b) {
		this.i = b.x - a.x;
		this.j = b.y - a.y;
		this.k = b.z - a.z;
	}

	/**
	 * @return
	 */
	public double getLength() {
		return Math.sqrt(i * i + j * j + k * k);
	}

	/**
	 */
	public void reverse() {
		i *= -1;
		j *= -1;
		k *= -1;
	}

	/**
	 * 
	 */
	public void normalize() {
		double length = getLength();
		if (length != 0) {
			i /= length;
			j /= length;
			k /= length;
		}
	}

	/**
	 * @param m
	 */
	public void multiply(Matrix m) {
		double ti = m.m00 * i + m.m01 * j + m.m02 * k + m.m03;
		double tj = m.m10 * i + m.m11 * j + m.m12 * k + m.m31;
		double tk = m.m20 * i + m.m21 * j + m.m22 * k + m.m32;
		this.i = ti;
		this.j = tj;
		this.k = tk;
	}

	/**
	 * @param v
	 * @return
	 */
	public double getDotProduct(Vector3D v) {
		return i * v.i + j * v.j + k * v.k;
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3D getCrossProduct(Vector3D a, Vector3D b) {
		Vector3D ab = new Vector3D();
		ab.i = a.j * b.k - a.k * b.j;
		ab.j = a.k * b.i - a.i * b.k;
		ab.k = a.i * b.j - a.j * b.i;
		return ab;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Vector3D getCrossProduct(Point3D a, Point3D b, Point3D c) {
		Vector3D abac = new Vector3D();
		abac.i = (b.y - a.y) * (c.z - a.z) - (b.z - a.z) * (c.y - a.y);
		abac.j = (b.z - a.z) * (c.x - a.x) - (b.x - a.x) * (c.z - a.z);
		abac.k = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		return abac;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%.1f, %.1f, %.1f]", i, j, k);
	}
}
