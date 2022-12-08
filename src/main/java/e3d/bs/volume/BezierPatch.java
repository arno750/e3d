package e3d.bs.volume;

import e3d.bs.Matrix;
import e3d.bs.Volume;

/**
 * @author Arnaud Wieland
 * 
 */
public class BezierPatch extends Volume {

	public static final boolean DEBUG = false;
	static final int[][] PATCHES = { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16 } };

	static final double[][] VERTICES = { { 0.0, 0.0, 0.0 },
			{ -3.0, 0.0, -3.0 }, { -1.0, 0.0, -3.0 }, { 1.0, 0.0, -3.0 },
			{ 3.0, 0.0, -3.0 }, { -3.0, 0.0, -1.0 }, { -3.0, 3.0, -3.0 },
			{ 3.0, 3.0, -3.0 }, { 3.0, 0.0, -1.0 }, { -3.0, 0.0, 1.0 },
			{ -3.0, 3.0, 3.0 }, { 3.0, 3.0, 3.0 }, { 3.0, 0.0, 1.0 },
			{ -3.0, 0.0, 3.0 }, { -1.0, 0.0, 3.0 }, { 1.0, 0.0, 3.0 },
			{ 3.0, 0.0, 3.0 } };

	double coefficients[][];

	/**
	 * @param steps
	 * @param transform
	 */
	public BezierPatch(int steps, Matrix transform) {
		name = "Teapot";

		coefficients = getBernsteinBasisFunction(steps);
		for (int[] patch : PATCHES)
			addBezierPatch(steps, coefficients, VERTICES, patch);

		System.out.println("Vertices: " + vertices.size());
		System.out.println("Surfaces: " + surfaces.size());

		// pointOutside(new Point3D(0, 0, 0));
		transform(transform);
		initialize();
		workOutVertexNormals();
	}
}
