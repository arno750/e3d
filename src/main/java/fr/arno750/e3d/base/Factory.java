package fr.arno750.e3d.base;

import fr.arno750.e3d.base.config.Status;
import fr.arno750.e3d.base.config.VolumeDefinition;
import fr.arno750.e3d.base.config.Volumes;
import fr.arno750.e3d.base.volume.*;
import org.yaml.snakeyaml.Yaml;

public class Factory {

    /**
     * Builds the scene.
     *
     * @param sceneConfigurationName the name of the scene configuration.
     * @return the scene.
     */
    public static Scene build(String sceneConfigurationName) {
        Yaml yaml = new Yaml();
        Volumes volumes = yaml.loadAs(Scene.class.getResourceAsStream(sceneConfigurationName), Volumes.class);

        Scene scene = new Scene();

        for (VolumeDefinition definition : volumes.getVolumes()) {
            if (definition.getStatus() == Status.rendered) {
                Volume newVolume = switch (definition.getType()) {
                    case "Box" -> new Box(definition);
                    case "Sphere" -> new Sphere(definition);
                    case "Torus" -> new Torus(definition);
                    case "BezierPatch" -> new BezierPatch(definition);
                    case "Teapot" -> new Teapot(definition);
                    default ->
                            throw new IllegalArgumentException(String.format("Unsupported type %s", definition.getType()));
                };

                scene.add(newVolume);
            }
        }

        return scene;
    }
}
