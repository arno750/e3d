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
public class Torus extends Volume {

	/**
	 * @param ratio
	 * @param latitudes
	 * @param longitudes
	 * @param transform
	 * @return
	 */
	public Torus(double ratio, int latitudes, int longitudes, Matrix transform) {
		name = "Torus";

		// Adds vertices
		for (int i = 0; i < latitudes; i++) {
			for (int j = 0; j < longitudes; j++) {
				double theta = 2 * Math.PI * i / latitudes;
				double phi = 2 * Math.PI * j / longitudes;

				vertices.add(new Vertex((1 + ratio * Math.cos(theta))
						* Math.cos(phi), ratio * Math.sin(theta), (1 + ratio
						* Math.cos(theta))
						* Math.sin(phi)));
			}
		}

		// Defines the centers per longitudes
		Point3D[] centers = new Point3D[longitudes];
		for (int j = 0; j < longitudes; j++) {
			double phi = 2 * Math.PI * j / longitudes;
			centers[j] = new Point3D(Math.cos(phi), 0, Math.sin(phi));
		}

		// Adds squares
		for (int i = 0; i < latitudes; i++) {
			for (int j = 0; j < longitudes; j++) {
				int a = i * longitudes + j;
				int b = a + 1;
				int c = (i + 1) * longitudes + j;
				int d = c + 1;
				if (i == latitudes - 1) {
					c -= latitudes * longitudes;
					d -= latitudes * longitudes;
				}
				if (j == longitudes - 1) {
					b -= longitudes;
					d -= longitudes;
				}

				Surface s1 = new Surface(vertices.get(a), vertices.get(b),
						vertices.get(c));
				s1.workOutParameters();
				s1.updateToPointOutside(centers[j]);
				surfaces.add(s1);

				Surface s2 = new Surface(vertices.get(c), vertices.get(d),
						vertices.get(b));
				s2.workOutParameters();
				s2.updateToPointOutside(centers[j]);
				surfaces.add(s2);
			}
		}

		apply(transform);
		workOutSurfaceParameters();
		workOutVertexNormals();
	}
}
