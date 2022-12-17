package fr.arno750.e3d.gui;

import fr.arno750.e3d.Resources;

import java.awt.*;

public class Constants {

    public static Font PLAIN_DEFAULT_FONT;
    public static Font BOLD_DEFAULT_FONT;

    public static Color THESEUS_CURRENT;
    public static Color THESEUS_NEXT;
    public static Color MINOTAUR_CURRENT;
    public static Color MINOTAUR_NEXT;

    /**
     * @param resources
     */
    public static void initialize(Resources resources) {
        PLAIN_DEFAULT_FONT = resources.getFont("mainFrame.font.defaultPlain");
        BOLD_DEFAULT_FONT = resources.getFont("mainFrame.font.defaultBold");
    }
}
