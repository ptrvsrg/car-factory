package ru.nsu.ccfit.petrov.task4.ui.view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.SwingBackgroundPanel;
import ru.nsu.ccfit.petrov.task4.ui.view.gui.components.SwingMenuButton;

public class SwingStartMenu {

    private static final String TITLE = "Welcome To Car Factory";
    private static final String BACKGROUND_IMAGE_FILE = "start_menu_background.png";
    private static final String START_BUTTON_TITLE = "Start";
    private static final String ABOUT_BUTTON_TITLE = "About";
    private static final String ABOUT_PANEL_TITLE = "About";
    private static final String ABOUT_PANEL_MESSAGE =
        "Program \"Minesweeper v1.0\"\n" + "Designer: ptrvsrg\n" + "Developer: ptrvsrg\n"
            + "Source code: https://github.com/ptrvsrg/NSU_OOP_Java/tree/master/Task4";
    private final JFrame frame = new JFrame();
    private final UIController controller;

    public SwingStartMenu(UIController controller) {
        this.controller = controller;

        initFrame();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame.setTitle(TITLE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosingListener());

        URL url = SwingStartMenu.class.getClassLoader().getResource(BACKGROUND_IMAGE_FILE);
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage(url);
        SwingBackgroundPanel contentPane = new SwingBackgroundPanel(backgroundImage);
        contentPane.add(createButtonArea());

        frame.setContentPane(contentPane);
    }

    private JPanel createButtonArea() {
        JPanel buttonArea = new JPanel();
        buttonArea.setSize(frame.getWidth() / 2, frame.getHeight() / 2);
        buttonArea.setBackground(new Color(0, 0, 0, 0));
        buttonArea.setLayout(new GridLayout(2, 1));
        buttonArea.add(createMenuButton(START_BUTTON_TITLE, new StartButtonListener()));
        buttonArea.add(createMenuButton(ABOUT_BUTTON_TITLE, new AboutButtonListener()));

        return buttonArea;
    }

    private SwingMenuButton createMenuButton(String title, ActionListener listener) {
        SwingMenuButton startButton = new SwingMenuButton(title);
        startButton.addActionListener(listener);

        return startButton;
    }

    private class StartButtonListener
        implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new SwingGameSpace(controller));
        }
    }

    private class AboutButtonListener
        implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, ABOUT_PANEL_MESSAGE, ABOUT_PANEL_TITLE,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
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
                frame.dispose();
                System.exit(0);
            }
        }
    }
}

