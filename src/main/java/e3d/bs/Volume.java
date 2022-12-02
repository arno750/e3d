package e3d.bs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arnaud Wieland
 * 
 */
public class Volume {

	protected String name;
	protected List<Vertex> vertices = new ArrayList<Vertex>();
	protected List<Surface> surfaces = new ArrayList<Surface>();

	/**
	 * 
	 */
	public Volume() {
	}

	/**
	 * @param v
	 */
	public Volume(Volume v) {
		this.name = v.name;
	}

	/**
	 * @return the vertices
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * @return the surfaces
	 */
	public List<Surface> getSurfaces() {
		return surfaces;
	}

	/**
	 * @param v
	 * @return
	 */
	public List<Surface> getSurfaces(Vertex v) {
		List<Surface> ss = new ArrayList<Surface>();
		for (Surface s : surfaces)
			if ((s.a == v) || (s.b == v) || (s.c == v))
				ss.add(s);

		return ss;
	}

	/**
	 * @param ls
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Surface getLinkedSurface(Surface ls, Vertex v1, Vertex v2) {
		for (Surface s : surfaces)
			if ((s.a == v1) || (s.b == v1) || (s.c == v1) || (s.a == v2) || (s.b == v2) || (s.c == v2))
				if (s != ls)
					return s;

		return null;
	}

	/**
	 * @param transform
	 */
	protected void apply(Matrix transform) {
		for (Vertex v : vertices) {
			v.p.apply(transform);
		}
	}

	/**
	 * @param center
	 */
	protected void pointOutside(Point3D center) {
		for (Surface s : surfaces) {
			s.workOutParameters();
			s.updateToPointOutside(center);
		}
	}

	/**
	 * 
	 */
	protected void workOutSurfaceParameters() {
		for (Surface s : surfaces)
			s.workOutParameters();
	}

	/**
	 * 
	 */
	protected void workOutVertexNormals() {
		for (Vertex v : vertices) {
			List<Surface> ss = getSurfaces(v);
			v.n = new Vector3D();
			for (Surface s : ss) {
				double area = s.getArea();
				v.n.i += s.n.i * area;
				v.n.j += s.n.j * area;
				v.n.k += s.n.k * area;
			}
			v.n.normalize();
		}
	}

	/**
	 * @param steps
	 * @param coefficients
	 * @param data
	 * @param indices
	 */
	public void addBezierPatch(int steps, double[][] coefficients, double[][] data, int[] indices) {
		int start = vertices.size();
		double coefficient;
		for (int u = 0; u < steps; u++)
			for (int v = 0; v < steps; v++) {

				Point3D p = new Point3D();
				int k = 0;
				for (int i = 0; i < 4; i++)
					for (int j = 0; j < 4; j++) {
						int vi = indices[k];
						coefficient = coefficients[i][u] * coefficients[j][v];
						p.x += data[vi][0] * coefficient;
						p.y += data[vi][2] * coefficient;
						p.z += data[vi][1] * coefficient;
						k++;
					}

				vertices.add(new Vertex(p));
			}

		for (int u = 0; u < steps - 1; u++)
			for (int v = 0; v < steps - 1; v++) {
				int a = start + u * steps + v;
				int b = a + 1;
				int c = a + steps;
				int d = c + 1;
				surfaces.add(new Surface(vertices.get(a), vertices.get(c), vertices.get(b)));
				surfaces.add(new Surface(vertices.get(b), vertices.get(c), vertices.get(d)));
			}
	}

	/**
	 * @param steps
	 * @return
	 */
	public double[][] getBernsteinBasisFunction(int steps) {
		double[][] coefficients = new double[4][steps];
		for (int i = 0; i < steps; i++) {
			double u = i / (double) (steps - 1);
			double u2 = u * u;
			double v = 1.0 - u;
			double v2 = v * v;

			coefficients[0][i] = v * v2;
			coefficients[1][i] = 3 * u * v2;
			coefficients[2][i] = 3 * u2 * v;
			coefficients[3][i] = u * u2;
		}

		return coefficients;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format("- name: %s\n  surfaces:\n", name));
		for (Surface s : surfaces) {
			buffer.append(String.format("  - A%s B%s C%s\n", s.a.p, s.b.p, s.c.p));
		}
		return buffer.toString();
	}
}
