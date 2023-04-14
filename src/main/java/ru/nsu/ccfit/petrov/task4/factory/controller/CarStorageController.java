package ru.nsu.ccfit.petrov.task4.factory.controller;

import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;
import ru.nsu.ccfit.petrov.task4.factory.worker.AssemblingTask;
import ru.nsu.ccfit.petrov.task4.factory.worker.WorkerDepartment;
import ru.nsu.ccfit.petrov.task4.observer.Observable;
import ru.nsu.ccfit.petrov.task4.observer.Observer;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;
import ru.nsu.ccfit.petrov.task4.observer.context.StorageMovingContext;

public class CarStorageController
    implements Observer {

    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<SeatCover> accessoryStorage;
    private final Storage<Car> carStorage;

    public CarStorageController(Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                                Storage<SeatCover> accessoryStorage, Storage<Car> carStorage,
                                WorkerDepartment workerDepartment) {
        this.engineStorage = engineStorage;
        this.bodyStorage = bodyStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
        this.workerDepartment = workerDepartment;

        carStorage.addObserver(this);
    }

    private final WorkerDepartment workerDepartment;

    /**
     * Handles the context of the {@link Observable Observable} object message.
     *
     * @param context the context
     */
    @Override
    public void update(Context context) {
        String contextName = context.getClass().getSimpleName();
        if (contextName.equals(StorageMovingContext.class.getSimpleName())) {
            StorageMovingContext storageMovingContext = (StorageMovingContext) context;
            evaluateStorageStatus(storageMovingContext.getCurrentProductCount(),
                                  storageMovingContext.getCapacity());
        }
    }

    private void evaluateStorageStatus(int currentProductCount, int capacity) {
        int minProductCount = (int) (capacity * 0.75);
        if (currentProductCount < minProductCount) {
            workerDepartment.addTask(
                new AssemblingTask(engineStorage, bodyStorage, accessoryStorage, carStorage));
        }
    }
}
