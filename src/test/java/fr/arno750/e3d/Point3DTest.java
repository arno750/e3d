package fr.arno750.e3d;

import fr.arno750.e3d.base.Matrix;
import fr.arno750.e3d.base.Point3D;
import fr.arno750.e3d.base.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Point3DTest {

    @Test
    void givenPoint3D_whenConstruct() {
        Point3D p = new Point3D(0, 1, 2);
        Vector3D v = new Vector3D(-5, 7, 100);
        Point3D r = new Point3D(p, v);
        assertEquals(-5, r.x);
        assertEquals(1 + 7, r.y);
        assertEquals(2 + 100, r.z);
    }

    @Test
    void whenMultiply() {
        Point3D p = new Point3D(1, 1000, 77777);
        Matrix m = new Matrix(5, 0, 0, 0, 7, 1, 0, 0, 2, 0, 1, 0, 3, 0, 0, 0);
        p.multiply(m);
        assertEquals(5 + 1000 * 7 + 77777 * 2 + 3, p.x);
        assertEquals(1000, p.y);
        assertEquals(77777, p.z);
    }

    @Test
    void getDistanceFromOrigin() {
        assertEquals(77, new Point3D(77, 0, 0).getDistanceFromOrigin());
        assertEquals(55, new Point3D(0, 55, 0).getDistanceFromOrigin());
        assertEquals(11, new Point3D(0, 0, 11).getDistanceFromOrigin());
        assertEquals(Math.sqrt(14.0), new Point3D(1, 2, 3).getDistanceFromOrigin());
    }

    @Test
    void whenGetDistance() {
        assertEquals(22, new Point3D(77, 0, 0).getDistance(new Point3D(55, 0, 0)));
        assertEquals(44, new Point3D(0, 55, 0).getDistance(new Point3D(0, 11, 0)));
        assertEquals(66, new Point3D(0, 0, 11).getDistance(new Point3D(0, 0, 77)));
        assertEquals(Math.sqrt(3 * 3 + 3 * 3 + 3 * 3), new Point3D(1, 2, 3).getDistance(new Point3D(4, 5, 6)));
    }

    @Test
    void whenGetSquaredDistance() {
        assertEquals(22 * 22, new Point3D(77, 0, 0).getSquaredDistance(new Point3D(55, 0, 0)));
        assertEquals(44 * 44, new Point3D(0, 55, 0).getSquaredDistance(new Point3D(0, 11, 0)));
        assertEquals(66 * 66, new Point3D(0, 0, 11).getSquaredDistance(new Point3D(0, 0, 77)));
        assertEquals(3 * 3 + 3 * 3 + 3 * 3, new Point3D(1, 2, 3).getSquaredDistance(new Point3D(4, 5, 6)));
    }

    @Test
    void whenGetMiddle() {
        Point3D m = Point3D.getMiddle(new Point3D(1, 2, 3), new Point3D(3, 4, 5));
        assertEquals(2, m.x);
        assertEquals(3, m.y);
        assertEquals(4, m.z);
    }
}
