package e3d.bs;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arnaud Wieland
 *
 */
public class Renderer {

	Context context;
	Scene scene;

	static Point3D X_AXIS = new Point3D(1, 0, 0);
	static Point3D Y_AXIS = new Point3D(0, 0, 1);

	/**
	 * @param scene
	 */
	public Renderer(Scene scene) {
		this.scene = scene;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @param scene
	 * @return
	 */
	public List<RenderedSurface> getSurfaces() {
		List<RenderedSurface> renderedSurfaces = new ArrayList<>();
		for (Volume volume : scene.getVolumes()) {
			for (Surface s : volume.getSurfaces()) {
				Point3D o = Matrix.multiply(s.o, context.transform);
				Vector3D n = Matrix.multiply(s.n, context.transform);
				if (Surface.isVisible(o, n)) {
					Point a = getAdjustedPoint(getScreenProjection(Matrix.multiply(s.a.p, context.transform)));
					Point b = getAdjustedPoint(getScreenProjection(Matrix.multiply(s.b.p, context.transform)));
					Point c = getAdjustedPoint(getScreenProjection(Matrix.multiply(s.c.p, context.transform)));
					renderedSurfaces.add(new RenderedSurface(o.getDistanceFromOrigin(), a, b, c, s.n));
				}
			}
		}
		Collections.sort(renderedSurfaces);
		return renderedSurfaces;
	}

	/**
	 * @return
	 */
	public List<RenderedLine> getLines() {
		List<RenderedLine> renderedLines = new ArrayList<>();

		Point o = getAdjustedPoint(getScreenProjection(Matrix.multiply(Point3D.ORIGIN, context.transform)));
		Point ox = getAdjustedPoint(getScreenProjection(Matrix.multiply(X_AXIS, context.transform)));
		Point oy = getAdjustedPoint(getScreenProjection(Matrix.multiply(Y_AXIS, context.transform)));

		renderedLines.add(new RenderedLine(o, ox, Color.blue));
		renderedLines.add(new RenderedLine(o, oy, Color.red));

		return renderedLines;
	}

	/**
	 * @param p
	 * @param focal
	 * @param resolution
	 * @return
	 */
	public Point getScreenProjection(Point3D p) {
		Point pp = new Point();
		double divisor = (p.z != 0) ? p.z : 1;
		pp.x = (int) (p.x * context.focal / divisor * context.resolution);
		pp.y = (int) (p.y * context.focal / divisor * context.resolution);

		return pp;
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
}
