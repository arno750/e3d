package e3d;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import e3d.bs.Point3D;
import e3d.bs.Vector3D;

class Point3DTest {

	@Test
	void testConstructor() {
		Point3D p = new Point3D(0, 1, 2);
		Vector3D v = new Vector3D(-5, 7, 100);
		Point3D r = new Point3D(p, v);
		assertEquals(0 - 5, r.x);
		assertEquals(1 + 7, r.y);
		assertEquals(2 + 100, r.z);
	}

}
