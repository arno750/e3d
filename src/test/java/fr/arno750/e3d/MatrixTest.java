package fr.arno750.e3d;

import fr.arno750.e3d.base.Matrix;
import fr.arno750.e3d.base.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixTest {

    void setZeroBelow(Point3D p, double delta) {
        if (Math.abs(p.x) < delta)
            p.x = 0;
        if (Math.abs(p.y) < delta)
            p.y = 0;
        if (Math.abs(p.z) < delta)
            p.z = 0;
    }

    @Test
    void whenGetNull() {
        Matrix n = Matrix.getNull();
        assertEquals(0, n.getDeterminant());
        Matrix m = new Matrix(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        assertEquals(n, Matrix.multiply(n, m));
        assertEquals(n, Matrix.multiply(m, n));
        double d = m.getDeterminant();
        m.add(n);
        assertEquals(d, m.getDeterminant());
    }

    @Test
    void whenGetIdentity() {
        Matrix i = Matrix.getIdentity();
        assertEquals(1, i.getDeterminant());
        Matrix m = new Matrix(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        assertEquals(m, Matrix.multiply(i, m));
        assertEquals(m, Matrix.multiply(m, i));
    }

    @Test
    void whenGetTranslation() {
        Matrix t = Matrix.getTranslation(1, -2, 3);
        Point3D p = Matrix.multiply(Point3D.ORIGIN, t);
        assertEquals(1, p.x);
        assertEquals(-2, p.y);
        assertEquals(3, p.z);

        Matrix ti = Matrix.getTranslation(-1, 2, -3);
        Matrix m = Matrix.multiply(t, ti);
        assertEquals(Matrix.getIdentity(), m);
    }

    @Test
    void whenGetScaling() {
        Matrix s = Matrix.getScaling(1, -2, 3);
        Point3D p = Matrix.multiply(new Point3D(1, 1, 1), s);
        assertEquals(1, p.x);
        assertEquals(-2, p.y);
        assertEquals(3, p.z);

        Matrix si = Matrix.getScaling(1, -1.0 / 2.0, 1.0 / 3.0);
        Matrix m = Matrix.multiply(s, si);
        assertEquals(Matrix.getIdentity(), m);
    }

    @Test
    void whenGetXRotation() {
        Matrix rx = Matrix.getXRotation(Math.PI / 2.0);
        Point3D p = Matrix.multiply(Point3D.Y_UNIT, rx);
        setZeroBelow(p, 0.00001);
        // π/2 rotation around X axis moves Y to Z
        assertEquals(Point3D.Z_UNIT, p);

        Matrix rxi = Matrix.getXRotation(-Math.PI / 2.0);
        Matrix m = Matrix.multiply(rx, rxi);
        assertEquals(Matrix.getIdentity(), m);
    }

    @Test
    void whenGetYRotation() {
        Matrix ry = Matrix.getYRotation(Math.PI / 2.0);
        Point3D p = Matrix.multiply(Point3D.Z_UNIT, ry);
        setZeroBelow(p, 0.00001);
        // π/2 rotation around Y axis moves Z to X
        assertEquals(Point3D.X_UNIT, p);

        Matrix ryi = Matrix.getYRotation(-Math.PI / 2.0);
        Matrix m = Matrix.multiply(ry, ryi);
        assertEquals(Matrix.getIdentity(), m);
    }

    @Test
    void whenGetZRotation() {
        Matrix rz = Matrix.getZRotation(Math.PI / 2.0);
        Point3D p = Matrix.multiply(Point3D.X_UNIT, rz);
        setZeroBelow(p, 0.00001);
        // π/2 rotation around Z axis moves X to Y
        assertEquals(Point3D.Y_UNIT, p);

        Matrix rzi = Matrix.getZRotation(-Math.PI / 2.0);
        Matrix m = Matrix.multiply(rz, rzi);
        assertEquals(Matrix.getIdentity(), m);
    }
}
