package fr.arno750.e3d.render;

import java.awt.Color;
import java.awt.Point;

/**
 * @author Arnaud Wieland
 *
 */
public class RenderedLine {

	public final Point a;
	public final Point b;
	public final Color c;

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
