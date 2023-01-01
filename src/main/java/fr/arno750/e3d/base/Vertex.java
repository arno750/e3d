package fr.arno750.e3d.base;

import java.util.Objects;

public class Vertex {

    public int id;
    public Point3D p;
    public Vector3D n;

    public Vertex() {
    }

    /**
     * Constructs a vertex.
     *
     * @param id identifier
     * @param p  a point.
     */
    public Vertex(int id, Point3D p) {
        this.id = id;
        this.p = p;
    }

    /**
     * Constructs a vertex.
     *
     * @param id identifier
     * @param x  first coordinate.
     * @param y  second coordinate.
     * @param z  third coordinate.
     */
    public Vertex(int id, double x, double y, double z) {
        this.id = id;
        this.p = new Point3D(x, y, z);
    }

    @Override
    public String toString() {
        return p.toString();
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
