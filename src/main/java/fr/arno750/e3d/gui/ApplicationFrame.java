package fr.arno750.e3d.gui;

import fr.arno750.e3d.Resources;
import fr.arno750.e3d.render.Context;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;

public class ApplicationFrame extends JFrame {

    @Serial
    private static final long serialVersionUID = 7187814202647201494L;
    Context context;
    String title;
    JPanel mainPanel;
    RenderingPanel renderingPanel;
    CommandPanel commandPanel;
    int panelWidth, panelHeight;

    /**
     * @param context
     * @param title
     * @param resources
     */
    public void initialize(Context context, String title, Resources resources) {
        this.context = context;
        this.title = title;
        if (resources.containsKey("mainFrame.text"))
            this.title = resources.getString("mainFrame.text");
        setTitle(null);
        setIconImage(resources.getImageIcon("mainFrame.icon").getImage());

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("ApplicationFrame");
        setFont(Constants.PLAIN_DEFAULT_FONT);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagHelper constraints = new GridBagHelper();

        commandPanel = new CommandPanel();
        commandPanel.initialize();
        mainPanel.add(commandPanel, constraints);

        int width = resources.getInteger("mainFrame.width");
        int height = resources.getInteger("mainFrame.height");

        panelWidth = width - 20;
        panelHeight = height - 150;
        renderingPanel = new RenderingPanel();
        renderingPanel.initialize(context, panelWidth, panelHeight);
        renderingPanel.setBorder(new LineBorder(Color.black, 1));
        mainPanel.add(renderingPanel, constraints.getNextRow());

        getContentPane().add(mainPanel);
        pack();
        setSize(new Dimension(width, height));
    }

    /**
     * @return the panelWidth
     */
    public int getPanelWidth() {
        return panelWidth;
    }

    /**
     * @return the panelHeight
     */
    public int getPanelHeight() {
        return panelHeight;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.Frame#setTitle(java.lang.String)
     */
    public void setTitle(String suffix) {
        super.setTitle((suffix == null) ? title : title + " - " + suffix);
    }
}
