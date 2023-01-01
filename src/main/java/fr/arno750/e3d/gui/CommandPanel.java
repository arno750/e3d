package fr.arno750.e3d.gui;

import fr.arno750.e3d.render.Context;
import fr.arno750.e3d.render.SurfaceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.text.DecimalFormat;

public class CommandPanel extends JPanel {

    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.0");
    @Serial
    private static final long serialVersionUID = 5467976891976345050L;
    Context context;
    JSpinField focal;
    JSpinField resolution;
    JSpinField distance;
    JSpinField xObserver;
    JSpinField yObserver;
    JSpinField zObserver;
    JSpinField pitch;
    JSpinField roll;
    JSpinField yaw;
    JButton reset;
    JButton center;
    JCheckBox autoCenter;
    JCheckBox axis;
    JCheckBox hiddenSurfaceRemoval;
    JCheckBox opaque;

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

        label = new JLabel("Context");
        constraints2.gridwidth = 2;
        panel.add(label, constraints2);
        constraints2.gridwidth = 1;
        constraints2.insets = new Insets(0, 4, 0, 4);

        label = new JLabel("Focal");
        panel.add(label, constraints2.getNextRow());
        focal = new JSpinField(-100, 100, 0.01, 2);
        focal.setPreferredSize(new Dimension(70, 18));
        panel.add(focal, constraints2.getNextColumn());

        label = new JLabel("Resolution (pixel per meter)");
        panel.add(label, constraints2.getNextRow());
        resolution = new JSpinField(0, 10000, 100, 0);
        resolution.setPreferredSize(new Dimension(70, 18));
        panel.add(resolution, constraints2.getNextColumn());

        label = new JLabel("distance");
        panel.add(label, constraints2.getNextRow());
        distance = new JSpinField(-100, 100, 0.1, 2);
        distance.setPreferredSize(new Dimension(70, 18));
        panel.add(distance, constraints2.getNextColumn());


        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        add(panel, constraints1.getNextColumn());
        constraints2 = new GridBagHelper();

        label = new JLabel("Translation");
        constraints2.gridwidth = 2;
        panel.add(label, constraints2);
        constraints2.gridwidth = 1;
        constraints2.insets = new Insets(0, 4, 0, 4);

        label = new JLabel("x (red/horizontal)");
        panel.add(label, constraints2.getNextRow());
        xObserver = new JSpinField(-100, 100, 0.1, 2);
        xObserver.setPreferredSize(new Dimension(70, 18));
        panel.add(xObserver, constraints2.getNextColumn());

        label = new JLabel("y (green/vertical)");
        panel.add(label, constraints2.getNextRow());
        yObserver = new JSpinField(-100, 100, 0.1, 2);
        yObserver.setPreferredSize(new Dimension(70, 18));
        yObserver.setHorizontalAlignment(JTextField.TRAILING);
        panel.add(yObserver, constraints2.getNextColumn());

        label = new JLabel("z (blue/depth)");
        panel.add(label, constraints2.getNextRow());
        zObserver = new JSpinField(-100, 100, 0.1, 2);
        zObserver.setPreferredSize(new Dimension(70, 18));
        zObserver.setHorizontalAlignment(JTextField.TRAILING);
        panel.add(zObserver, constraints2.getNextColumn());


        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        add(panel, constraints1.getNextColumn());
        constraints2 = new GridBagHelper();

        label = new JLabel("Rotation");
        constraints2.gridwidth = 2;
        panel.add(label, constraints2);
        constraints2.gridwidth = 1;
        constraints2.insets = new Insets(0, 4, 0, 4);

        label = new JLabel("α (yaw/heading)");
        panel.add(label, constraints2.getNextRow());
        yaw = new JSpinField(0, 360, 1.0, 2);
        yaw.setPreferredSize(new Dimension(70, 18));
        yaw.setHorizontalAlignment(JTextField.TRAILING);
        panel.add(yaw, constraints2.getNextColumn());

        label = new JLabel("β (pitch/elevation)");
        panel.add(label, constraints2.getNextRow());
        pitch = new JSpinField(-180, 180, 1.0, 2);
        pitch.setHorizontalAlignment(JTextField.TRAILING);
        pitch.setPreferredSize(new Dimension(70, 18));
        panel.add(pitch, constraints2.getNextColumn());

        label = new JLabel("γ (roll/bank)");
        panel.add(label, constraints2.getNextRow());
        roll = new JSpinField(-90, 90, 1.0, 2);
        roll.setPreferredSize(new Dimension(70, 18));
        roll.setHorizontalAlignment(JTextField.TRAILING);
        panel.add(roll, constraints2.getNextColumn());


        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        constraints1.gridwidth = 3;
        add(panel, constraints1.getNextRow());
        constraints2 = new GridBagHelper();
        constraints2.insets = new Insets(4, 4, 8, 4);

