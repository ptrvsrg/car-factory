package ru.nsu.ccfit.petrov.task4.factory;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.config.FactoryConfig;
import ru.nsu.ccfit.petrov.task4.factory.controller.CarStorageController;
import ru.nsu.ccfit.petrov.task4.factory.dealer.Dealer;
import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;
import ru.nsu.ccfit.petrov.task4.factory.supplier.Supplier;
import ru.nsu.ccfit.petrov.task4.factory.worker.WorkerDepartment;
import ru.nsu.ccfit.petrov.task4.observer.Observer;

/**
 * The type {@code Factory} is class that describes factory, contains all its components, sets components dependencies,
 * starts and stops threads.
 *
 * @author ptrvsrg
 */
@Log4j2
public class Factory {

    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<SeatCover> accessoryStorage;
    private final Storage<Car> carStorage;
    private final Supplier<Engine> engineSupplier;
    private final Supplier<Body> bodySupplier;
    private final List<Supplier<SeatCover>> accessorySuppliers;
    private final WorkerDepartment workerDepartment;
    private final CarStorageController controller;
    private final List<Dealer> dealers;

    /**
     * Constructs a Factory.
     */
    public Factory() {
        log.info("Create storages");
        engineStorage = new Storage<>(FactoryConfig.getEngineStorageCapacity());
        bodyStorage = new Storage<>(FactoryConfig.getBodyStorageCapacity());
        accessoryStorage = new Storage<>(FactoryConfig.getAccessoryStorageCapacity());
        carStorage = new Storage<>(FactoryConfig.getCarStorageCapacity());

        log.info("Create suppliers");
        engineSupplier = new Supplier<>(engineStorage, Engine.class);
        bodySupplier = new Supplier<>(bodyStorage, Body.class);
        accessorySuppliers = new ArrayList<>();
        for (int i = 0; i < FactoryConfig.getAccessorySupplierCount(); ++i) {
            accessorySuppliers.add(new Supplier<>(accessoryStorage, SeatCover.class));
        }

        log.info("Create worker department");
        workerDepartment = new WorkerDepartment(FactoryConfig.getWorkerCount());

        log.info("Create controller for car storage");
        controller = new CarStorageController(engineStorage, bodyStorage, accessoryStorage, carStorage,
                                              workerDepartment);

        log.info("Create dealers");
        dealers = new ArrayList<>();
        for (int i = 0; i < FactoryConfig.getDealerCount(); ++i) {
            dealers.add(new Dealer(carStorage));
        }

    }

    /**
     * Sets engine production time.
     *
     * @param productionTime the production time
     */
    public void setEngineProductionTime(int productionTime) {
        engineSupplier.setProductionTime(productionTime);
    }

    /**
     * Sets body production time.
     *
     * @param productionTime the production time
     */
    public void setBodyProductionTime(int productionTime) {
        bodySupplier.setProductionTime(productionTime);
    }

    /**
     * Sets accessory production time.
     *
     * @param productionTime the production time
     */
    public void setAccessoryProductionTime(int productionTime) {
        for (Supplier<SeatCover> accessorySupplier : accessorySuppliers) {
            accessorySupplier.setProductionTime(productionTime);
        }
    }

    /**
     * Sets car sale time.
     *
     * @param saleTime the sale time
     */
    public void setCarSaleTime(int saleTime) {
        for (Dealer dealer : dealers) {
            dealer.setSaleTime(saleTime);
        }
    }

    /**
     * Add engine storage observer.
     *
     * @param observer the observer
     */
    public void addEngineStorageObserver(Observer observer) {
        engineStorage.addObserver(observer);
    }

    /**
     * Add body storage observer.
     *
     * @param observer the observer
     */
    public void addBodyStorageObserver(Observer observer) {
        bodyStorage.addObserver(observer);
    }

    /**
     * Add accessory storage observer.
     *
     * @param observer the observer
     */
    public void addAccessoryStorageObserver(Observer observer) {
        accessoryStorage.addObserver(observer);
    }

    /**
     * Add car storage observer.
     *
     * @param observer the observer
     */
    public void addCarStorageObserver(Observer observer) {
        carStorage.addObserver(observer);
    }

    /**
     * Start factory.
     */
    public void start() {
        log.info("Start factory");
        engineSupplier.start();
        bodySupplier.start();
        for (Supplier<SeatCover> accessorySupplier : accessorySuppliers) {
            accessorySupplier.start();
        }
        workerDepartment.start();
        for (Dealer dealer : dealers) {
            dealer.start();
        }
    }

    /**
     * Stop factory.
     */
    public void stop() {
        for (Dealer dealer : dealers) {
            dealer.interrupt();
        }
        workerDepartment.stop();
        engineSupplier.interrupt();
        bodySupplier.interrupt();
        for (Supplier<SeatCover> accessorySupplier : accessorySuppliers) {
            accessorySupplier.interrupt();
        }

        log.info("Stop factory");
    }
}
