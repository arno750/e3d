package fr.arno750.e3d.base;

import fr.arno750.e3d.base.config.VolumeDefinition;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Volume {

    protected String name;
    protected List<Vertex> vertices = new ArrayList<>();
    protected List<Surface> surfaces = new ArrayList<>();
    protected Point3D center;
    protected double radius;

    /**
     * @param definition
     * @param vertexFactory
     */
    public Volume(VolumeDefinition definition, VertexFactory vertexFactory) {
        name = definition.getName();
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
     * Works out a bounding sphere.
     * <p/>
     * Retrieves the minimum and maximum values on each coordinate.
     * Defines the center of the bounding sphere as the middle of minimum and maximum points and the radius as half the distance between minimum and maximum points (here calculated as the distance between the center and the minimum point)
     * <p/>
     * The sphere defined by the center and radius is in effect a bounding sphere for the volume. It is not the optimum bounding sphere. This algorithm runs in time O(n) on inputs consisting of n points.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Smallest-circle_problem">Smallest-circle problem</a>
     * @see <a href="https://en.wikipedia.org/wiki/Bounding_sphere">Bounding sphere</a>
     */
    public void workOutBoundingSphere() {
        Point3D min = new Point3D(vertices.get(0).p);
        Point3D max = new Point3D(vertices.get(0).p);
        for (Vertex v : vertices) {
            min.x = Math.min(min.x, v.p.x);
            min.y = Math.min(min.y, v.p.y);
            min.z = Math.min(min.z, v.p.z);
            max.x = Math.max(max.x, v.p.x);
            max.y = Math.max(max.y, v.p.y);
            max.z = Math.max(max.z, v.p.z);
        }
        this.center = Point3D.getMiddle(min, max);
        this.radius = this.center.getDistance(min);
    }

    /**
     * Works out a bounding sphere using Ritter's algorithm.
     * <p/>
     * This sphere is not the optimum bounding sphere but is usually close. This algorithm runs in time O(3n) on inputs consisting of n points in 3-dimensional space.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Bounding_sphere">Bounding sphere</a>
     */
    public void workOutRitterBoundingSphere() {
        Vertex x = vertices.get(0);
        Vertex y = getFurthestVertex(x);
        Vertex z = getFurthestVertex(y);
        this.center = Point3D.getMiddle(y.p, z.p);
        this.radius = y.p.getDistance(z.p) / 2.0;
        double squareRadius = this.radius * this.radius;

        for (Vertex v : vertices) {
            double squaredDistance = v.p.getSquaredDistance(center);
            if (squaredDistance <= squareRadius)
                continue;

            double distance = Math.sqrt(squaredDistance);
            this.radius = (this.radius + distance) / 2.0;
            double weight = distance - radius;
            this.center.x = (this.center.x * radius + v.p.x * weight) / distance;
            this.center.y = (this.center.y * radius + v.p.y * weight) / distance;
            this.center.z = (this.center.z * radius + v.p.z * weight) / distance;
        }
    }

    private Vertex getFurthestVertex(Vertex v0) {
        double maximum = 0;
        Vertex furthest = vertices.get(0);
        for (Vertex v : vertices) {
            double squaredDistance = v.p.getSquaredDistance(v0.p);
            if (squaredDistance > maximum) {
                maximum = squaredDistance;
                furthest = v;
            }
        }
        return furthest;
    }


    /**
     * @param steps
     * @param coefficients
     * @param data
     * @param indices
     * @maram vertexFactory
     */
    public void addBezierPatch(int steps, double[][] coefficients, double[][] data, int[] indices, VertexFactory vertexFactory) {
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

                vertices.add(vertexFactory.build(p));
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
