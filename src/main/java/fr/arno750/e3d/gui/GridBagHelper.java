package fr.arno750.e3d.gui;

import java.awt.*;
import java.io.Serial;

public class GridBagHelper extends GridBagConstraints {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public GridBagHelper() {
        gridx = 0;
        gridy = 0;
    }

    /**
     * @return
     */
    public GridBagHelper getNextColumn() {
        gridx++;
        return this;
    }

    /**
     * @return
     */
    public GridBagHelper getNextRow() {
        gridx = 0;
        gridy++;
        return this;
    }

    /**
     * @return
     */
    public GridBagHelper addHorizontalExpand() {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.weightx = 1.0;
        return duplicate;
    }

    /**
     * @return
     */
    public GridBagHelper addVerticalExpand() {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.weighty = 1.0;
        return duplicate;
    }

    /**
     * @param quantity
     * @return
     */
    public GridBagHelper addMergedColumns(int quantity) {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.gridwidth = quantity;
        return duplicate;
    }

    /**
     * @return
     */
    public GridBagHelper addRemainingColumns() {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.gridwidth = REMAINDER;
        return duplicate;
    }

    /**
     * @param quantity
     * @return
     */
    public GridBagHelper addMergedRows(int quantity) {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.gridheight = quantity;
        return duplicate;
    }

    /**
     * @return
     */
    public GridBagHelper addRemainingRows() {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.gridheight = REMAINDER;
        return duplicate;
    }

    /**
     * @param alignment
     * @return
     */
    public GridBagHelper addAlignment(int alignment) {
        GridBagHelper duplicate = (GridBagHelper) this.clone();
        duplicate.fill = NONE;
        duplicate.anchor = alignment;
        return duplicate;
    }
}