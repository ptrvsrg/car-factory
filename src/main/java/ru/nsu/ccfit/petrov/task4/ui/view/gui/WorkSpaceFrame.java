package ru.nsu.ccfit.petrov.task4.ui.view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.AccessoryInfoPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.BodyInfoPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.CarInfoPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.EngineInfoPanel;

/**
 * The type {@code WorkSpaceFrame} is class that describes work space frame, all of its components and listeners.
 *
 * @author ptrvsrg
 */
public class WorkSpaceFrame {

    private static final String TITLE = "Car Factory";
    private final JFrame frame = new JFrame();
    private final UIController controller;
    private final EngineInfoPanel engineInfoPanel;
    private final BodyInfoPanel bodyInfoPanel;
    private final AccessoryInfoPanel accessoryInfoPanel;
    private final CarInfoPanel carInfoPanel;

    /**
     * Constructs a WorkSpaceFrame.
     *
     * @param controller the controller
     */
    public WorkSpaceFrame(UIController controller) {
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
        frame.setSize(1200, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2, 5, 5));
        frame.addWindowListener(new WindowClosingListener());

        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(engineInfoPanel);
        frame.getContentPane().add(bodyInfoPanel);
        frame.getContentPane().add(accessoryInfoPanel);
        frame.getContentPane().add(carInfoPanel);
    }

    @RequiredArgsConstructor
    private class WindowClosingListener
        extends WindowAdapter {

        private static final String EXIT_CONFIRM_TITLE = "Confirmation";
        private static final String EXIT_CONFIRM_MESSAGE = "Are you sure?";

        /**
         * Invoked when the user attempts to close the window from the window's system menu.
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
