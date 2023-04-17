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

    private static final float OCCUPANCY_PERCENTAGE = 0.75F;
    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<SeatCover> accessoryStorage;
    private final Storage<Car> carStorage;
    private final WorkerDepartment workerDepartment;

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

    private void assembleCar() {
        workerDepartment.addTask(new AssemblingTask(engineStorage, bodyStorage, accessoryStorage, carStorage));
    }

    /**
     * Handles the context of the {@link Observable Observable} object message.
     *
     * @param context the context
     */
    @Override
    public void update(Context context) {
        if (!context.getClass().equals(StorageMovingContext.class)) {
            return;
        }

        evaluateStorageStatus(((StorageMovingContext) context).getCurrentProductCount(),
                              carStorage.getCapacity());
    }

    private void evaluateStorageStatus(int currentCarCount, int carStorageCapacity) {
        int minCarCount = (int) (carStorageCapacity * OCCUPANCY_PERCENTAGE);
        int assemblingCarCount = workerDepartment.getQueueSize();

        if (currentCarCount + assemblingCarCount < minCarCount) {
            for (int i = 0; i < carStorageCapacity - minCarCount; ++i) {
                assembleCar();
            }
        }
    }
}
