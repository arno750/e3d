package fr.arno750.e3d.base;

public class Surface {

	public Vertex a;
	public Vertex b;
	public Vertex c;

	public Point3D o; // triangle centroid
	public Vector3D n; // triangle normal

	public Surface() {
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 */
	public Surface(Vertex a, Vertex b, Vertex c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Indicates whether this surface contains the specified vertex.
	 *
	 * @param v a vertex.
	 * @return <tt>true</tt> if it does ; <tt>false</tt> otherwise.
	 */
	public boolean contains(Vertex v) {
		return a.equals(v) || b.equals(v) || c.equals(v);
	}

	/**
	 * @return
	 */
	public double getArea() {
		double detXY = getDeterminant(a.p.x, b.p.x, c.p.x, a.p.y, b.p.y, c.p.y, 1, 1, 1);
		double detYZ = getDeterminant(a.p.y, b.p.y, c.p.y, a.p.z, b.p.z, c.p.z, 1, 1, 1);
		double detZX = getDeterminant(a.p.z, b.p.z, c.p.z, a.p.x, b.p.x, c.p.x, 1, 1, 1);

		return 0.5 * Math.sqrt(detXY * detXY + detYZ * detYZ + detZX * detZX);
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 * @param g
	 * @param h
	 * @param i
	 * @return
	 */
	private double getDeterminant(double a, double b, double c, double d, double e, double f, double g, double h,
			double i) {
		return a * e * i + b * f * g + c * d * h - c * e * g - b * d * i - a * f * h;
	}

	/**
	 * 
	 */
	public void computeMiddleAndNormal() {
		o = Point3D.getMiddle(a.p, b.p, c.p);
		n = Vector3D.getCrossProduct(a.p, b.p, c.p).normalize();
	}

	/**
	 * @param center
	 */
	public void pointOutside(Point3D center) {
		Vector3D outside = new Vector3D(center, o);
		if (n.getDotProduct(outside) < 0) {
			Vertex t = b;
			b = c;
			c = t;
		}
	}

	/**
	 * @param o triangle centroid.
	 * @param n triangle normal.
	 * @return
	 */
	public static boolean isVisible(Point3D o, Vector3D n) {
		return o.x * n.i + o.y * n.j + o.z * n.k < 0;
	}
}
