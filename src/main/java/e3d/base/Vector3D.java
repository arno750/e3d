package e3d.base;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Vector3D {

    public static final Vector3D I_AXIS = new Vector3D(1, 0, 0);
    public static final Vector3D J_AXIS = new Vector3D(0, 1, 0);
    public static final Vector3D K_AXIS = new Vector3D(0, 0, 1);

    public double i, j, k;

    public Vector3D() {
    }

    /**
     * @param i x-axis component.
     * @param j y-axis component.
     * @param k z-axis component.
     */
    public Vector3D(double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    /**
     * @param a the initial point.
     * @param b the terminal point.
     */
    public Vector3D(@NotNull Point3D a, @NotNull Point3D b) {
        this.i = b.x - a.x;
        this.j = b.y - a.y;
        this.k = b.z - a.z;
    }

    /**
     * Returns the cross product of the two specified vectors.
     *
     * @param a the first vector.
     * @param b the second vector.
     * @return the cross product as a new vector.
     */
    public static Vector3D getCrossProduct(Vector3D a, Vector3D b) {
        Vector3D ab = new Vector3D();
        ab.i = a.j * b.k - a.k * b.j;
        ab.j = a.k * b.i - a.i * b.k;
        ab.k = a.i * b.j - a.j * b.i;
        return ab;
    }

    /**
     * Returns the cross product of two vectors specified by a common initial point and two terminal points.
     *
     * @param a the common initial point.
     * @param b the terminal point of the first vector.
     * @param c the terminal point of the second vector.
     * @return the cross product as a new vector.
     */
    public static Vector3D getCrossProduct(Point3D a, Point3D b, Point3D c) {
        Vector3D abac = new Vector3D();
        abac.i = (b.y - a.y) * (c.z - a.z) - (b.z - a.z) * (c.y - a.y);
        abac.j = (b.z - a.z) * (c.x - a.x) - (b.x - a.x) * (c.z - a.z);
        abac.k = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        return abac;
    }

    /**
     * Indicates whether this is a zero vector.
     *
     * @return <tt>true</tt> if it denotes a zero (or null) vector ; <tt>false</tt> otherwise.
     */
    public boolean isZero() {
        return (i == 0) && (j == 0) && (k == 0);
    }

    /**
     * Returns the length (or magnitude) of the vector. It is defined as its Euclidean norm.
     *
     * @return the length of the vector.
     */
    public double getLength() {
        return Math.sqrt(i * i + j * j + k * k);
    }

    /**
     * Reverses the initial and terminal points by multiplying the vector components by -1.
     *
     * @return itself.
     */
    public Vector3D reverse() {
        i *= -1;
        j *= -1;
        k *= -1;
        return this;
    }

    /**
     * Normalizes the vector by dividing each component by the current vector length.
     * The new vector size will be 1.0. The direction will not be changed.
     *
     * @return itself.
     */
    public Vector3D normalize() {
        double length = getLength();
        i /= length;
        j /= length;
        k /= length;
        return this;
    }

    /**
     * Multiplies the matrix and this vector. The fourth row of the matric is not used.
     *
     * @param m a matrix.
     * @return itself.
     */
    public Vector3D multiply(Matrix m) {
        double ti = m.m00 * i + m.m10 * j + m.m20 * k + m.m30;
        double tj = m.m01 * i + m.m11 * j + m.m21 * k + m.m31;
        double tk = m.m02 * i + m.m12 * j + m.m22 * k + m.m32;
        this.i = ti;
        this.j = tj;
        this.k = tk;
        return this;
    }

    /**
     * Returns the dot product of this vector and another vector.
     *
     * @param v another vector.
     * @return the dot product.
     */
    public double getDotProduct(Vector3D v) {
        return i * v.i + j * v.j + k * v.k;
    }

    @Override
    public String toString() {
        return String.format("[%.1f, %.1f, %.1f]", i, j, k);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3D vector3D = (Vector3D) o;

        if (Double.compare(vector3D.i, i) != 0) return false;
        if (Double.compare(vector3D.j, j) != 0) return false;
        return Double.compare(vector3D.k, k) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(i);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(j);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(k);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
