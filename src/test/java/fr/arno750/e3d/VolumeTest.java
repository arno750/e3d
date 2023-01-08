package fr.arno750.e3d;

import fr.arno750.e3d.base.VertexFactory;
import fr.arno750.e3d.base.config.VolumeDefinition;
import fr.arno750.e3d.base.volume.Box;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VolumeTest {

    @Test
    void whenWorkOutBoundingSphere() {
        Box box = new Box(VolumeDefinition.getDefault(), new VertexFactory());
        box.workOutBoundingSphere();
        assertEquals(Math.sqrt(0.75), box.getRadius());
    }

    @Test
    void whenWorkOutRitterBoundingSphere() {
        Box box = new Box(VolumeDefinition.getDefault(), new VertexFactory());
        box.workOutRitterBoundingSphere();
        assertEquals(Math.sqrt(0.75), box.getRadius());
    }
}
