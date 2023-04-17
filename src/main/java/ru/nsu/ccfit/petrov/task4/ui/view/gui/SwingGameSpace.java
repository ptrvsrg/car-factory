package ru.nsu.ccfit.petrov.task4.ui.view.gui;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.AccessoryInfoPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.BodyInfoPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.CarInfoPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.EngineInfoPanel;

public class SwingGameSpace {

    private static final String TITLE = "Car Factory";
    private final JFrame frame = new JFrame();
    private final UIController controller;
    private final EngineInfoPanel engineInfoPanel;
    private final BodyInfoPanel bodyInfoPanel;
    private final AccessoryInfoPanel accessoryInfoPanel;
    private final CarInfoPanel carInfoPanel;

    public SwingGameSpace(UIController controller) {
        this.controller = controller;

        controller.initFactory();

        engineInfoPanel = new EngineInfoPanel(controller);
        bodyInfoPanel = new BodyInfoPanel(controller);
        accessoryInfoPanel = new AccessoryInfoPanel(controller);
        carInfoPanel = new CarInfoPanel(controller);

        initFrame();

        controller.startFactory();

        frame.setVisible(true);
    }

    private void initFrame() {
        frame.setTitle(TITLE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        frame.add(engineInfoPanel);
        frame.add(bodyInfoPanel);
        frame.add(accessoryInfoPanel);
        frame.add(carInfoPanel);
    }
}