        reset = new JButton("Reset");
        constraints1.insets = new Insets(0, 0, 8, 0);
        panel.add(reset, constraints2);
        reset.addActionListener(event -> {
            xObserver.setValue(2);
            yObserver.setValue(2);
            zObserver.setValue(-7);
            updateContext();
        });

        center = new JButton("center");
        panel.add(center, constraints2.getNextColumn());
        center.addActionListener(event -> {
            updateContext();
            Controller.lookTowardsOrigin();
            pitch.adjustValue(Math.toDegrees(context.alpha));
            roll.adjustValue(Math.toDegrees(context.beta));
            yaw.adjustValue(Math.toDegrees(context.gamma));

            Controller.updateContext();
            distance.adjustValue(context.distance);

            Controller.updateView();
        });

        autoCenter = new JCheckBox("Auto-centered", true);
        panel.add(autoCenter, constraints2.getNextColumn());

        axis = new JCheckBox("Axis");
        panel.add(axis, constraints2.getNextColumn());

        hiddenSurfaceRemoval = new JCheckBox("Hidden surface removal");
        panel.add(hiddenSurfaceRemoval, constraints2.getNextColumn());

        opaque = new JCheckBox("Opaque surfaces");
        panel.add(opaque, constraints2.getNextColumn());

        setFromContext(context);

        ActionListener actionListener = event -> {
            updateContext();
            Controller.updateContext();
            Controller.updateView();
        };
        axis.addActionListener(actionListener);
        hiddenSurfaceRemoval.addActionListener(actionListener);
        opaque.addActionListener(actionListener);

        PropertyChangeListener propertyChangeListener1 = event -> {
            if (event.getPropertyName().equals("value")) {
                updateContext();
                if (autoCenter.isSelected()) {
                    Controller.lookTowardsOrigin();
                    pitch.adjustValue(Math.toDegrees(context.alpha));
                    roll.adjustValue(Math.toDegrees(context.beta));
                    yaw.adjustValue(Math.toDegrees(context.gamma));

                }
                Controller.updateContext();
                distance.adjustValue(context.distance);

                Controller.updateView();
            }
        };
        focal.addPropertyChangeListener(propertyChangeListener1);
        resolution.addPropertyChangeListener(propertyChangeListener1);
        xObserver.addPropertyChangeListener(propertyChangeListener1);
        yObserver.addPropertyChangeListener(propertyChangeListener1);
        zObserver.addPropertyChangeListener(propertyChangeListener1);

        PropertyChangeListener propertyChangeListener2 = event -> {
            if (event.getPropertyName().equals("value")) {
                updateContext();
                Controller.changeDistance();
                xObserver.adjustValue(context.observer.x);
                yObserver.adjustValue(context.observer.y);
                zObserver.adjustValue(context.observer.z);

                Controller.updateContext();
                Controller.updateView();
            }
        };
        distance.addPropertyChangeListener(propertyChangeListener2);

        PropertyChangeListener propertyChangeListener3 = event -> {
            if (event.getPropertyName().equals("value")) {
                if (!autoCenter.isSelected()) {
                    updateContext();
                    Controller.updateContext();
                    Controller.updateView();
                }
            }
        };
        pitch.addPropertyChangeListener(propertyChangeListener3);
        roll.addPropertyChangeListener(propertyChangeListener3);
        yaw.addPropertyChangeListener(propertyChangeListener3);
    }

    /**
     * @param context
     */
    public void setFromContext(Context context) {
        focal.setValue(context.focal);
        resolution.setValue(context.resolution);
        distance.setValue(context.distance);
        xObserver.setValue(context.observer.x);
        yObserver.setValue(context.observer.y);
        zObserver.setValue(context.observer.z);
        pitch.setValue(Math.toDegrees(context.alpha));
        roll.setValue(Math.toDegrees(context.beta));
        yaw.setValue(Math.toDegrees(context.gamma));

        axis.setSelected(context.axis);
        hiddenSurfaceRemoval.setSelected(context.hiddenSurfaceRemoval);
        opaque.setSelected(context.surfaceType == SurfaceType.OPAQUE);
    }

    /**
     *
     */
    public void updateContext() {
        context.focal = focal.getValue();
        context.resolution = resolution.getValue();
        context.distance = distance.getValue();
        context.observer.x = xObserver.getValue();
        context.observer.y = yObserver.getValue();
        context.observer.z = zObserver.getValue();
        context.alpha = Math.toRadians(pitch.getValue());
        context.beta = Math.toRadians(roll.getValue());
        context.gamma = Math.toRadians(yaw.getValue());

        context.axis = axis.isSelected();
        context.hiddenSurfaceRemoval = hiddenSurfaceRemoval.isSelected();
        context.surfaceType = opaque.isSelected() ? SurfaceType.OPAQUE : SurfaceType.MESH;
    }
}
