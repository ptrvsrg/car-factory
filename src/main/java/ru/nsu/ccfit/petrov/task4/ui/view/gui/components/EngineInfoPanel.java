package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

public class EngineInfoPanel
    extends SwingInfoPanel{

    public EngineInfoPanel(UIController controller) {
        super(controller);
        controller.addEngineStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Total engine count: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Current engine count: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Engine production time: ";
    }

    @Override
    protected int getStorageCapacity() {
        return controller.getEngineStorageCapacity();
    }

    @Override
    protected void setFactoryTime(int time) {
        controller.setEngineProductionTime(time);
    }
}
