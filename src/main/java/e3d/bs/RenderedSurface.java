package e3d.bs;

import java.awt.Point;

/**
 * @author Arnaud Wieland
 * 
 */
public class RenderedSurface implements Comparable<RenderedSurface> {

	public double z;
	public Point a;
	public Point b;
	public Point c;
	public Vector3D n;

	/**
	 * @param z
	 * @param a
	 * @param b
	 * @param c
	 * @param n
	 */
	public RenderedSurface(double z, Point a, Point b, Point c, Vector3D n) {
		this.z = z;
		this.a = a;
		this.b = b;
		this.c = c;
		this.n = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(RenderedSurface renderedSurface) {
		return -Double.compare(z, renderedSurface.z);
	}
}
