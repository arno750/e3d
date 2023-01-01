package fr.arno750.e3d.gui;

import fr.arno750.e3d.Resources;
import fr.arno750.e3d.base.*;
import fr.arno750.e3d.render.Context;
import fr.arno750.e3d.render.Renderer;

import javax.swing.*;
import java.awt.*;

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
    public static void startGui(String title, String sceneConfigurationName) {
        scene = Factory.build(sceneConfigurationName);

        context = new Context();
        lookTowardsOrigin();
        updateContext();

        renderer = new Renderer(scene);
        renderer.setContext(context);

        setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        resources = new Resources("fr.arno750.e3d.gui.Gui");
        Constants.initialize(resources);

        applicationFrame = new ApplicationFrame();
        applicationFrame.initialize(context, title, resources);
        context.halfWidth = applicationFrame.getPanelWidth() >> 1;
        context.halfHeight = applicationFrame.getPanelHeight() >> 1;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        applicationFrame.setLocation((screenSize.width - applicationFrame.getWidth()) / 2,
                (screenSize.height - applicationFrame.getHeight()) / 2);
        applicationFrame.setVisible(true);
    }

    /**
     * @param name Name of the look and feel
     */
    private static void setLookAndFeel(String name) {
        try {
            UIManager.setLookAndFeel(name);
        } catch (Exception exception) {
        }
    }

    /**
     * @param value
     * @return
     */
    private static double getAngle(double value) {
        if (value > 1.0)
            return 0;
        if (value < -1.0)
            return 2 * Math.PI;
        return Math.acos(value);
    }

    /**
     *
     */
    public static void changeDistance() {
        context.observer = new Point3D(new Vector3D(context.observer).setLength(context.distance));
    }

    /**
     *
     */
    public static void lookTowardsOrigin() {
        Vector3D headingOrigin = new Vector3D(context.observer, Point3D.ORIGIN);
        headingOrigin.j = 0;
        headingOrigin.normalize();
        context.gamma = -Math.signum(headingOrigin.i) * getAngle(headingOrigin.getDotProduct(Vector3D.K_UNIT));

        if (DEBUG) {
            System.out.format("heading: %s\n", headingOrigin);
            System.out.format("gamma: %.1f\n", context.gamma);
        }

        Vector3D elevationOrigin = new Vector3D(context.observer, Point3D.ORIGIN);
        elevationOrigin.normalize();
        context.alpha = Math.signum(elevationOrigin.j) * getAngle(elevationOrigin.getDotProduct(headingOrigin));

        if (DEBUG) {
            System.out.format("elevation: %s\n", elevationOrigin);
            System.out.format("alpha: %.1f\n", context.alpha);
        }
    }

    /**
     *
     */
    public static void updateContext() {
        context.distance = context.observer.getDistanceFromOrigin();

        context.transform = Matrix.getTransform(-context.observer.x, -context.observer.y, -context.observer.z, 1.0, 1.0,
                1.0, context.alpha, context.gamma, context.beta);

        System.out.format("transform:\n%s", context.transform);
    }

    /**
     *
     */
    public static void updateView() {
        applicationFrame.renderingPanel.repaint();
    }
}