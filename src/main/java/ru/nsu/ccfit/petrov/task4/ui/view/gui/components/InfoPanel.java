package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicProgressBarUI;
import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.petrov.task4.observer.Observable;
import ru.nsu.ccfit.petrov.task4.observer.Observer;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;
import ru.nsu.ccfit.petrov.task4.observer.context.StorageMovingContext;
import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

/**
 * The type {@code Info panel} is abstract class that describes panel that contains product information, receives
 * change message and sends requests to change factory state.
 *
 * @author ptrvsrg
 */
public abstract class InfoPanel
    extends JPanel
    implements Observer {

    private static final int SLIDER_MAX_VALUE = 30;
    private static final int SLIDER_MAJOR_SPACING = SLIDER_MAX_VALUE / 6;
    private static final int SLIDER_MINOR_SPACING = 1;
    private static final Font FONT = new Font(Font.DIALOG, Font.PLAIN, 32);
    protected final UIController controller;
    private final JLabel totalProductCounter = new JLabel();
    private final JProgressBar currentProductCounter = new JProgressBar();
    private final JSlider timeSlider = new JSlider(JSlider.HORIZONTAL);

    /**
     * Constructs a InfoPanel.
     *
     * @param controller the controller
     */
    protected InfoPanel(UIController controller) {
        this.controller = controller;
        initPanel();
    }

    private void initPanel() {
        initTotalProductCount();
        initCurrentProductCount();
        initTimeSlider();

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        add(totalProductCounter, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2;
        add(currentProductCounter, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 4;
        add(createSliderTitle(), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 5;
        add(timeSlider, gbc);
    }

    private void initTotalProductCount() {
        totalProductCounter.setHorizontalAlignment(SwingConstants.CENTER);
        totalProductCounter.setFont(FONT);
        setTotalProductCount(0);
    }

    private void initCurrentProductCount() {
        currentProductCounter.setFont(FONT);
        currentProductCounter.setBorderPainted(false);
        currentProductCounter.setStringPainted(true);
        currentProductCounter.setForeground(Color.BLUE);
        currentProductCounter.setBackground(Color.WHITE);
        currentProductCounter.setUI(new BasicProgressBarUI() {
            /**
             * The "selectionForeground" is the color of the text when it is painted over a filled
             * area of the progress bar.
             *
             * @return the color of the selected foreground
             */
            @Override
            protected Color getSelectionForeground() {
                return Color.WHITE;
            }

            /**
             * The "selectionBackground" is the color of the text when it is painted over an
             * unfilled area of the progress bar.
             *
             * @return the color of the selected background
             */
            @Override
            protected Color getSelectionBackground() {
                return Color.BLACK;
            }
        });
        setCurrentProductCount(0);
    }

    private void initTimeSlider() {
        timeSlider.setBackground(Color.WHITE);
        timeSlider.setFont(FONT);
        timeSlider.setMajorTickSpacing(SLIDER_MAJOR_SPACING);
        timeSlider.setMinorTickSpacing(SLIDER_MINOR_SPACING);
        timeSlider.setPaintTrack(true);
        timeSlider.setPaintTicks(true);
        timeSlider.setPaintLabels(true);
        timeSlider.setMinimum(0);
        timeSlider.setMaximum(SLIDER_MAX_VALUE);
        timeSlider.setValue(SLIDER_MAX_VALUE / 2);
        timeSlider.addChangeListener(new SliderListener());

        setFactoryTime(SLIDER_MAX_VALUE / 2);
    }

    private JLabel createSliderTitle() {
        JLabel sliderTitle = new JLabel();
        sliderTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sliderTitle.setFont(FONT);
        sliderTitle.setText(getTimeSliderTitle());

        return sliderTitle;
    }

    private void setTotalProductCount(Integer totalProductCount) {
        totalProductCounter.setText(getTotalProductCounterTitle() + totalProductCount);
        totalProductCounter.repaint();
    }

    private void setCurrentProductCount(Integer currentProductCount) {
        String values = String.format("%s / %s", currentProductCount, getStorageCapacity());
        currentProductCounter.setString(getCurrentProductCounterTitle() + values);
        currentProductCounter.setValue(100 * currentProductCount / getStorageCapacity());
        currentProductCounter.repaint();
    }

    /**
     * Sets factory time.
     *
     * @param time the time
     */
    protected abstract void setFactoryTime(int time);

    /**
     * Gets total product counter title.
     *
     * @return the total product counter title
     */
    protected abstract String getTotalProductCounterTitle();

    /**
     * Gets current product counter title.
     *
     * @return the current product counter title
     */
    protected abstract String getCurrentProductCounterTitle();

    /**
     * Gets time slider title.
     *
     * @return the time slider title
     */
    protected abstract String getTimeSliderTitle();

    /**
     * Handles the context of the {@link Observable Observable} object message.
     *
     * @param context the context
     */
    @Override
    public void update(Context context) {
        if (!context.getClass().equals(StorageMovingContext.class)) {
            return;
        }

        setCurrentProductCount(((StorageMovingContext) context).getCurrentProductCount());
        setTotalProductCount(((StorageMovingContext) context).getTotalProductCount());
    }

    /**
     * Gets storage capacity.
     *
     * @return the storage capacity
     */
    protected abstract int getStorageCapacity();

    @RequiredArgsConstructor
    private class SliderListener
        implements ChangeListener {

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            int time = Math.max(1, timeSlider.getValue());

            setFactoryTime(time);
        }
    }
}
