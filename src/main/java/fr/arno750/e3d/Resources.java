package fr.arno750.e3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Resources {

    final Logger logger = LoggerFactory.getLogger(Resources.class);

    String name;
    ResourceBundle resourceBundle;

    /**
     * @param name
     */
    public Resources(String name) {
        try {
            resourceBundle = ResourceBundle.getBundle(name);
            this.name = name;
        } catch (MissingResourceException exception) {
            logger.error("Missing resources", exception);
            throw (new Error("Resources " + name + " missing..."));
        }
    }

    /**
     * @param value
     * @param size
     * @return
     */
    public static String getHexString(int value, int size) {
        String text = Integer.toHexString(value);
        return "00000000".substring(8 - size + text.length()) + text;
    }

    /**
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return resourceBundle.containsKey(key);
    }

    /**
     * @return
     */
    public Enumeration<String> getKeys() {
        return resourceBundle.getKeys();
    }

    /**
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        try {
            return resourceBundle.getString(key).equals("true");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * @param key
     * @return
     */
    public int getInteger(String key) {
        try {
            return Integer.parseInt(resourceBundle.getString(key));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return -1;
    }

    /**
     * @param key
     * @return
     */
    public double getDouble(String key) {
        try {
            return Double.parseDouble(resourceBundle.getString(key));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return -1;
    }

    /**
     * @param key
     * @return
     */
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception exception) {
            System.err.println("Key [" + key + "] is missing in " + name);
        }

        return null;
    }

    /**
     * The point should be defined as x,y
     *
     * @param key
     * @return
     */
    public Point getPoint(String key) {
        try {
            String[] values = resourceBundle.getString(key).split(",");

            int x = Integer.parseInt(values[0]);
            int y = Integer.parseInt(values[1]);
            return new Point(x, y);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * The dimension should be defined as width,height
     *
     * @param key
     * @return
     */
    public Dimension getDimension(String key) {
        try {
            String[] values = resourceBundle.getString(key).split(",");

            int width = Integer.parseInt(values[0]);
            int height = Integer.parseInt(values[1]);
            return new Dimension(width, height);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * The dimension should be defined as top,left,bottom,right
     *
     * @param key
     * @return
     */
    public Insets getInsets(String key) {
        try {
            String[] values = resourceBundle.getString(key).split(",");

            int top = Integer.parseInt(values[0]);
            int left = Integer.parseInt(values[1]);
            int bottom = Integer.parseInt(values[2]);
            int right = Integer.parseInt(values[3]);
            return new Insets(top, left, bottom, right);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * @param key
     * @return
     */
    public Color getColor(String key) {
        try {
            String colorName = resourceBundle.getString(key);
            if (!colorName.startsWith("#"))
                return null;
            int colorValue = Integer.parseInt(colorName.substring(1), 16);

            return new Color(colorValue);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * @param key
     * @return
     */
    public Font getFont(String key) {
        try {
            return Font.decode(resourceBundle.getString(key));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * @param key
     * @return
     */
    public ImageIcon getImageIcon(String key) {
        try {
            return new ImageIcon(this.getClass().getResource(resourceBundle.getString(key)));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * @param key
     * @return
     */
    public KeyStroke getKeyStroke(String key) {
        try {
            return KeyStroke.getKeyStroke(resourceBundle.getString(key));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
