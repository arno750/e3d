package fr.arno750.e3d.gui;

import fr.arno750.e3d.render.Context;
import fr.arno750.e3d.render.RenderedLine;
import fr.arno750.e3d.render.RenderedSurface;
import fr.arno750.e3d.render.SurfaceType;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class RenderingPanel extends JPanel {

    static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);
    static final Stroke THICK_STROKE = new BasicStroke(2.0f);
    @Serial
    private static final long serialVersionUID = -749852770402369917L;
    static Color[] GRAYS = new Color[256];

    static {
        for (int i = 0; i < 256; i++)
            GRAYS[i] = new Color(i, i, i);
    }

    Context context;

    /**
     * @param width
     * @param height
     */
    public void initialize(Context context, int width, int height) {
        this.context = context;
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

        Controller.renderer.render();

        for (RenderedSurface s : Controller.renderer.getRenderedSurfaces()) {
            int intensity = (int) (128.0 - s.n.k * 64.0);
            if (intensity > 255)
                intensity = 255;
            if (intensity < 0)
                intensity = 0;
            graphics.setColor(GRAYS[intensity]);

            if (context.surfaceType == SurfaceType.OPAQUE) {
                Polygon polygon = new Polygon();
                polygon.addPoint(s.a.x, s.a.y);
                polygon.addPoint(s.b.x, s.b.y);
                polygon.addPoint(s.c.x, s.c.y);
                graphics.fillPolygon(polygon);
            }

            if (context.surfaceType == SurfaceType.MESH) {
                graphics.drawLine(s.a.x, s.a.y, s.b.x, s.b.y);
                graphics.drawLine(s.b.x, s.b.y, s.c.x, s.c.y);
                graphics.drawLine(s.c.x, s.c.y, s.a.x, s.a.y);
            }
        }

        for (RenderedLine l : Controller.renderer.getRenderedLines()) {
            graphics.setColor(l.c);
            graphics.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
        }
    }
}
