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
        return "Total body count: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Current body count: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Body production time: ";
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
