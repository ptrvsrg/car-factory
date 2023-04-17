package ru.nsu.ccfit.petrov.task4;

import javax.swing.SwingUtilities;
import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.StartMenuFrame;

/**
 * The type {@code Main} is class that contains static method {@code main}.
 *
 * @author ptrvsrg
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        UIController controller = new UIController();
        SwingUtilities.invokeLater(() -> new StartMenuFrame(controller));
    }
}