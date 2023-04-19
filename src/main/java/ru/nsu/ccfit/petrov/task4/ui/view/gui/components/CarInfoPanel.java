package ru.nsu.ccfit.petrov.task4.ui.view.gui.components;

import ru.nsu.ccfit.petrov.task4.ui.controller.UIController;

/**
 * The type {@code CarInfoPanel} is class that implements abstract methods of
 *  {@link ru.nsu.ccfit.petrov.task4.ui.view.gui.components.InfoPanel InfoPanel} and describes car information.
 *
 * @author ptrvsrg
 */
public class CarInfoPanel
    extends InfoPanel {

    /**
     * Constructs a CarInfoPanel.
     *
     * @param controller the controller
     */
    public CarInfoPanel(UIController controller) {
        super(controller);
        controller.addCarStorageObserver(this);
    }

    @Override
    protected String getTotalProductCounterTitle() {
        return "Car produced: ";
    }

    @Override
    protected String getCurrentProductCounterTitle() {
        return "Cars in storage: ";
    }

    @Override
    protected String getTimeSliderTitle() {
        return "Car sale time in sec: ";
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
