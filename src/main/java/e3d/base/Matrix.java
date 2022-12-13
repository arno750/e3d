package e3d.base;

public class Matrix {

    public double m00, m01, m02, m03;
    public double m10, m11, m12, m13;
    public double m20, m21, m22, m23;
    public double m30, m31, m32, m33;

    public Matrix() {
    }

    /**
     * Constructs a new 4x4 matrix.
     *
     * @param m00 component row #0 column #0
     * @param m01 component row #0 column #1
     * @param m02 component row #0 column #2
     * @param m03 component row #0 column #3
     * @param m10 component row #1 column #0
     * @param m11 component row #1 column #1
     * @param m12 component row #1 column #2
     * @param m13 component row #1 column #3
     * @param m20 component row #2 column #0
     * @param m21 component row #2 column #1
     * @param m22 component row #2 column #2
     * @param m23 component row #2 column #3
     * @param m30 component row #3 column #0
     * @param m31 component row #3 column #1
     * @param m32 component row #3 column #2
     * @param m33 component row #3 column #3
     */
    public Matrix(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    /**
     * Returns the identity matrix with ones on the main diagonal and zeros
     * elsewhere.
     *
     * @return the identity matrix.
     */
    public static Matrix getIdentity() {
        Matrix m = new Matrix();
        m.m00 = 1;
        m.m11 = 1;
        m.m22 = 1;
        m.m33 = 1;
        return m;
    }

    /**
     * Returns a translation matrix. It is based on an identity matrix with displacements
     * on the last row.
     *
     * @param tx x displacement.
     * @param ty y displacement.
     * @param tz z displacement.
     * @return the translation matrix.
     */
    public static Matrix getTranslation(double tx, double ty, double tz) {
        Matrix m = getIdentity();
        m.m30 = tx;
        m.m31 = ty;
        m.m32 = tz;
        return m;
    }

    /**
     * Returns a scaling matrix. It is an identity matrix with scaling factors on the main
     * diagonal.
     *
     * @param sx x scaling factor.
     * @param sy y scaling factor.
     * @param sz z scaling factor.
     * @return the scaling matrix.
     */
    public static Matrix getScaling(double sx, double sy, double sz) {
        Matrix m = getIdentity();
        m.m00 = sx;
        m.m11 = sy;
        m.m22 = sz;
        return m;
    }

    /**
     * Returns a rotation matrix around the X axis. It is an identity matrix with rotations on YZ coordinates.
     *
     * @param theta an angle, in radians.
     * @return the rotation matrix.
     */
    public static Matrix getXRotation(double theta) {
        Matrix m = getIdentity();
        m.m11 = Math.cos(theta);
        m.m12 = Math.sin(theta);
        m.m21 = -Math.sin(theta);
        m.m22 = Math.cos(theta);
        return m;
    }

    /**
     * Returns a rotation matrix around the Y axis. It is an identity matrix with rotations on XZ coordinates.
     *
     * @param theta an angle, in radians.
     * @return the rotation matrix.
     */
    public static Matrix getYRotation(double theta) {
        Matrix m = getIdentity();
        m.m00 = Math.cos(theta);
        m.m02 = -Math.sin(theta);
        m.m20 = Math.sin(theta);
        m.m22 = Math.cos(theta);

        return m;
    }

    /**
     * Returns a rotation matrix around the Z axis. It is an identity matrix with rotations on XY coordinates.
     *
     * @param theta an angle, in radians.
     * @return the rotation matrix.
     */
    public static Matrix getZRotation(double theta) {
        Matrix m = getIdentity();
        m.m00 = Math.cos(theta);
        m.m01 = Math.sin(theta);
        m.m10 = -Math.sin(theta);
        m.m11 = Math.cos(theta);
        return m;
    }


    /**
     * Converts an angle in degrees to an angle in radians.
     *
     * @param deg an angle, in degrees.
     * @return the angle, in radians.
     */
    private static double deg2rad(double deg) {
        return deg / 180.0 * Math.PI;
    }

    /**
     * @param tx
     * @param ty
     * @param tz
     * @param sx
     * @param sy
     * @param sz
     * @param alpha
     * @param beta
     * @param gamma
     * @return
     */
    public static Matrix getTransformRad(double tx, double ty, double tz, double sx, double sy, double sz, double alpha,
                                         double beta, double gamma) {

        return getTransform(tx, ty, tz, sx, sy, sz, deg2rad(alpha), deg2rad(beta), deg2rad(gamma));
    }

    /**
     * @param tx
     * @param ty
     * @param tz
     * @param sx
     * @param sy
     * @param sz
     * @param alpha
     * @param beta
     * @param gamma
     * @return
     */
    public static Matrix getTransform(double tx, double ty, double tz, double sx, double sy, double sz, double alpha,
                                      double beta, double gamma) {

        if ((sx < 0) || (sy < 0) || (sz < 0))
            throw new IllegalArgumentException("Illegal argument: negative scaling value");

        Matrix mt = Matrix.getTranslation(tx, ty, tz);
        Matrix mrx = Matrix.getXRotation(alpha);
        Matrix mry = Matrix.getYRotation(gamma);
        Matrix mrz = Matrix.getZRotation(beta);
        Matrix ms = Matrix.getScaling(sx, sy, sz);

        return Matrix.multiply(mt, Matrix.multiply(mry, Matrix.multiply(mrz, Matrix.multiply(mrx, ms))));
    }

    /**
     * Adds two 4x4 matrices A and B and returns the result as 4x4 matrix R.
     * <p>
     * <tt>R = A + B</tt>
     * </p>
     *
     * @param a
     * @param b
     * @return
     */
    public static Matrix add(Matrix a, Matrix b) {
        Matrix ab = new Matrix();
        ab.m00 = a.m00 + b.m00;
        ab.m01 = a.m01 + b.m01;
        ab.m02 = a.m02 + b.m02;
        ab.m03 = a.m03 + b.m03;
        ab.m10 = a.m10 + b.m10;
        ab.m11 = a.m11 + b.m11;
        ab.m12 = a.m12 + b.m12;
        ab.m13 = a.m13 + b.m13;
        ab.m20 = a.m20 + b.m20;
        ab.m21 = a.m21 + b.m21;
        ab.m22 = a.m22 + b.m22;
        ab.m23 = a.m23 + b.m23;
        ab.m30 = a.m30 + b.m30;
        ab.m31 = a.m31 + b.m31;
        ab.m32 = a.m32 + b.m32;
        ab.m33 = a.m33 + b.m33;
        return ab;
    }

    /**
     * Multiplies the 4x4 matrix A with 4x4 matrix B and returns the result as 4x4 matrix C:
     * <p>
     * <tt>C = A x B</tt>
     * </p>
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return a matrix
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix ab = new Matrix();
        ab.m00 = a.m00 * b.m00 + a.m01 * b.m10 + a.m02 * b.m20 + a.m03 * b.m30;
        ab.m01 = a.m00 * b.m01 + a.m01 * b.m11 + a.m02 * b.m21 + a.m03 * b.m31;
        ab.m02 = a.m00 * b.m02 + a.m01 * b.m12 + a.m02 * b.m22 + a.m03 * b.m32;
        ab.m03 = a.m00 * b.m03 + a.m01 * b.m13 + a.m02 * b.m23 + a.m03 * b.m33;
        ab.m10 = a.m10 * b.m00 + a.m11 * b.m10 + a.m12 * b.m20 + a.m13 * b.m30;
        ab.m11 = a.m10 * b.m01 + a.m11 * b.m11 + a.m12 * b.m21 + a.m13 * b.m31;
        ab.m12 = a.m10 * b.m02 + a.m11 * b.m12 + a.m12 * b.m22 + a.m13 * b.m32;
        ab.m13 = a.m10 * b.m03 + a.m11 * b.m13 + a.m12 * b.m23 + a.m13 * b.m33;
        ab.m20 = a.m20 * b.m00 + a.m21 * b.m10 + a.m22 * b.m20 + a.m23 * b.m30;
        ab.m21 = a.m20 * b.m01 + a.m21 * b.m11 + a.m22 * b.m21 + a.m23 * b.m31;
        ab.m22 = a.m20 * b.m02 + a.m21 * b.m12 + a.m22 * b.m22 + a.m23 * b.m32;
        ab.m23 = a.m20 * b.m03 + a.m21 * b.m13 + a.m22 * b.m23 + a.m23 * b.m33;
        ab.m30 = a.m30 * b.m00 + a.m31 * b.m10 + a.m32 * b.m20 + a.m33 * b.m30;
        ab.m31 = a.m30 * b.m01 + a.m31 * b.m11 + a.m32 * b.m21 + a.m33 * b.m31;
        ab.m32 = a.m30 * b.m02 + a.m31 * b.m12 + a.m32 * b.m22 + a.m33 * b.m32;
        ab.m33 = a.m30 * b.m03 + a.m31 * b.m13 + a.m32 * b.m23 + a.m33 * b.m33;
        return ab;
    }

    /**
     * Multiplies the point P with matrix M and returns the result as point R:
     * <p>
     * <tt>R = P x M</tt>
     * </p>
     * <p>
     * The point is a homogeneous coordinate [ x y z w ] with w always taken to be 1. This 1x4 vector and the whole 4x4 matrix are used to calculate the new point.
     *
     * @param p a point
     * @param m a matrix
     * @return a point
     */
    public static Point3D multiply(Point3D p, Matrix m) {
        Point3D tp = new Point3D();
        tp.x = p.x * m.m00 + p.y * m.m10 + p.z * m.m20 + m.m30;
        tp.y = p.x * m.m01 + p.y * m.m11 + p.z * m.m21 + m.m31;
        tp.z = p.x * m.m02 + p.y * m.m12 + p.z * m.m22 + m.m32;
        return tp;
    }

    /**
     * Multiplies the vector V with matrix M and returns the result as vector R:
     * <p>
     * <tt>R = V x M</tt>
     * </p>
     * <p>
     * The 1x3 vector [ i j k ] and the 3x3 upper left matrix are used to calculate the new vector.
     *
     * @param v a vector
     * @param m a matrix
     * @return a vector
     */
    public static Vector3D multiply(Vector3D v, Matrix m) {
        Vector3D tv = new Vector3D();
        tv.i = v.i * m.m00 + v.j * m.m10 + v.k * m.m20;
        tv.j = v.i * m.m01 + v.j * m.m11 + v.k * m.m21;
        tv.k = v.i * m.m02 + v.j * m.m12 + v.k * m.m22;
        return tv;
    }

    /**
     * Adds this matrix S and the specified 4x4 matrix M:
     * <p>
     * <tt>S += M</tt>
     * </p>
     *
     * @param m another matrix
     * @return itself
     */
    public Matrix add(Matrix m) {
        m00 += m.m00;
        m01 += m.m01;
        m02 += m.m02;
        m03 += m.m03;
        m10 += m.m10;
        m11 += m.m11;
        m12 += m.m12;
        m13 += m.m13;
        m20 += m.m20;
        m21 += m.m21;
        m22 += m.m22;
        m23 += m.m23;
        m30 += m.m30;
        m31 += m.m31;
        m32 += m.m32;
        m33 += m.m33;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "| %.1f %.1f %.1f %.1f |\n| %.1f %.1f %.1f %.1f |\n| %.1f %.1f %.1f %.1f |\n| %.1f %.1f %.1f %.1f |\n",
                m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
}
