package fr.arno750.e3d.base;

public class VertexFactory {
    int nextId = 0;

    public Vertex build(double x, double y, double z) {
        return build(new Point3D(x, y, z));
    }

    public Vertex build(Point3D p) {
        return new Vertex(nextId++, p);
    }
}
