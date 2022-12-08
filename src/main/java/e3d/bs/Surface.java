package e3d.bs;

/**
 * @author Arnaud Wieland
 * 
 */
public class Surface {

	public Vertex a;
	public Vertex b;
	public Vertex c;

	public Point3D o; // triangle centroid
	public Vector3D n; // triangle normal

	/**
	 * 
	 */
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
	public void initialize() {
		o = new Point3D((a.p.x + b.p.x + c.p.x) / 3, (a.p.y + b.p.y + c.p.y) / 3, (a.p.z + b.p.z + c.p.z) / 3);
		n = Vector3D.getCrossProduct(a.p, b.p, c.p);
		n.normalize();
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
	 * @param triangle centroid
	 * @param triangle normal
	 * @return
	 */
	public static boolean isVisible(Point3D o, Vector3D n) {
		return o.x * n.i + o.y * n.j + o.z * n.k < 0;
	}
}
