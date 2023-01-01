package fr.arno750.e3d.base.volume;

import fr.arno750.e3d.base.VertexFactory;
import fr.arno750.e3d.base.Volume;
import fr.arno750.e3d.base.config.VolumeDefinition;

public class BezierPatch extends Volume {

    public static final boolean DEBUG = false;
    static final int[][] PATCHES = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
            13, 14, 15, 16}};

    static final double[][] VERTICES = {{0.0, 0.0, 0.0},
            {-3.0, 0.0, -3.0}, {-1.0, 0.0, -3.0}, {1.0, 0.0, -3.0},
            {3.0, 0.0, -3.0}, {-3.0, 0.0, -1.0}, {-3.0, 3.0, -3.0},
            {3.0, 3.0, -3.0}, {3.0, 0.0, -1.0}, {-3.0, 0.0, 1.0},
            {-3.0, 3.0, 3.0}, {3.0, 3.0, 3.0}, {3.0, 0.0, 1.0},
            {-3.0, 0.0, 3.0}, {-1.0, 0.0, 3.0}, {1.0, 0.0, 3.0},
            {3.0, 0.0, 3.0}};

    final double[][] coefficients;

    /**
     * @param definition
     * @param vertexFactory
     */
    public BezierPatch(VolumeDefinition definition, VertexFactory vertexFactory) {
        super(definition, vertexFactory);

        coefficients = getBernsteinBasisFunction(definition.getParameters().getSteps());
        for (int[] patch : PATCHES)
            addBezierPatch(definition.getParameters().getSteps(), coefficients, VERTICES, patch, vertexFactory);

        transform(definition.getTransformMatrix());
        prepareSurfaces();
        workOutVertexNormals();
    }
}
