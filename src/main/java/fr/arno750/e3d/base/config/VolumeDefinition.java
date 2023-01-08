package fr.arno750.e3d.base.config;

import fr.arno750.e3d.base.Matrix;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolumeDefinition {
    private String name;
    private Status status;
    private String type;

    private Parameters parameters;
    private Transform transform;

    public static VolumeDefinition getDefault() {
        VolumeDefinition vd = new VolumeDefinition();
        vd.setParameters(new Parameters());
        vd.setTransform(new Transform());
        return vd;
    }

    public Matrix getTransformMatrix() {
        return transform.getTransform();
    }
}
