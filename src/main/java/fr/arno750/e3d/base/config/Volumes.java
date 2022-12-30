package fr.arno750.e3d.base.config;

import java.util.ArrayList;
import java.util.List;

public class Volumes {
    private List<VolumeDefinition> volumes;

    public Volumes() {
        this.volumes = new ArrayList<>();
    }

    public List<VolumeDefinition> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDefinition> volumes) {
        this.volumes = volumes;
    }
}
