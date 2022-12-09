package e3d.base;

public class Matrix {

    public double m00, m10, m20, m30;
    public double m01, m11, m21, m31;
    public double m02, m12, m22, m32;
    public double m03, m13, m23, m33;

    /**
     *
     */
    public Matrix() {
    }

    /**
     * @param m00
     * @param m10
     * @param m20
     * @param m30
     * @param m01
     * @param m11
     * @param m21
     * @param m31
     * @param m02
     * @param m12
     * @param m22
     * @param m32
     * @param m03
     * @param m13
     * @param m23
     * @param m33
     */
    public Matrix(double m00, double m10, double m20, double m30, double m01, double m11, double m21, double m31,
                  double m02, double m12, double m22, double m32, double m03, double m13, double m23, double m33) {
        this.m00 = m00;
        this.m10 = m10;
        this.m20 = m20;
        this.m30 = m30;
        this.m01 = m01;
        this.m11 = m11;
        this.m21 = m21;
        this.m31 = m31;
        this.m02 = m02;
        this.m12 = m12;
        this.m22 = m22;
        this.m32 = m32;
        this.m03 = m03;
        this.m13 = m13;
        this.m23 = m23;
        this.m33 = m33;
    }

    /**
     * Returns the identity matrix with ones on the main diagonal and zeros elsewhere.
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
     * Returns a translation matrix. It is based on an identity matrix with values on the last column.
     *
     * @param tx x-axis parameter.
     * @param ty y-axis parameter.
     * @param tz z-axis parameter.
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
     * Returns a scaling matrix. It is an identity matrix with values on the main diagonal.
     *
     * @param sx x-axis parameter.
     * @param sy y-axis parameter.
     * @param sz z-axis parameter.
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
     * @param alpha an angle, in radians.
     * @return
     */
    public static Matrix getElevationRotation(double alpha) {
        Matrix m = getIdentity();
        m.m11 = Math.cos(alpha);
        m.m21 = -Math.sin(alpha);
        m.m12 = Math.sin(alpha);
        m.m22 = Math.cos(alpha);
        return m;
    }

    /**
     * @param beta an angle, in radians.
     * @return
     */
    public static Matrix getHeadingRotation(double beta) {
        Matrix m = getIdentity();
        m.m00 = Math.cos(beta);
        m.m20 = Math.sin(beta);
        m.m02 = -Math.sin(beta);
        m.m22 = Math.cos(beta);

        return m;
    }

    /**
     * @param gamma an angle, in radians.
     * @return
     */
    public static Matrix getBankRotation(double gamma) {
        Matrix m = getIdentity();
        m.m00 = Math.cos(gamma);
        m.m10 = Math.sin(gamma);
        m.m01 = -Math.sin(gamma);
        m.m11 = Math.cos(gamma);
        return m;
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
            throw new IllegalArgumentException("Illegal argument : negative scaling value");

        Matrix mt = Matrix.getTranslation(tx, ty, tz);
        Matrix mra = Matrix.getElevationRotation(alpha);
        Matrix mrb = Matrix.getBankRotation(beta);
        Matrix mrh = Matrix.getHeadingRotation(gamma);
        Matrix ms = Matrix.getScaling(sx, sy, sz);

        return Matrix.multiply(mra, Matrix.multiply(mrb, Matrix.multiply(mrh, Matrix.multiply(mt, ms))));
    }

    /**
     * @param deg
     * @return
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
     * @param a
     * @param b
     * @return
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix ab = new Matrix();
        ab.m00 = a.m00 * b.m00 + a.m10 * b.m01 + a.m20 * b.m02 + a.m30 * b.m03;
        ab.m01 = a.m01 * b.m00 + a.m11 * b.m01 + a.m21 * b.m02 + a.m31 * b.m03;
        ab.m02 = a.m02 * b.m00 + a.m12 * b.m01 + a.m22 * b.m02 + a.m32 * b.m03;
        ab.m03 = a.m03 * b.m00 + a.m13 * b.m01 + a.m23 * b.m02 + a.m33 * b.m03;
        ab.m10 = a.m00 * b.m10 + a.m10 * b.m11 + a.m20 * b.m12 + a.m30 * b.m13;
        ab.m11 = a.m01 * b.m10 + a.m11 * b.m11 + a.m21 * b.m12 + a.m31 * b.m13;
        ab.m12 = a.m02 * b.m10 + a.m12 * b.m11 + a.m22 * b.m12 + a.m32 * b.m13;
        ab.m13 = a.m03 * b.m10 + a.m13 * b.m11 + a.m23 * b.m12 + a.m33 * b.m13;
        ab.m20 = a.m00 * b.m20 + a.m10 * b.m21 + a.m20 * b.m22 + a.m30 * b.m23;
        ab.m21 = a.m01 * b.m20 + a.m11 * b.m21 + a.m21 * b.m22 + a.m31 * b.m23;
        ab.m22 = a.m02 * b.m20 + a.m12 * b.m21 + a.m22 * b.m22 + a.m32 * b.m23;
        ab.m23 = a.m03 * b.m20 + a.m13 * b.m21 + a.m23 * b.m22 + a.m33 * b.m23;
        ab.m30 = a.m00 * b.m30 + a.m10 * b.m31 + a.m20 * b.m32 + a.m30 * b.m33;
        ab.m31 = a.m01 * b.m30 + a.m11 * b.m31 + a.m21 * b.m32 + a.m31 * b.m33;
        ab.m32 = a.m02 * b.m30 + a.m12 * b.m31 + a.m22 * b.m32 + a.m32 * b.m33;
        ab.m33 = a.m03 * b.m30 + a.m13 * b.m31 + a.m23 * b.m32 + a.m33 * b.m33;
        return ab;
    }

    /**
     * @param p
     * @param m
     * @return
     */
    public static Point3D multiply(Point3D p, Matrix m) {
        Point3D tp = new Point3D();
        tp.x = m.m00 * p.x + m.m10 * p.y + m.m20 * p.z + m.m30;
        tp.y = m.m01 * p.x + m.m11 * p.y + m.m21 * p.z + m.m31;
        tp.z = m.m02 * p.x + m.m12 * p.y + m.m22 * p.z + m.m32;
        return tp;
    }

    /**
     * @param v
     * @param m
     * @return
     */
    public static Vector3D multiply(Vector3D v, Matrix m) {
        Vector3D tv = new Vector3D();
        tv.i = m.m00 * v.i + m.m10 * v.j + m.m20 * v.k;
        tv.j = m.m01 * v.i + m.m11 * v.j + m.m21 * v.k;
        tv.k = m.m02 * v.i + m.m12 * v.j + m.m22 * v.k;
        return tv;
    }

    /**
     * Adds this matrix and the specified one.
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
                m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33);
    }
}
