package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

/**
 * The type {@code BodyInfoPanel} is class that implements abstract methods of
 *  {@link ru.nsu.ccfit.petrov.task4.ui.view.gui.components.InfoPanel InfoPanel} and describes body information.
 *
 * @author ptrvsrg
 */
public class BodyInfoPanel
    extends InfoPanel {

    /**
     * Constructs a BodyInfoPanel.
     *
     * @param controller the controller
     */
    public BodyInfoPanel(UIController controller) {
        super(controller);
        controller.addBodyStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Body produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Bodies in storage: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Body production time in sec: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getBodyStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setBodyProductionTime(time);
    }
}
