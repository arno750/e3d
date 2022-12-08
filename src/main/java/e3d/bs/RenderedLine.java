package e3d.bs;

import java.awt.Color;
import java.awt.Point;

/**
 * @author Arnaud Wieland
 *
 */
public class RenderedLine {

	public Point a;
	public Point b;
	public Color c;

	/**
	 * @param a
	 * @param b
	 * @param c
	 */
	public RenderedLine(Point a, Point b, Color c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
}
