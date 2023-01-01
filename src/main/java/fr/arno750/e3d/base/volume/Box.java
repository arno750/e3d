package fr.arno750.e3d.base.volume;

import fr.arno750.e3d.base.*;
import fr.arno750.e3d.base.config.VolumeDefinition;

import java.util.HashMap;
import java.util.Map;

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

        Map<Integer, Vertex> map = new HashMap<>();
        for (int[] indices : SURFACES) {
            splitTriangle(vertices.get(indices[0]), vertices.get(indices[1]), vertices.get(indices[2]), definition.getParameters().getSteps(), map, vertexFactory);
        }
        vertices.addAll(map.values());

        prepareSurfaces(new Point3D(0.5, 0.5, 0.5));
        transform(definition.getTransformMatrix());
        prepareSurfaces();
        workOutVertexNormals();
    }

    private void splitTriangle(Vertex a, Vertex b, Vertex c, int steps, Map<Integer, Vertex> map, VertexFactory vertexFactory) {
        Vertex mab = getMiddle(a, b, map, vertexFactory);
        Vertex mbc = getMiddle(b, c, map, vertexFactory);
        Vertex mac = getMiddle(a, c, map, vertexFactory);
        if (steps <= 1) {
            surfaces.add(new Surface(a, mab, mac));
            surfaces.add(new Surface(b, mbc, mab));
            surfaces.add(new Surface(c, mac, mbc));
            surfaces.add(new Surface(mab, mbc, mac));
        } else {
            splitTriangle(a, mab, mac, steps - 1, map, vertexFactory);
            splitTriangle(b, mbc, mab, steps - 1, map, vertexFactory);
            splitTriangle(c, mac, mbc, steps - 1, map, vertexFactory);
            splitTriangle(mab, mbc, mac, steps - 1, map, vertexFactory);
        }
    }

    private Vertex getMiddle(Vertex a, Vertex b, Map<Integer, Vertex> map, VertexFactory vertexFactory) {
        int key = (a.id << 16) + b.id;
        Vertex v = map.get(key);
        if (v == null) {
            v = vertexFactory.build(Point3D.getMiddle(a.p, b.p));
            map.put(key, v);
        }
        return v;
    }
}
