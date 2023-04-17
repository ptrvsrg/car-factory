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
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
        frame.addWindowListener(new WindowClosingListener());

        frame.add(engineInfoPanel);
        frame.add(bodyInfoPanel);
        frame.add(accessoryInfoPanel);
        frame.add(carInfoPanel);
    }

    @RequiredArgsConstructor
    private class WindowClosingListener
        extends WindowAdapter {

        private static final String EXIT_CONFIRM_TITLE = "Confirmation";
        private static final String EXIT_CONFIRM_MESSAGE = "Are you sure?";

        /**
         * Invoked when a window is in the process of being closed. The close operation can be overridden at this point.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosing(WindowEvent e) {
            int res = JOptionPane.showConfirmDialog(frame, EXIT_CONFIRM_MESSAGE, EXIT_CONFIRM_TITLE,
                                                    JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                controller.stopFactory();
                frame.dispose();
                System.exit(0);
            }
        }
    }
}
