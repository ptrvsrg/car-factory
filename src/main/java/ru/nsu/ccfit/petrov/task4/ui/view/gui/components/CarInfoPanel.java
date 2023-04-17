package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

public class CarInfoPanel
    extends SwingInfoPanel {

    public CarInfoPanel(UIController controller) {
        super(controller);
        controller.addCarStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Total car count: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Current car count: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Car sale time: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getCarStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setCarSaleTime(time);
    }
}
