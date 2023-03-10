package fr.arno750.e3d.base.config;

import fr.arno750.e3d.base.Matrix;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transform {

    private double tx;
    private double ty;
    private double tz;
    private double sx = 1.0;
    private double sy = 1.0;
    private double sz = 1.0;
    private double rx;
    private double ry;
    private double rz;

    public Matrix getTransform() {
        return Matrix.getTransform(tx, ty, tz, sx, sy, sz, rx, ry, rz);
    }
}
