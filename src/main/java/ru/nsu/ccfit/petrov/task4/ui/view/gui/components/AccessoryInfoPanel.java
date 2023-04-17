package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

public class AccessoryInfoPanel
    extends InfoPanel {

    public AccessoryInfoPanel(UIController controller) {
        super(controller);
        controller.addCarStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Total accessory count: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Current accessory count: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Accessory production time: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getAccessoryStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setAccessoryProductionTime(time);
    }
}
