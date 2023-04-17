package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

/**
 * The type {@code EngineInfoPanel} is class that implements abstract methods of
 * {@link ru.nsu.ccfit.petrov.task4.ui.view.gui.components.InfoPanel InfoPanel} and describes engine information.
 *
 * @author ptrvsrg
 */
public class EngineInfoPanel
    extends InfoPanel {

    /**
     * Constructs a EngineInfoPanel.
     *
     * @param controller the controller
     */
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
