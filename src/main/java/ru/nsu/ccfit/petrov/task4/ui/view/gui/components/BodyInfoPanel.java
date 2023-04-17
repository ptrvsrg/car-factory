package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

public class BodyInfoPanel
    extends SwingInfoPanel {

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
