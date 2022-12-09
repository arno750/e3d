package e3d.render;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import e3d.base.Matrix;
import e3d.base.Point3D;
import e3d.base.Scene;
import e3d.base.Surface;
import e3d.base.Vector3D;
import e3d.base.Volume;

public class Renderer {

	Context context;
	Scene scene;

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
	 * @return
	 */
	public List<RenderedSurface> getSurfaces() {
		List<RenderedSurface> renderedSurfaces = new ArrayList<>();
		for (Volume volume : scene.getVolumes()) {
			for (Surface s : volume.getSurfaces()) {
				Point3D o = Matrix.multiply(s.o, context.transform);
				Vector3D n = Matrix.multiply(s.n, context.transform);
				if (Surface.isVisible(o, n) || ! context.hiddenSurfaceRemoval) {
					Point a = projectPoint(s.a.p);
					Point b = projectPoint(s.b.p);
					Point c = projectPoint(s.c.p);
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

		Point o = projectPoint(Point3D.ORIGIN);
		Point ox = projectPoint(Point3D.X_UNIT);
		Point oy = projectPoint(Point3D.Y_UNIT);
		Point oz = projectPoint(Point3D.Z_UNIT);

		renderedLines.add(new RenderedLine(o, ox, Color.red));
		renderedLines.add(new RenderedLine(o, oy, Color.green));
		renderedLines.add(new RenderedLine(o, oz, Color.blue));

		return renderedLines;
	}

	/**
	 * @param p
	 * @return
	 */
	public Point projectPoint(Point3D p) {
		return getAdjustedPoint(getScreenProjection(Matrix.multiply(p, context.transform)));
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
