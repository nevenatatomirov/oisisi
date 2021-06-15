package com.pozoriste.pages.EventPage;

import javax.swing.*;
import java.awt.*;

public class SeatsLayout extends GridLayout {
    public SeatsLayout(int rows, int cols) {
        super(rows, cols);

        setHgap(5);
        setVgap(15);
    }

    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int num_components = parent.getComponentCount();
            int num_rows = getRows();
            int num_cols = getColumns();

            if (num_components == 0) {
                return;
            }
            if (num_rows > 0) {
                num_cols = (num_components + num_rows - 1) / num_rows;
            } else {
                num_rows = (num_components + num_cols - 1) / num_cols;
            }

            int totalGapsWidth = (num_cols - 1) * getHgap();
            int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
            int widthOnComponent = (widthWOInsets - totalGapsWidth) / num_cols;
            int extraWidthAvailable = (widthWOInsets - (widthOnComponent * num_cols + totalGapsWidth)) / 2;

            int totalGapsHeight = (num_rows - 1) * getVgap();
            int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
            int heightOnComponent = (heightWOInsets - totalGapsHeight) / num_rows;
            int extraHeightAvailable = (heightWOInsets - (heightOnComponent * num_rows + totalGapsHeight)) / 2;

            int size=Math.min(widthOnComponent, heightOnComponent);
            widthOnComponent=size;
            heightOnComponent=size;

            for (int c = 0, x = insets.left + extraWidthAvailable; c < num_cols ; c++, x += widthOnComponent + getHgap()) {
                for (int r = 0, y = insets.top + extraHeightAvailable; r < num_rows; r++, y += heightOnComponent + getVgap()) {
                    int i = r * num_cols + c;
                    if (i < num_components) {
                        JButton button = (JButton) parent.getComponent(i);
                        button.setBounds(x, y, widthOnComponent, heightOnComponent);
                        button.setBorderPainted(false);

                        int margin = 15;
                        button.setMargin(new Insets(margin, margin, margin, margin));
                    }
                }
            }
        }
    }
}
