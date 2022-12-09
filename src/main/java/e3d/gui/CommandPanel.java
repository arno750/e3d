package e3d.gui;

import com.toedter.components.JSpinField;
import e3d.render.Context;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

public class CommandPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.0");
	Context context;
	JSpinField focal;
	JSpinField resolution;
	JSpinField cx;
	JSpinField cy;
	JSpinField cz;
	JSpinField ca;
	JSpinField cb;
	JSpinField cg;
	JButton reset;
	JButton center;
	JButton update;
	JCheckBox autoCenter;

	public void initialize() {
		context = Controller.context;

		setLayout(new GridBagLayout());
		GridBagHelper constraints1 = new GridBagHelper();
		GridBagHelper constraints2;

		JLabel label;
		JPanel panel;

		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		add(panel, constraints1);
		constraints2 = new GridBagHelper();

		label = new JLabel("Translation");
		constraints2.gridwidth = 2;
		panel.add(label, constraints2);
		constraints2.gridwidth = 1;
		constraints2.insets = new Insets(0, 4, 0, 4);

		label = new JLabel("x (horizontal)");
		panel.add(label, constraints2.getNextRow());
		cx = new JSpinField(-100, 100);
		cx.setValue((int) context.observer.x);
		cx.setPreferredSize(new Dimension(70, 18));
		panel.add(cx, constraints2.getNextColumn());

		label = new JLabel("y (vertical)");
		panel.add(label, constraints2.getNextRow());
		cy = new JSpinField(-100, 100);
		cy.setValue((int) context.observer.y);
		cy.setPreferredSize(new Dimension(70, 18));
		cy.setHorizontalAlignment(JTextField.TRAILING);
		panel.add(cy, constraints2.getNextColumn());

		label = new JLabel("z (depth)");
		panel.add(label, constraints2.getNextRow());
		cz = new JSpinField(-100, 100);
		cz.setValue((int) context.observer.z);
		cz.setPreferredSize(new Dimension(70, 18));
		cz.setHorizontalAlignment(JTextField.TRAILING);
		panel.add(cz, constraints2.getNextColumn());

		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		add(panel, constraints1.getNextColumn());
		constraints2 = new GridBagHelper();

		label = new JLabel("Rotation");
		constraints2.gridwidth = 2;
		panel.add(label, constraints2);
		constraints2.gridwidth = 1;
		constraints2.insets = new Insets(0, 4, 0, 4);

		label = new JLabel("α (elevation)");
		panel.add(label, constraints2.getNextRow());
		ca = new JSpinField(-180, 180);
		ca.setHorizontalAlignment(JTextField.TRAILING);
		ca.setPreferredSize(new Dimension(70, 18));
		panel.add(ca, constraints2.getNextColumn());

		label = new JLabel("β (bank)");
		panel.add(label, constraints2.getNextRow());
		cb = new JSpinField(-180, 180);
		cb.setPreferredSize(new Dimension(70, 18));
		cb.setHorizontalAlignment(JTextField.TRAILING);
		panel.add(cb, constraints2.getNextColumn());

		label = new JLabel("γ (heading)");
		panel.add(label, constraints2.getNextRow());
		cg = new JSpinField(-180, 180);
		cg.setPreferredSize(new Dimension(70, 18));
		cg.setHorizontalAlignment(JTextField.TRAILING);
		panel.add(cg, constraints2.getNextColumn());

		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		constraints1.gridwidth = 2;
		add(panel, constraints1.getNextRow());
		constraints2 = new GridBagHelper();
		constraints2.insets = new Insets(4, 4, 8, 4);

		reset = new JButton("Reset");
		constraints1.insets = new Insets(0, 0, 8, 0);
		panel.add(reset, constraints2);
		reset.addActionListener(event -> {
			cx.setValue(0);
			cy.setValue(1);
			cz.setValue(-7);
			updateContext(true);
		});

		update = new JButton("center");
		panel.add(update, constraints2.getNextColumn());
		update.addActionListener(event -> updateContext(true));

		update = new JButton("update");
		panel.add(update, constraints2.getNextColumn());
		update.addActionListener(event -> updateContext(false));

		autoCenter = new JCheckBox("Auto-centered");
		panel.add(autoCenter, constraints2.getNextColumn());

		updateAngles();

		PropertyChangeListener propertyChangeListener = event -> {
			if (event.getPropertyName().equals("value")) {
				updateContext(autoCenter.isSelected());
			}
		};
		cx.addPropertyChangeListener(propertyChangeListener);
		cy.addPropertyChangeListener(propertyChangeListener);
		cz.addPropertyChangeListener(propertyChangeListener);
		ca.addPropertyChangeListener(propertyChangeListener);
		cb.addPropertyChangeListener(propertyChangeListener);
		cg.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * 
	 */
	public void updateContext(boolean centered) {
		context.observer.x = cx.getValue();
		context.observer.y = cy.getValue();
		context.observer.z = cz.getValue();
		context.alpha = Math.PI * ca.getValue() / 180.0;
		context.beta = Math.PI * cb.getValue() / 180.0;
		context.gamma = Math.PI * cg.getValue() / 180.0;

		if (centered) {
			Controller.centerContext();
			updateAngles();
		}

		Controller.updateContext();
		Controller.updateView();
	}

	/**
	 * 
	 */
	public void updateAngles() {
		ca.setValue((int) (context.alpha / Math.PI * 180.0));
		cb.setValue((int) (context.beta / Math.PI * 180.0));
		cg.setValue((int) (context.gamma / Math.PI * 180.0));
	}
}
