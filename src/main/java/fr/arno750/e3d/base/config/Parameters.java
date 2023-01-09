package fr.arno750.e3d.base.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameters {
    private int steps;
    private double ratio = 0.5;
    private int latitudes = 4;
    private int longitudes = 4;
}
