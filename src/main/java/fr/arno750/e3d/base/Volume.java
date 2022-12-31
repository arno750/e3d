package fr.arno750.e3d.base;

import fr.arno750.e3d.base.config.VolumeDefinition;

import java.util.ArrayList;
import java.util.List;

public class Volume {

    protected String name;
    protected List<Vertex> vertices = new ArrayList<>();
    protected List<Surface> surfaces = new ArrayList<>();

    public Volume(VolumeDefinition definition) {
        name = definition.getName();
    }

    /**
     * Returns the list of vertices.
     *
     * @return the vertices
     */
    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Returns the list of surfaces.
     *
     * @return the surfaces
     */
    public List<Surface> getSurfaces() {
        return surfaces;
    }

    /**
     * Returns the surface containing the two specified vertices and not being the given surface.
     *
     * @param gs surface
     * @param v1 first vertex
     * @param v2 second vertex
     * @return the connected surface
     */
    public Surface getConnectedSurface(Surface gs, Vertex v1, Vertex v2) {
        for (Surface s : surfaces)
            if (s.contains(v1) || s.contains(v2))
                if (s != gs) // tested afterwards as it seldom happens
                    return gs;

        return null;
    }

    /**
     * Transforms the volume using the specified matrix. The matrix is applied (by multiplication) on all the vertices.
     *
     * @param transform
     */
    protected void transform(Matrix transform) {
        for (Vertex v : vertices) {
            v.p.multiply(transform);
        }
    }

    /**
     * Prepares the surfaces by computing middle point and normal vector.
     * <p>
     * If a center point is specified, the surface will also be arranged to point towards the outside. This feature works only with convex volume.
     *
     * @param center the volume center
     */
    protected void prepareSurfaces(Point3D center) {
        for (Surface s : surfaces) {
            s.computeMiddleAndNormal();
            if (center != null)
                s.pointOutside(center);
        }
    }

    /**
     * Prepares the surfaces by computing middle point and normal vector.
     */
    protected void prepareSurfaces() {
        prepareSurfaces(null);
    }

    /**
     *
     */
    protected void workOutVertexNormals() {
        for (Vertex v : vertices) {
            v.n = new Vector3D();
        }
        for (Surface s : surfaces) {
            double area = s.getArea();
            s.a.n.addVector(s.n, area);
            s.b.n.addVector(s.n, area);
            s.c.n.addVector(s.n, area);
        }
        for (Vertex v : vertices) {
            v.n.normalize();
        }
    }


    /**
     * @param steps
     * @param coefficients
     * @param data
     * @param indices
     */
    public void addBezierPatch(int steps, double[][] coefficients, double[][] data, int[] indices) {
        int start = vertices.size();
        double coefficient;
        for (int u = 0; u < steps; u++)
            for (int v = 0; v < steps; v++) {

                Point3D p = new Point3D();
                int k = 0;
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++) {
                        int vi = indices[k];
                        coefficient = coefficients[i][u] * coefficients[j][v];
                        p.x += data[vi][0] * coefficient;
                        p.y += data[vi][2] * coefficient;
                        p.z += data[vi][1] * coefficient;
                        k++;
                    }

                vertices.add(new Vertex(p));
            }

        for (int u = 0; u < steps - 1; u++)
            for (int v = 0; v < steps - 1; v++) {
                int a = start + u * steps + v;
                int b = a + 1;
                int c = a + steps;
                int d = c + 1;
                surfaces.add(new Surface(vertices.get(a), vertices.get(c), vertices.get(b)));
                surfaces.add(new Surface(vertices.get(b), vertices.get(c), vertices.get(d)));
            }
    }

    /**
     * @param steps
     * @return
     */
    public double[][] getBernsteinBasisFunction(int steps) {
        double[][] coefficients = new double[4][steps];
        for (int i = 0; i < steps; i++) {
            double u = i / (double) (steps - 1);
            double u2 = u * u;
            double v = 1.0 - u;
            double v2 = v * v;

            coefficients[0][i] = v * v2;
            coefficients[1][i] = 3 * u * v2;
            coefficients[2][i] = 3 * u2 * v;
            coefficients[3][i] = u * u2;
        }

        return coefficients;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("- name: %s\n  surfaces:\n", name));
        for (Surface s : surfaces) {
            buffer.append(String.format("  - A%s B%s C%s\n", s.a.p, s.b.p, s.c.p));
        }
        return buffer.toString();
    }
}
