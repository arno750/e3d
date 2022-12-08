package e3d.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Stroke;

import javax.swing.JPanel;

import e3d.bs.RenderedLine;
import e3d.bs.RenderedSurface;

/**
 * @author Arnaud Wieland
 * 
 */
public class RenderingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);
	static final Stroke THICK_STROKE = new BasicStroke(2.0f);
	static double RESOLUTION = 1000;
	static Color[] GRAYS = new Color[256];
	static {
		for (int i = 0; i < 256; i++)
			GRAYS[i] = new Color(i, i, i);
	}

	/**
	 * @param width
	 * @param height
	 */
	public void initialize(int width, int height) {
		Dimension dimension = new Dimension(width, height);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		for (RenderedSurface s : Controller.renderer.getSurfaces()) {
			int intensity = (int) (128.0 - s.n.k * 64.0);
			if (intensity > 255)
				intensity = 255;
			if (intensity < 0)
				intensity = 0;
			graphics.setColor(GRAYS[intensity]);
			Polygon polygon = new Polygon();
			polygon.addPoint(s.a.x, s.a.y);
			polygon.addPoint(s.b.x, s.b.y);
			polygon.addPoint(s.c.x, s.c.y);
//			graphics.fillPolygon(polygon);

			graphics.drawLine(s.a.x, s.a.y, s.b.x, s.b.y);
			graphics.drawLine(s.b.x, s.b.y, s.c.x, s.c.y);
			graphics.drawLine(s.c.x, s.c.y, s.a.x, s.a.y);
		}

		for (RenderedLine l : Controller.renderer.getLines()) {
			graphics.setColor(l.c);
			graphics.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
		}
	}
}
