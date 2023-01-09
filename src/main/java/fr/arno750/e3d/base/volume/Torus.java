package fr.arno750.e3d.base.volume;

import fr.arno750.e3d.base.Point3D;
import fr.arno750.e3d.base.Surface;
import fr.arno750.e3d.base.VertexFactory;
import fr.arno750.e3d.base.Volume;
import fr.arno750.e3d.base.config.VolumeDefinition;

public class Torus extends Volume {

    /**
     * @param definition
     * @param vertexFactory
     */
    public Torus(VolumeDefinition definition, VertexFactory vertexFactory) {
        super(definition, vertexFactory);

        double ratio = definition.getParameters().getRatio();
        if (ratio <= 0.0)
            throw new IllegalArgumentException(String.format("Ratio should be greater than 0"));
        int latitudes = definition.getParameters().getLatitudes();
        if (latitudes < 4)
            throw new IllegalArgumentException(String.format("Latitude count should be greater than or equal to 4"));
        int longitudes = definition.getParameters().getLongitudes();
        if (longitudes < 4)
            throw new IllegalArgumentException(String.format("Longitude count should be greater than or equal to 4"));

        // Adds vertices
        for (int i = 0; i < latitudes; i++) {
            for (int j = 0; j < longitudes; j++) {
                double theta = 2 * Math.PI * i / latitudes;
                double phi = 2 * Math.PI * j / longitudes;

                vertices.add(vertexFactory.build((1 + ratio * Math.cos(theta))
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
                s1.computeMiddleAndNormal();
                s1.pointOutside(centers[j]);
                surfaces.add(s1);

                Surface s2 = new Surface(vertices.get(c), vertices.get(d),
                        vertices.get(b));
                s2.computeMiddleAndNormal();
                s2.pointOutside(centers[j]);
                surfaces.add(s2);
            }
        }

        transform(definition.getTransformMatrix());
        prepareSurfaces();
        workOutVertexNormals();
    }
}
