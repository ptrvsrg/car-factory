package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private static final int BORDER_SIZE = 10;
    private static final int FONT_SIZE = 20;
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

        initTotalProductCount();
        initCurrentProductCount();
        initTimeSlider();
        initPanel();
    }

    private void initTotalProductCount() {
        totalProductCounter.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
        setTotalProductCount(0);
    }

    private void initCurrentProductCount() {
        currentProductCounter.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
        currentProductCounter.setForeground(Color.BLACK);
        setCurrentProductCount(0);
    }

    private void initTimeSlider() {
        timeSlider.setMinimum(0);
        timeSlider.setMaximum(SLIDER_MAX_VALUE);
        timeSlider.setValue(SLIDER_MAX_VALUE / 2);
        timeSlider.setMajorTickSpacing(SLIDER_MAJOR_SPACING);
        timeSlider.setPaintTrack(true);
        timeSlider.setPaintLabels(true);
        timeSlider.addChangeListener(new SliderListener());

        setFactoryTime(SLIDER_MAX_VALUE / 2);
    }

    private void initPanel() {
        setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        setLayout(new GridLayout(3, 2, BORDER_SIZE, BORDER_SIZE));

        add(createTitleLabel(getTotalProductCounterTitle()));
        add(totalProductCounter);

        add(createTitleLabel(getCurrentProductCounterTitle()));
        add(currentProductCounter);

        add(createTitleLabel(getTimeSliderTitle()));
        add(timeSlider);
    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel();
        titleLabel.setText(title);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
        return titleLabel;
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

    private void setTotalProductCount(Integer totalProductCount) {
        totalProductCounter.setText(totalProductCount.toString());
        totalProductCounter.repaint();
    }

    private void setCurrentProductCount(Integer currentProductCount) {
        currentProductCounter.setString(String.format("%s / %s", currentProductCount, getStorageCapacity()));
        currentProductCounter.setValue(100 * currentProductCount / getStorageCapacity());
        currentProductCounter.setStringPainted(true);
        currentProductCounter.repaint();
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
            int time = timeSlider.getValue();
            setFactoryTime(time);
        }
    }
}
