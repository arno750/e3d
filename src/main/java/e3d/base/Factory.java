package e3d.base;

import e3d.base.volume.*;

public class Factory {

	/**
	 * Builds the scene.
	 *
	 * @return the scene.
	 */
	public static Scene build() {
		Scene scene = new Scene();

//		 scene.volumes.add(new Box(Matrix.getTransform(-0.5, -0.5, -0.5, 1, 1, 1, 0, 0, 0)));
//		scene.volumes.add(new Box(Matrix.getTransform(1, 0, 0, 2, 2, 1, 0, 0, 0)));
//		scene.volumes.add(new Box(Matrix.getTransform(3, 0, 0, 3, 3, 1, 0, 0, 0)));
//		scene.volumes.add(new Box(Matrix.getTransform(4, 1, -1, 1, 1, 1, 0, 0, 0)));
//		scene.volumes.add(new Sphere(24, 32, Matrix.getTransform(0, 0, 0, 0.1, 0.1, 0.1, 0, 0, 0)));
//		scene.volumes.add(new Torus(0.5, 16, 16, Matrix.getTransform(0, -3, -3, 1, 1, 1, 0, 0, 0)));
		scene.volumes.add(new Teapot(16, Matrix.getTransform(0, 0, 0, 0.15, 0.15, 0.15, 0, 0, 0)));
//		scene.volumes.add(new BezierPatch(8, Matrix.getTransform(0, 0, 0, 1, 1, 1, 0, 0, 0)));

		return scene;
	}
}
