package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

public abstract class SwingInfoPanel
    extends JPanel
    implements Observer {

    private static final int SLIDER_MAX_VALUE = 60000;
    private static final int SLIDER_MAJOR_SPACING = SLIDER_MAX_VALUE / 6;
    private static final int BORDER_SIZE = 10;
    private static final int FONT_SIZE = 20;
    protected final UIController controller;
    private final JLabel totalProductCounter = new JLabel();
    private final JLabel currentProductCounter = new JLabel();
    private final JSlider timeSlider = new JSlider(JSlider.HORIZONTAL);

    public SwingInfoPanel(UIController controller) {
        this.controller = controller;

        setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(3, 2));

        initSlider();

        add(createTitleLabel(getTotalProductCounterTitle()));
        add(createTitleLabel(getCurrentProductCounterTitle()));
        add(createTitleLabel(getTimeSliderTitle()));

        add(totalProductCounter);
        add(currentProductCounter);
        add(timeSlider);
    }

    protected abstract String getTotalProductCounterTitle();

    protected abstract String getCurrentProductCounterTitle();

    protected abstract String getTimeSliderTitle();

    protected abstract int getStorageCapacity();

    protected abstract void setFactoryTime(int time);

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel();
        titleLabel.setText(title);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
        return titleLabel;
    }

    public void setTotalProductCount(Integer totalProductCount) {
        totalProductCounter.setText(totalProductCount.toString());
        totalProductCounter.repaint();
    }

    public void setCurrentProductCount(Integer currentProductCount) {
        currentProductCounter.setText(String.format("%s / %s", currentProductCount, getStorageCapacity()));
        currentProductCounter.repaint();
    }

    public void initSlider() {
        timeSlider.setMinimum(0);
        timeSlider.setMaximum(SLIDER_MAX_VALUE);
        timeSlider.setValue(SLIDER_MAX_VALUE);
        timeSlider.setMajorTickSpacing(SLIDER_MAJOR_SPACING);
        timeSlider.setPaintTrack(true);
        timeSlider.setPaintLabels(true);
        timeSlider.addChangeListener(new SliderListener());
    }

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
