package ru.nsu.ccfit.petrov.task4.ui.controller;

import ru.nsu.ccfit.petrov.task4.factory.Factory;
import ru.nsu.ccfit.petrov.task4.factory.config.FactoryConfig;
import ru.nsu.ccfit.petrov.task4.observer.Observer;

public class UIController {

    private Factory factory;

    public void initFactory() {
        factory = new Factory();
    }

    private void checkFactory() {
        if (factory == null) {
            throw new UnsupportedOperationException("Factory is uninitialized");
        }
    }

    public void startFactory() {
        checkFactory();
        factory.start();
    }

    public void stopFactory() {
        checkFactory();
        factory.stop();
        factory = null;
    }

    public int getEngineStorageCapacity() {
        return FactoryConfig.getEngineStorageCapacity();
    }

    public int getBodyStorageCapacity() {
        return FactoryConfig.getBodyStorageCapacity();
    }

    public int getAccessoryStorageCapacity() {
        return FactoryConfig.getAccessoryStorageCapacity();
    }

    public int getCarStorageCapacity() {
        return FactoryConfig.getCarStorageCapacity();
    }

    public void setEngineProductionTime(int engineProductionTime) {
        checkFactory();
        factory.setEngineProductionTime(engineProductionTime);
    }

    public void setBodyProductionTime(int bodyProductionTime) {
        checkFactory();
        factory.setBodyProductionTime(bodyProductionTime);
    }

    public void setAccessoryProductionTime(int accessoryProductionTime) {
        checkFactory();
        factory.setAccessoryProductionTime(accessoryProductionTime);
    }

    public void setCarSaleTime(int saleTime) {
        checkFactory();
        factory.setCarSaleTime(saleTime);
    }

    public void addEngineStorageObserver(Observer observer) {
        checkFactory();
        factory.addEngineStorageObserver(observer);
    }

    public void addBodyStorageObserver(Observer observer) {
        checkFactory();
        factory.addBodyStorageObserver(observer);
    }

    public void addAccessoryStorageObserver(Observer observer) {
        checkFactory();
        factory.addAccessoryStorageObserver(observer);
    }

    public void addCarStorageObserver(Observer observer) {
        checkFactory();
        factory.addCarStorageObserver(observer);
    }
}
