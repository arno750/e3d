package fr.arno750.e3d;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.arno750.e3d.base.Matrix;
import fr.arno750.e3d.base.Point3D;
import fr.arno750.e3d.base.Vector3D;

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
		assertEquals(new Point3D(77, 0, 0).getDistanceFromOrigin(), 77);
		assertEquals(new Point3D(0, 55, 0).getDistanceFromOrigin(), 55);
		assertEquals(new Point3D(0, 0, 11).getDistanceFromOrigin(), 11);
		assertEquals(new Point3D(1, 2, 3).getDistanceFromOrigin(), Math.sqrt(14.0));
	}

	@Test
	void whenGetDistance() {
		assertEquals(new Point3D(77, 0, 0).getDistance(new Point3D(55, 0, 0)), 22);
		assertEquals(new Point3D(0, 55, 0).getDistance(new Point3D(0, 11, 0)), 44);
		assertEquals(new Point3D(0, 0, 11).getDistance(new Point3D(0, 0, 77)), 66);
		assertEquals(new Point3D(1, 2, 3).getDistance(new Point3D(4, 5, 6)), Math.sqrt(3 * 3 + 3 * 3 + 3 * 3));
	}

	@Test
	void whenGetMiddle() {
		Point3D m = Point3D.getMiddle(new Point3D(1, 2, 3), new Point3D(3, 4, 5));
		assertEquals(2, m.x);
		assertEquals(3, m.y);
		assertEquals(4, m.z);
	}
}
