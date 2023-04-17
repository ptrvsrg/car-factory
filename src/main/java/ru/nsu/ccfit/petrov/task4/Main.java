package ru.nsu.ccfit.petrov.task4;

import javax.swing.SwingUtilities;
import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.StartMenuFrame;

public class Main {
    public static void main(String[] args) {
        try {
            UIController controller = new UIController();
            SwingUtilities.invokeLater(() -> new StartMenuFrame(controller));
        } catch (Exception e) {
            System.exit(1);
        }
    }
}