package fr.arno750.e3d.base.config;

import fr.arno750.e3d.base.Matrix;

public class VolumeDefinition {
    private String name;
    private Status status;
    private String type;

    private Parameters parameters;
    private Transform transform;

    public Matrix getTransformMatrix() {
        return transform.getTransform();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
