package fr.arno750.e3d.base.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Volumes {
    private List<VolumeDefinition> volumes;

    public Volumes() {
        this.volumes = new ArrayList<>();
    }
}
