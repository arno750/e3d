package fr.arno750.e3d.base.volume;

import fr.arno750.e3d.base.Point3D;
import fr.arno750.e3d.base.Surface;
import fr.arno750.e3d.base.Vertex;
import fr.arno750.e3d.base.Volume;
import fr.arno750.e3d.base.config.VolumeDefinition;

public class Sphere extends Volume {

    /**
     * @param definition
     * @return
     */
    public Sphere(VolumeDefinition definition) {
        super(definition);

        int latitudes = definition.getParameters().getLatitudes();
        int longitudes = definition.getParameters().getLongitudes();
        int halfLatitudes = latitudes / 2;
        int halfLongitudes = longitudes / 2;

        // Adds vertices (only one on poles)
        for (int i = -halfLatitudes; i <= halfLatitudes; i++) {
            for (int j = -halfLongitudes; j < halfLongitudes; j++) {
                double theta = Math.PI / 2 * i / halfLatitudes;
                double phi = Math.PI * j / halfLongitudes;

                if (Math.abs(i) == halfLatitudes) {
                    vertices.add(new Vertex(0, Math.sin(theta), 0));
                    break;
                }

                vertices.add(new Vertex(Math.cos(theta) * Math.cos(phi), Math
                        .sin(theta), Math.cos(theta) * Math.sin(phi)));
            }
        }

        // Adds top/bottom triangles
        for (int j = 0; j < longitudes; j++) {
            int a = 0;
            int b = j + 1;
            int c = b + 1;
            if (j == longitudes - 1)
                c = 1;

            surfaces.add(new Surface(vertices.get(a), vertices.get(b), vertices
                    .get(c)));

            a = (latitudes - 1) * longitudes + 1;
            b += (latitudes - 2) * longitudes;
            c += (latitudes - 2) * longitudes;

            surfaces.add(new Surface(vertices.get(a), vertices.get(b), vertices
                    .get(c)));
        }

        // Adds middle squares
        for (int i = 0; i < latitudes - 2; i++) {
            for (int j = 0; j < longitudes; j++) {
                int a = i * longitudes + j + 1;
                int b = a + 1;
                int c = (i + 1) * longitudes + j + 1;
                int d = c + 1;
                if (j == longitudes - 1) {
                    b -= longitudes;
                    d -= longitudes;
                }

                surfaces.add(new Surface(vertices.get(a), vertices.get(b),
                        vertices.get(c)));
                surfaces.add(new Surface(vertices.get(c), vertices.get(d),
                        vertices.get(b)));
            }
        }

        prepareSurfaces(Point3D.ORIGIN);
        transform(definition.getTransformMatrix());
        prepareSurfaces();
        workOutVertexNormals();
    }
}
