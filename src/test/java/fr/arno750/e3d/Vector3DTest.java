package fr.arno750.e3d;

import fr.arno750.e3d.base.Matrix;
import fr.arno750.e3d.base.Point3D;
import fr.arno750.e3d.base.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3DTest {

    @Test
    void givenPoint3D_whenConstruct() {
        Vector3D v = new Vector3D(new Point3D(0, 1, 2), new Point3D(3, 5, 7));
        assertEquals(3, v.i);
        assertEquals(4, v.j);
        assertEquals(5, v.k);
    }

    @Test
    void whenIsZero() {
        assertTrue(new Vector3D().isZero());
        assertFalse(new Vector3D(1, 2, 3).isZero());
    }

    @Test
    void whenGetLength() {
        assertEquals(new Vector3D(1, 2, 3).getLength(), Math.sqrt(1 + 4 + 9));
    }

    @Test
    void whenReverseDirection() {
        Vector3D v = new Vector3D(1, 2, 3);
        v.reverse();
        assertEquals(-1, v.i);
        assertEquals(-2, v.j);
        assertEquals(-3, v.k);
    }

    @Test
    void whenNormalize() {
        Vector3D v = new Vector3D(1, 2, 3);
        double l = v.getLength();
        v.normalize();
        assertEquals(1 / l, v.i);
        assertEquals(2 / l, v.j);
        assertEquals(3 / l, v.k);
        assertEquals(1, v.getLength());
    }

    @Test
    void whenSetlength() {
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(1, 2, 3);
        v2.setLength(10.0);
        assertEquals(10.0, v2.getLength());
        assertTrue(Vector3D.getCrossProduct(v1, v2).isZero());
    }

    @Test
    void givenZeroVector_whenNormalize() {
        Vector3D v = new Vector3D();
        v.normalize();
        assertTrue(Double.isNaN(v.i));
        assertTrue(Double.isNaN(v.j));
        assertTrue(Double.isNaN(v.k));
    }

    @Test
    void whenMultiply() {
        Vector3D v = new Vector3D(1, 1000, 77777);
        Matrix m = new Matrix(5, 0, 0, 0, 7, 1, 0, 0, 2, 0, 1, 0, 3, 0, 0, 0);
        v.multiply(m);
        assertEquals(5 + 1000 * 7 + 77777 * 2 + 3, v.i);
        assertEquals(1000, v.j);
        assertEquals(77777, v.k);
    }

}
