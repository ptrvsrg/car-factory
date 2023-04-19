package ru.nsu.ccfit.petrov.task4.ui.controller;

import ru.nsu.ccfit.petrov.task4.factory.Factory;
import ru.nsu.ccfit.petrov.task4.factory.config.FactoryConfig;
import ru.nsu.ccfit.petrov.task4.observer.Observer;

/**
 * The type {@code UIController} is class that sends requests to change the state of the model from the view
 * layer to the model layer. Factory is model in this case.
 *
 * @author ptrvsrg
 */
public class UIController {

    private Factory factory;

    /**
     * Initializes factory.
     */
    public void initFactory() {
        factory = new Factory();
    }

    private void checkFactory() {
        if (factory == null) {
            throw new UnsupportedOperationException("Factory is uninitialized");
        }
    }

    /**
     * Starts factory.
     */
    public void startFactory() {
        checkFactory();
        factory.start();
    }

    /**
     * Stops factory.
     */
    public void stopFactory() {
        checkFactory();
        factory.stop();
        factory = null;
    }

    /**
     * Gets engine storage capacity.
     *
     * @return the engine storage capacity
     */
    public int getEngineStorageCapacity() {
        return FactoryConfig.getEngineStorageCapacity();
    }

    /**
     * Gets body storage capacity.
     *
     * @return the body storage capacity
     */
    public int getBodyStorageCapacity() {
        return FactoryConfig.getBodyStorageCapacity();
    }

    /**
     * Gets accessory storage capacity.
     *
     * @return the accessory storage capacity
     */
    public int getAccessoryStorageCapacity() {
        return FactoryConfig.getAccessoryStorageCapacity();
    }

    /**
     * Gets car storage capacity.
     *
     * @return the car storage capacity
     */
    public int getCarStorageCapacity() {
        return FactoryConfig.getCarStorageCapacity();
    }

    /**
     * Sets engine production time.
     *
     * @param engineProductionTime the engine production time
     */
    public void setEngineProductionTime(int engineProductionTime) {
        checkFactory();
        factory.setEngineProductionTime(engineProductionTime * 1000);
    }

    /**
     * Sets body production time.
     *
     * @param bodyProductionTime the body production time
     */
    public void setBodyProductionTime(int bodyProductionTime) {
        checkFactory();
        factory.setBodyProductionTime(bodyProductionTime * 1000);
    }

    /**
     * Sets accessory production time.
     *
     * @param accessoryProductionTime the accessory production time
     */
    public void setAccessoryProductionTime(int accessoryProductionTime) {
        checkFactory();
        factory.setAccessoryProductionTime(accessoryProductionTime * 1000);
    }

    /**
     * Sets car sale time.
     *
     * @param saleTime the sale time
     */
    public void setCarSaleTime(int saleTime) {
        checkFactory();
        factory.setCarSaleTime(saleTime * 1000);
    }

    /**
     * Adds engine storage observer.
     *
     * @param observer the observer
     */
    public void addEngineStorageObserver(Observer observer) {
        checkFactory();
        factory.addEngineStorageObserver(observer);
    }

    /**
     * Adds body storage observer.
     *
     * @param observer the observer
     */
    public void addBodyStorageObserver(Observer observer) {
        checkFactory();
        factory.addBodyStorageObserver(observer);
    }

    /**
     * Adds accessory storage observer.
     *
     * @param observer the observer
     */
    public void addAccessoryStorageObserver(Observer observer) {
        checkFactory();
        factory.addAccessoryStorageObserver(observer);
    }

    /**
     * Adds car storage observer.
     *
     * @param observer the observer
     */
    public void addCarStorageObserver(Observer observer) {
        checkFactory();
        factory.addCarStorageObserver(observer);
    }
}
