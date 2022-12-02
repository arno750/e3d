package e3d.bs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fzv695
 *
 */
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
	 * @param scene
	 * @return
	 */
	public List<RenderedSurface> compute() {
		List<RenderedSurface> renderedSurfaces = new ArrayList<RenderedSurface>();
		for (Volume volume : scene.getVolumes()) {
			for (Surface s : volume.getSurfaces()) {
				Point3D o = Matrix.multiply(context.transform, s.o);
				Vector3D n = Matrix.multiply(context.transform, s.n);
				if (Surface.isVisible(o, n)) {
					Point a = getAdjustedPoint(getScreenProjection(Matrix.multiply(context.transform, s.a.p)));
					Point b = getAdjustedPoint(getScreenProjection(Matrix.multiply(context.transform, s.b.p)));
					Point c = getAdjustedPoint(getScreenProjection(Matrix.multiply(context.transform, s.c.p)));
					renderedSurfaces.add(new RenderedSurface(Point3D.getDistance(o), a, b, c, s.n));
				}
			}
		}
		Collections.sort(renderedSurfaces);
		return renderedSurfaces;
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
