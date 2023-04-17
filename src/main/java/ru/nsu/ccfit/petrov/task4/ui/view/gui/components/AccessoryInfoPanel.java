package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

/**
 * The type {@code AccessoryInfoPanel} is class that implements abstract methods of
 * {@link ru.nsu.ccfit.petrov.task4.ui.view.gui.components.InfoPanel InfoPanel} and describes accessory information.
 *
 * @author ptrvsrg
 */
public class AccessoryInfoPanel
    extends InfoPanel {

    /**
     * Constructs a AccessoryInfoPanel.
     *
     * @param controller the controller
     */
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
