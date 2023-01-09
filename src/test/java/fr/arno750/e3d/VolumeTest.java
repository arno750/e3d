package fr.arno750.e3d;

import fr.arno750.e3d.base.VertexFactory;
import fr.arno750.e3d.base.config.VolumeDefinition;
import fr.arno750.e3d.base.volume.Box;
import fr.arno750.e3d.base.volume.Sphere;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VolumeTest {

    @Test
    void givenBox_whenWorkOutBoundingSphere() {
        Box box = new Box(VolumeDefinition.getDefault(), new VertexFactory());
        box.workOutBoundingSphere();
        assertEquals(Math.sqrt(0.75), box.getRadius());
        assertEquals(0.5, box.getCenter().x);
        assertEquals(0.5, box.getCenter().y);
        assertEquals(0.5, box.getCenter().z);
    }

    @Test
    void givenSphere_whenWorkOutBoundingSphere() {
        var vd = VolumeDefinition.getDefault();
        vd.getParameters().setLatitudes(32);
        vd.getParameters().setLongitudes(64);
        Sphere sphere = new Sphere(vd, new VertexFactory());
        sphere.workOutBoundingSphere();
        assertEquals(Math.sqrt(3), sphere.getRadius());
    }

    @Test
    void givenBox_whenWorkOutRitterBoundingSphere() {
        Box box = new Box(VolumeDefinition.getDefault(), new VertexFactory());
        box.workOutRitterBoundingSphere();
        assertEquals(Math.sqrt(0.75), box.getRadius());
        assertEquals(0.5, box.getCenter().x);
        assertEquals(0.5, box.getCenter().y);
        assertEquals(0.5, box.getCenter().z);
    }

    @Test
    void givenSphere_whenWorkOutRitterBoundingSphere() {
        var vd = VolumeDefinition.getDefault();
        vd.getParameters().setLatitudes(32);
        vd.getParameters().setLongitudes(64);
        Sphere sphere = new Sphere(vd, new VertexFactory());
        sphere.workOutRitterBoundingSphere();
        assertEquals(1.0, sphere.getRadius());
    }
}
