package fr.arno750.e3d.base.volume;

import fr.arno750.e3d.base.Point3D;
import fr.arno750.e3d.base.Surface;
import fr.arno750.e3d.base.VertexFactory;
import fr.arno750.e3d.base.Volume;
import fr.arno750.e3d.base.config.VolumeDefinition;

public class Box extends Volume {
    static final double[][] VERTICES = {{0, 0, 0}, {1, 0, 0}, {1, 1, 0},
            {0, 1, 0}, {0, 0, 1}, {1, 0, 1}, {1, 1, 1}, {0, 1, 1}};
    static final int[][] SURFACES = {{0, 1, 2}, {2, 3, 0}, {4, 5, 6},
            {6, 7, 4}, {0, 1, 4}, {4, 5, 1}, {1, 2, 5}, {5, 6, 2},
            {2, 3, 6}, {6, 7, 3}, {3, 0, 7}, {7, 4, 0}};

    /**
     * @param definition
     * @param vertexFactory
     */
    public Box(VolumeDefinition definition, VertexFactory vertexFactory) {
        super(definition, vertexFactory);

        for (double[] coordinates : VERTICES) {
            vertices.add(vertexFactory.build(coordinates[0], coordinates[1],
                    coordinates[2]));
        }

        for (int[] indices : SURFACES) {
            Surface s = new Surface();
            s.a = vertices.get(indices[0]);
            s.b = vertices.get(indices[1]);
            s.c = vertices.get(indices[2]);
            surfaces.add(s);
        }

        prepareSurfaces(new Point3D(0.5, 0.5, 0.5));
        transform(definition.getTransformMatrix());
        prepareSurfaces();
        workOutVertexNormals();
    }
}
