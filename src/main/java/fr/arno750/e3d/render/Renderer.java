package fr.arno750.e3d.render;

import fr.arno750.e3d.base.*;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Renderer {

    Context context;
    Scene scene;

    List<RenderedSurface> renderedSurfaces;
    List<RenderedLine> renderedLines;

    /**
     * @param scene
     */
    public Renderer(Scene scene) {
        this.scene = scene;
        renderedSurfaces = new ArrayList<>();
        renderedLines = new ArrayList<>();
    }

    /**
     * @param context the context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }

    public List<RenderedSurface> getRenderedSurfaces() {
        return renderedSurfaces;
    }

    public List<RenderedLine> getRenderedLines() {
        return renderedLines;
    }

    /**
     * @return
     */
    public void render() {
        renderedSurfaces.clear();
        renderedLines.clear();

        Map<Integer, Point> points = new HashMap<>();
        for (Volume volume : scene.getVolumes()) {
            for (Surface s : volume.getSurfaces()) {
                Point3D o = Matrix.multiply(s.o, context.transform);
                Vector3D n = Matrix.multiply(s.n, context.transform);
                if (Surface.isVisible(o, n) || !context.hiddenSurfaceRemoval) {
                    Point a = getProjectedPoint(s.a, points);
                    Point b = getProjectedPoint(s.b, points);
                    Point c = getProjectedPoint(s.c, points);
                    renderedSurfaces.add(new RenderedSurface(o.getDistanceFromOrigin(), a, b, c, s.n));
                    if(context.normal) {
                        renderedLines.add(new RenderedLine(projectPoint(o), projectPoint(new Point3D(o, n.setLength(0.01))), Color.DARK_GRAY));
                    }
                }
            }
        }
        points.clear();
        Collections.sort(renderedSurfaces);

        if (context.axis) {
            Point o = projectOriginalPoint(Point3D.ORIGIN);
            Point ox = projectOriginalPoint(Point3D.X_UNIT);
            Point oy = projectOriginalPoint(Point3D.Y_UNIT);
            Point oz = projectOriginalPoint(Point3D.Z_UNIT);

            renderedLines.add(new RenderedLine(o, ox, Color.red));
            renderedLines.add(new RenderedLine(o, oy, Color.green));
            renderedLines.add(new RenderedLine(o, oz, Color.blue));
        }
    }

    /**
     * @param v
     * @param points
     * @return
     */
    private Point getProjectedPoint(Vertex v, Map<Integer, Point> points) {
        Point pp = points.get(v.id);
        if (pp != null)
            return pp;
        pp = projectOriginalPoint(v.p);
        points.put(v.id, pp);
        return pp;
    }

    /**
     * @param p
     * @return
     */
    public Point projectOriginalPoint(Point3D p) {
        return projectPoint(Matrix.multiply(p, context.transform));
    }

    /**
     * @param p
     * @return
     */
    public Point projectPoint(Point3D p) {
        return getAdjustedPoint(getScreenProjection(p));
    }

    /**
     * @param p
     * @return
     */
    public Point getAdjustedPoint(Point p) {
        p.x += context.halfWidth;
        p.y = context.halfHeight - p.y;
        return p;
    }

    /**
     * @param p
     * @return
     */
    public Point getScreenProjection(Point3D p) {
        Point pp = new Point();
        double divisor = (p.z != 0) ? p.z : 1;
        pp.x = (int) (p.x * context.focal / divisor * context.resolution);
        pp.y = (int) (p.y * context.focal / divisor * context.resolution);

        return pp;
    }
}
