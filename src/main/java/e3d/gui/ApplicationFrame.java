package e3d.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import e3d.Resources;

/**
 * @author Arnaud Wieland
 * 
 */
public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	String title;
	JPanel mainPanel;
	RenderingPanel renderingPanel;
	MapPanel mapPanel;
	CommandPanel commandPanel;
	int panelWidth, panelHeight;

	/**
	 * @param title
	 * @param resources
	 */
	public void initialize(String title, Resources resources) {
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
		renderingPanel.initialize(panelWidth, panelHeight);
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
