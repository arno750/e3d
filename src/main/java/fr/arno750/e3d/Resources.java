package fr.arno750.e3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Resources {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Resources.class);

    String name;
    ResourceBundle resourceBundle;

    /**
     * @param name the resource name
     */
    public Resources(String name) {
        try {
            resourceBundle = ResourceBundle.getBundle(name);
            this.name = name;
        } catch (MissingResourceException exception) {
            LOGGER.error("Missing resources", exception);
            throw (new Error("Resources " + name + " missing..."));
        }
    }

    /**
     * @param key the key.
     * @return <tt>true</tt> is the key exits ; <tt>false</tt> otherwise.
     */
    public boolean containsKey(String key) {
        return resourceBundle.containsKey(key);
    }

    /**
     * @param key the key.
     * @return the <tt>boolean</tt>.
     */
    public boolean getBoolean(String key) {
        return resourceBundle.getString(key).equals("true");
    }

    /**
     * @param key the key.
     * @return the <tt>integer</tt>.
     */
    public int getInteger(String key) {
        return Integer.parseInt(resourceBundle.getString(key));
    }

    /**
     * @param key the key.
     * @return the <tt>double</tt>.
     */
    public double getDouble(String key) {
        return Double.parseDouble(resourceBundle.getString(key));
    }

    /**
     * @param key the key.
     * @return the <tt>java.lang.String</tt>.
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * The point is defined as <tt>x,y</tt>
     *
     * @param key the key.
     * @return <tt>java.awt.Point</tt>.
     */
    public Point getPoint(String key) {
        String[] values = resourceBundle.getString(key).split(",");

        int x = Integer.parseInt(values[0]);
        int y = Integer.parseInt(values[1]);
        return new Point(x, y);
    }

    /**
     * The dimension is defined as <tt>width,height</tt>
     *
     * @param key the key.
     * @return <tt>java.awt.Dimension</tt>.
     */
    public Dimension getDimension(String key) {
        String[] values = resourceBundle.getString(key).split(",");

        int width = Integer.parseInt(values[0]);
        int height = Integer.parseInt(values[1]);
        return new Dimension(width, height);
    }

    /**
     * The dimension is defined as <tt>top,left,bottom,right</tt>.
     *
     * @param key the key.
     * @return the <tt>java.awt.Insets</tt>.
     */
    public Insets getInsets(String key) {
        String[] values = resourceBundle.getString(key).split(",");

        int top = Integer.parseInt(values[0]);
        int left = Integer.parseInt(values[1]);
        int bottom = Integer.parseInt(values[2]);
        int right = Integer.parseInt(values[3]);
        return new Insets(top, left, bottom, right);
    }

    /**
     * The color is defined as <tt>#rrggbb</tt> with values in hexadecimal.
     *
     * @param key the key.
     * @return the <tt>java.awt.Color</tt>.
     */
    public Color getColor(String key) {
        String colorName = resourceBundle.getString(key);
        if (!colorName.startsWith("#"))
            throw new IllegalArgumentException("Color format is #rrggbb with values in hexadecimal");
        int colorValue = Integer.parseInt(colorName.substring(1), 16);

        return new Color(colorValue);
    }

    /**
     * @param key the key.
     * @return the <tt>java.awt.Font</tt>.
     */
    public Font getFont(String key) {
        return Font.decode(resourceBundle.getString(key));
    }

    /**
     * @param key the key.
     * @return the <tt>javax.swing.ImageIcon</tt>.
     */
    public ImageIcon getImageIcon(String key) {
        return new ImageIcon(this.getClass().getResource(resourceBundle.getString(key)));
    }

    /**
     * @param key the key.
     * @return the <tt>javax.swing.KeyStroke</tt>.
     */
    public KeyStroke getKeyStroke(String key) {
        return KeyStroke.getKeyStroke(resourceBundle.getString(key));
    }
}
