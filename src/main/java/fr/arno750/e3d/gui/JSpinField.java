package fr.arno750.e3d.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Based on JSpinField by Kai Toedter (kai@toedter.com, www.toedter.com)
 *
 */
public class JSpinField extends JPanel implements ChangeListener, CaretListener, ActionListener, FocusListener {

	private static final long serialVersionUID = 6681418088755325567L;

	protected JSpinner spinner;
	protected SpinnerNumberModel model;

    protected JTextField textField;
    protected double minimum;
    protected double maximum;
    protected double stepSize;
    protected double value;
    protected Color darkGreen;
    private final String format;

	/**
	 * Constructs a default instance with valid value range between
	 * <tt>Integer.MIN_VALUE</tt> and <tt>Integer.MAX_VALUE</tt>. The step size is
	 * set to 1. The initial value is 0.
	 */
	public JSpinField() {
		this(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);
	}

	/**
	 * @param minimum   the minimum.
	 * @param maximum   the maximum.
	 * @param stepSize  the step size.
	 * @param precision the number precision (when displayed)
	 */
	public JSpinField(double minimum, double maximum, double stepSize, int precision) {
		super();
		setName("JSpinField");
		this.minimum = minimum;
		if (maximum < minimum)
			maximum = minimum;
		this.maximum = maximum;
		this.stepSize = stepSize;
		this.format = String.format("%%.%df", precision);

		value = 0;
		if (value < minimum)
			value = minimum;
		if (value > maximum)
			value = maximum;

		darkGreen = new Color(0, 150, 0);
		setLayout(new BorderLayout());
		textField = new JTextField();
		textField.addCaretListener(this);
		textField.addActionListener(this);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setText(String.format(this.format, value));
		textField.addFocusListener(this);
		model = new SpinnerNumberModel(this.value, this.minimum, this.maximum, this.stepSize);
		spinner = new JSpinner(model) {

			private static final long serialVersionUID = 3682086601092749841L;

			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, getEditor().getPreferredSize().height);
			}
		};
		spinner.setEditor(textField);
		spinner.addChangeListener(this);
		add(spinner, BorderLayout.CENTER);
	}

	public void stateChanged(ChangeEvent e) {
		setValue(model.getNumber().doubleValue());
	}

	/**
	 * Sets the value (for internal purpose).
	 *
	 * @param newValue           The new value.
	 * @param updateTextField    <tt>true</tt> if text field should be updated.
	 * @param firePropertyChange <tt>true</tt> to fire a property change event.
	 */
	protected void setValue(double newValue, boolean updateTextField, boolean firePropertyChange) {
		double oldValue = value;
		value = Math.min(maximum, Math.max(minimum, newValue));

		if (updateTextField) {
			textField.setText(String.format(this.format, value));
			textField.setForeground(Color.black);
		}

		if (firePropertyChange) {
			firePropertyChange("value", oldValue, value);
		}
	}

	/**
	 * Returns the current value.
	 * 
	 * @return the value.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 * @see #getValue
	 */
	public void setValue(double value) {
		setValue(value, true, true);
		spinner.setValue(this.value);
	}

	/**
	 * @param value
	 */
	public void adjustValue(double value) {
		setValue(value, true, false);
		spinner.setValue(this.value);
	}

	/**
	 * Sets the horizontal alignment of the displayed value.
	 *
	 * @param alignment the horizontal alignment
	 */
	public void setHorizontalAlignment(int alignment) {
		textField.setHorizontalAlignment(alignment);
	}

	/**
	 * Sets the font property.
	 *
	 * @param font the new font
	 */
	public void setFont(Font font) {
		if (textField != null) {
			textField.setFont(font);
		}
	}

	/**
	 * Sets the foreground color
	 *
	 * @param fg the foreground
	 */
	public void setForeground(Color fg) {
		if (textField != null) {
			textField.setForeground(fg);
		}
	}

	/**
	 * After any user input, the value of the text field is verified. Depending on
	 * being parsed successfully, the value is colored green or red.
	 *
	 * @param e the caret event
	 */
	public void caretUpdate(CaretEvent e) {
		try {
			double testValue = Double.parseDouble(textField.getText());
			if ((testValue >= minimum) && (testValue <= maximum)) {
				textField.setForeground(darkGreen);
				setValue(testValue, false, true);
			} else {
				textField.setForeground(Color.red);
			}
		} catch (NumberFormatException exception) {
			textField.setForeground(Color.red);
		}

		textField.repaint();
	}

	/**
	 * After any user input, the value of the text field is verified. If the text
	 * field is green, the enter key is accepted and the new value is set.
	 *
	 * @param e the action event
	 */
	public void actionPerformed(ActionEvent e) {
		if (textField.getForeground().equals(darkGreen)) {
			setValue(Double.parseDouble(textField.getText()));
		}
	}

	/**
	 * Enable or disable the component.
	 *
	 * @param enabled <tt>true</tt> to enable ; <tt>false</tt> otherwise.
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		spinner.setEnabled(enabled);
		textField.setEnabled(enabled);
		if (!enabled) {
			textField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		}
	}

	public void focusGained(FocusEvent e) {
	}

	/**
	 * The value of the text field is checked against a valid (green) value. If
	 * valid, the value is set and a property change is fired.
	 */
	public void focusLost(FocusEvent e) {
		actionPerformed(null);
	}
}
