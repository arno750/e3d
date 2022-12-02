package e3d.bs.volume;

import e3d.bs.Matrix;
import e3d.bs.Point3D;
import e3d.bs.Surface;
import e3d.bs.Vertex;
import e3d.bs.Volume;

/**
 * @author Arnaud Wieland
 * 
 */
public class Box extends Volume {
	static final double[][] VERTICES = { { 0, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 },
			{ 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 1 }, { 1, 1, 1 }, { 0, 1, 1 } };
	static final int[][] SURFACES = { { 0, 1, 2 }, { 2, 3, 0 }, { 4, 5, 6 },
			{ 6, 7, 4 }, { 0, 1, 4 }, { 4, 5, 1 }, { 1, 2, 5 }, { 5, 6, 2 },
			{ 2, 3, 6 }, { 6, 7, 3 }, { 3, 0, 7 }, { 7, 4, 0 } };

	/**
	 * @param tranform
	 * @return
	 */
	public Box(Matrix transform) {
		name = "Box";
		
		for (double[] coordinates : VERTICES) {
			vertices.add(new Vertex(coordinates[0], coordinates[1],
					coordinates[2]));
		}
		
		for (int[] indices : SURFACES) {
			Surface s = new Surface();
			s.a = vertices.get(indices[0]);
			s.b = vertices.get(indices[1]);
			s.c = vertices.get(indices[2]);
			surfaces.add(s);
		}

		pointOutside(new Point3D(0.5, 0.5, 0.5));
		apply(transform);
		workOutSurfaceParameters();
		workOutVertexNormals();
	}
}
