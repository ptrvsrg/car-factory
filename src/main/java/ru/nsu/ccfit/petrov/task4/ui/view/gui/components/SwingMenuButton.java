package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

/**
 * The type {@code MenuButton} is class that describe the button for start and finish menu.
 *
 * @author ptrvsrg
 */
public class SwingMenuButton
    extends JButton {
    private static final int FONT_SIZE = 40;

    /**
     * Instantiates a new MenuButton.
     *
     * @param text the text
     */
    public SwingMenuButton(String text) {
        setText(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
        setForeground(Color.WHITE);
    }
}