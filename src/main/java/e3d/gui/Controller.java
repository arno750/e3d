package e3d.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import e3d.Resources;
import e3d.bs.Context;
import e3d.bs.Factory;
import e3d.bs.Matrix;
import e3d.bs.Point3D;
import e3d.bs.Renderer;
import e3d.bs.Scene;
import e3d.bs.Toolbox;
import e3d.bs.Vector3D;

/**
 * @author Arnaud Wieland
 * 
 */
public class Controller {

	public static final boolean DEBUG = true;
	public static Scene scene;
	public static Context context;
	public static Renderer renderer;
	public static Resources resources;
	public static ApplicationFrame applicationFrame;

	/**
	 * @param title
	 */
	public static void startGui(String title) {
		scene = Factory.read("");

		context = new Context();
		context.focal = 0.75;
		context.observer = new Point3D(-5, 5, -7);
		context.resolution = 1000;

		centerContext();
		updateContext();

		renderer = new Renderer(scene);
		renderer.setContext(context);

		setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		resources = new Resources("e3d.gui.Gui");
		Constants.initialize(resources);

		applicationFrame = new ApplicationFrame();
		applicationFrame.initialize(title, resources);
		context.halfWidth = applicationFrame.getPanelWidth() >> 1;
		context.halfHeight = applicationFrame.getPanelHeight() >> 1;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		applicationFrame.setLocation((screenSize.width - applicationFrame.getWidth()) / 2,
				(screenSize.height - applicationFrame.getHeight()) / 2);
		applicationFrame.setVisible(true);
	}

	/**
	 * @param identifier
	 */
	private static void setLookAndFeel(String name) {
		try {
			UIManager.setLookAndFeel(name);
		} catch (Exception exception) {
		}
	}

	/**
	 * 
	 */
	public static void centerContext() {
		Vector3D headingOrigin = new Vector3D(context.observer, Point3D.ORIGIN);
		headingOrigin.j = 0;
		headingOrigin.normalize();
		context.gamma = -Math.signum(headingOrigin.i)
				* Toolbox.getAngle(Vector3D.getDotProduct(headingOrigin, Vector3D.K_AXIS));

		if (DEBUG) {
			System.out.format("heading: %s\n", headingOrigin);
			System.out.format("gamma: %.1f\n", context.gamma);
		}

		Vector3D elevationOrigin = new Vector3D(context.observer, Point3D.ORIGIN);
		elevationOrigin.normalize();
		context.alpha = Math.signum(elevationOrigin.j)
				* Toolbox.getAngle(Vector3D.getDotProduct(elevationOrigin, headingOrigin));

		if (DEBUG) {
			System.out.format("elevation: %s\n", elevationOrigin);
			System.out.format("alpha: %.1f\n", context.alpha);
		}
	}

	/**
	 * 
	 */
	public static void updateContext() {
		context.transform = Matrix.getTransform(-context.observer.x, -context.observer.y, -context.observer.z, 1.0, 1.0,
				1.0, context.alpha, context.beta, context.gamma);

		System.out.format("tranform:\n%s", context.transform);
	}

	/**
	 * 
	 */
	public static void updateView() {
		applicationFrame.renderingPanel.repaint();
	}
}