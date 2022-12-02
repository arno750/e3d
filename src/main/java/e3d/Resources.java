package e3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * @author Arnaud Wieland
 * 
 */
public class Resources {

	String name;
	ResourceBundle resourceBundle;

	/**
	 * @param id
	 */
	public Resources(String name) {
		try {
			resourceBundle = ResourceBundle.getBundle(name);
			this.name = name;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw (new Error("Resources " + name + " not available..."));
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
		try {
			resourceBundle.getObject(key);
		} catch (Exception exception) {
			return false;
		}
		return true;
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
			return resourceBundle.getString(key).equals("true") == true;
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
			if (colorName.startsWith("#") == false)
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
