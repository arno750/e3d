package fr.arno750.e3d.render;

import fr.arno750.e3d.base.Vector3D;

import java.awt.*;

public class RenderedSurface implements Comparable<RenderedSurface> {

    public final double z;
    public final Point a;
    public final Point b;
    public final Point c;
    public final Vector3D n;

    /**
     * @param z
     * @param a
     * @param b
     * @param c
     * @param n
     */
    public RenderedSurface(double z, Point a, Point b, Point c, Vector3D n) {
        this.z = z;
        this.a = a;
        this.b = b;
        this.c = c;
        this.n = n;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(RenderedSurface renderedSurface) {
        return -Double.compare(z, renderedSurface.z);
    }
}
