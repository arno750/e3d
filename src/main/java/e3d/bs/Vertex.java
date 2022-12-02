package e3d.bs;

/**
 * @author Arnaud Wieland
 * 
 */
public class Vertex {

	public Point3D p;
	public Vector3D n;

	/**
	 * 
	 */
	public Vertex() {
	}

	/**
	 * @param p
	 */
	public Vertex(Point3D p) {
		this.p = p;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vertex(double x, double y, double z) {
		this.p = new Point3D(x, y, z);
	}
}
