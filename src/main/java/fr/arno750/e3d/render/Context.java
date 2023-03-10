package fr.arno750.e3d.render;

import fr.arno750.e3d.base.Matrix;
import fr.arno750.e3d.base.Point3D;

public class Context {

    /**
     * Number of pixels per meter deduced from a 27" wide screen with a width of 62
     * cm for 3840 pixels.
     */
    public static final int PIXEL_PER_METER = (int) (1.0 / 0.62 * 3840.0);

    /**
     * Distance between the observer's eye and the projection screen (i.e. the
     * computer screen)
     */
    public double focal = 0.75;

    public double resolution = PIXEL_PER_METER;
    public int halfWidth, halfHeight;

    public double distance;

    /**
     * Default observer's position is ten meters behind the scene
     */
    public Point3D observer = new Point3D(2, 2, -7);

    public double alpha;
    public double beta;
    public double gamma;

    public Matrix transform;

    public SurfaceType surfaceType = SurfaceType.OPAQUE;
    public boolean hiddenSurfaceRemoval = true;
    public boolean axis = true;
    public boolean normal = false;
}
