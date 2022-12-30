package fr.arno750.e3d.base.config;

import fr.arno750.e3d.base.Matrix;

public class Transform {

    private double tx;
    private double ty;
    private double tz;
    private double sx;
    private double sy;
    private double sz;
    private double alpha;
    private double beta;
    private double gamma;

    public Matrix getTransform() {
        return Matrix.getTransform(tx, ty, tz, sx, sy, sz, alpha, beta, gamma);
    }

    public double getTx() {
        return tx;
    }

    public void setTx(double tx) {
        this.tx = tx;
    }

    public double getTy() {
        return ty;
    }

    public void setTy(double ty) {
        this.ty = ty;
    }

    public double getTz() {
        return tz;
    }

    public void setTz(double tz) {
        this.tz = tz;
    }

    public double getSx() {
        return sx;
    }

    public void setSx(double sx) {
        this.sx = sx;
    }

    public double getSy() {
        return sy;
    }

    public void setSy(double sy) {
        this.sy = sy;
    }

    public double getSz() {
        return sz;
    }

    public void setSz(double sz) {
        this.sz = sz;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }
}
