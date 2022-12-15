package fr.arno750.e3d.base;

import java.util.Objects;

public class Vertex {

    public Point3D p;
    public Vector3D n;

    public Vertex() {
    }

    /**
     * Constructs a vertex.
     *
     * @param p a point.
     */
    public Vertex(Point3D p) {
        this.p = p;
    }

    /**
     * Constructs a vertex.
     *
     * @param x first coordinate.
     * @param y second coordinate.
     * @param z third coordinate.
     */
    public Vertex(double x, double y, double z) {
        this.p = new Point3D(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(p, vertex.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
