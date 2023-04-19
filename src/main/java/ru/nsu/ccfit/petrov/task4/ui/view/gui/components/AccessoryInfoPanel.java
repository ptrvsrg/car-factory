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
        controller.addAccessoryStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Accessory produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Accessories in storage: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Accessory production time in sec: ";
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
