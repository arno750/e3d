package fr.arno750.e3d.base;

public class Point3D {

    public static final Point3D ORIGIN = new Point3D(0, 0, 0);
    public static final Point3D X_UNIT = new Point3D(1, 0, 0);
    public static final Point3D Y_UNIT = new Point3D(0, 1, 0);
    public static final Point3D Z_UNIT = new Point3D(0, 0, 1);

    public double x, y, z;

    public Point3D() {
    }

    /**
     * Constructs a 3D point.
     *
     * @param x x-axis coordinate.
     * @param y y-axis coordinate.
     * @param z z-axis coordinate.
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a 3D point from the specified model.
     *
     * @param p a 3D point.
     */
    public Point3D(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    /**
     * Constructs a 3D point as the terminal point for the specified initial point
     * and vector.
     *
     * @param p an initial point.
     * @param v a vector.
     */
    public Point3D(Point3D p, Vector3D v) {
        this.x = p.x + v.i;
        this.y = p.y + v.j;
        this.z = p.z + v.k;
    }

    /**
     * Constructs a 3D point as the terminal point for the specified vector (with
     * the origin as initial point).
     *
     * @param v a vector.
     */
    public Point3D(Vector3D v) {
        this.x = v.i;
        this.y = v.j;
        this.z = v.k;
    }

    /**
     * Returns the point located between two specified points.
     *
     * @param a the first point.
     * @param b the second point.
     * @return the middle point.
     */
    public static Point3D getMiddle(Point3D a, Point3D b) {
        return new Point3D((a.x + b.x) / 2.0, (a.y + b.y) / 2.0, (a.z + b.z) / 2.0);
    }

    /**
     * Returns the point located between three specified points.
     *
     * @param a the first point.
     * @param b the second point.
     * @param c the third point.
     * @return the middle point.
     */
    public static Point3D getMiddle(Point3D a, Point3D b, Point3D c) {
        return new Point3D((a.x + b.x + c.x) / 3.0, (a.y + b.y + c.y) / 3.0, (a.z + b.z + c.z) / 3.0);
    }

    /**
     * Multiplies this point P with matrix M:
     * <p>
     * <tt>P = P x M</tt>
     * </p>
     * <p>
     * The point is a homogeneous coordinate [ x y z w ] with w always taken to be
     * 1. This 1x4 vector and the whole 4x4 matrix are used to calculate the new
     * point.
     *
     * @param m a matrix.
     * @return itself.
     */
    public Point3D multiply(Matrix m) {
        double tx = x * m.m00 + y * m.m10 + z * m.m20 + m.m30;
        double ty = x * m.m01 + y * m.m11 + z * m.m21 + m.m31;
        double tz = x * m.m02 + y * m.m12 + z * m.m22 + m.m32;
        this.x = tx;
        this.y = ty;
        this.z = tz;
        return this;
    }

    /**
     * Returns the distance between this point and the origin.
     *
     * @return the calculated distance.
     */
    public double getDistanceFromOrigin() {
        return getDistance(ORIGIN);
    }

    /**
     * Returns the distance between this point and another point.
     *
     * @param a another point.
     * @return the calculated distance.
     */
    public double getDistance(Point3D a) {
        return Math.sqrt(getSquaredDistance(a));
    }

    /**
     * Returns the squared distance between this point and another point.
     *
     * @param a another point.
     * @return the calculated squared distance.
     */
    public double getSquaredDistance(Point3D a) {
        double dx = x - a.x;
        double dy = y - a.y;
        double dz = z - a.z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public String toString() {
        return String.format("[%.1f, %.1f, %.1f]", x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Point3D point3D = (Point3D) o;

        if (Double.compare(point3D.x, x) != 0)
            return false;
        if (Double.compare(point3D.y, y) != 0)
            return false;
        return Double.compare(point3D.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
