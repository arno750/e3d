package fr.arno750.e3d;

import fr.arno750.e3d.base.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SurfaceTest {

    @Test
    void whenGetArea() {
        Surface surface = new Surface(new Vertex(Point3D.ORIGIN), new Vertex(Point3D.X_UNIT), new Vertex(Point3D.Y_UNIT));
        assertEquals(0.5, surface.getArea());
    }
}
