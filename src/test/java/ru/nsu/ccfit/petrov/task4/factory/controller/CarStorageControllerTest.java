package ru.nsu.ccfit.petrov.task4.factory.controller;

import java.lang.reflect.Field;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;
import ru.nsu.ccfit.petrov.task4.factory.worker.WorkerDepartment;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;
import ru.nsu.ccfit.petrov.task4.observer.context.StorageMovingContext;
import ru.nsu.ccfit.petrov.task4.threadpool.Task;

public class CarStorageControllerTest
    extends Assertions {

    private static final int CAR_STORAGE_CAPACITY = 10;
    private WorkerDepartment workerDepartment;
    private CarStorageController carStorageController;

    @BeforeClass
    public void beforeClass() {
        Storage<Engine> engineStorage = Mockito.mock(Storage.class);
        Storage<Body> bodyStorage = Mockito.mock(Storage.class);
        Storage<SeatCover> accessoryStorage = Mockito.mock(Storage.class);
        Storage<Car> carStorage = Mockito.mock(Storage.class);
        workerDepartment = Mockito.mock(WorkerDepartment.class);

        Mockito.when(carStorage.getCapacity()).thenReturn(CAR_STORAGE_CAPACITY);

        carStorageController = new CarStorageController(engineStorage, bodyStorage, accessoryStorage, carStorage,
                                                        workerDepartment);
    }

    private float getOccupancyPercentage()
        throws NoSuchFieldException, IllegalAccessException {
        Field occupancyPercentageField = CarStorageController.class.getDeclaredField("OCCUPANCY_PERCENTAGE");
        occupancyPercentageField.setAccessible(true);
        return (float) occupancyPercentageField.get(null);
    }

    @Test(description = "Check initial assembly request",
          groups = "Car storage controller")
    public void checkInitialAssembleRequest() {
        // check
        Mockito.verify(workerDepartment, Mockito.times(CAR_STORAGE_CAPACITY)).addTask(Mockito.any(Task.class));
    }

    @Test(description = "Check updating when context is incorrect",
          groups = "Car storage controller")
    public void checkUpdateWhenContextIsIncorrect() {
        // prepare
        Mockito.clearInvocations(workerDepartment);

        // do
        carStorageController.update(new Context() {});

        // check
        Mockito.verify(workerDepartment, Mockito.never()).addTask(Mockito.any(Task.class));
    }

    @DataProvider(name = "Create car current count when request must be send")
    private Object[][] createCarCurrentCountWhenRequestMustBeSend()
        throws NoSuchFieldException, IllegalAccessException {
        float occupancyPercentage = getOccupancyPercentage();
        int minCarCount = (int) (CAR_STORAGE_CAPACITY * occupancyPercentage);
        Object[][] data = new Object[minCarCount][1];

        for (int i = 0; i < minCarCount; ++i) {
            data[i][0] = i;
        }

        return data;
    }

    @Test(description = "Check updating when request must be send",
          groups = "Car storage controller",
          dataProvider = "Create car current count when request must be send")
    public void checkUpdateWhenRequestMustBeSend(int carCurrentCount) {
        // prepare
        StorageMovingContext storageMovingContext = new StorageMovingContext(carCurrentCount, CAR_STORAGE_CAPACITY);

        Mockito.clearInvocations(workerDepartment);
        Mockito.when(workerDepartment.getQueueSize()).thenReturn(0);

        // do
        carStorageController.update(storageMovingContext);

        // check
        Mockito.verify(workerDepartment, Mockito.times(CAR_STORAGE_CAPACITY - carCurrentCount))
               .addTask(Mockito.any(Task.class));
    }

    @DataProvider(name = "Create car current count when request must not be send")
    private Object[][] createCarCurrentCountWhenRequestMustNotBeSend()
        throws NoSuchFieldException, IllegalAccessException {
        float occupancyPercentage = getOccupancyPercentage();
        int minCarCount = (int) (CAR_STORAGE_CAPACITY * occupancyPercentage);
        Object[][] data = new Object[CAR_STORAGE_CAPACITY - minCarCount + 1][1];

        for (int i = 0; i < CAR_STORAGE_CAPACITY - minCarCount + 1; ++i) {
            data[i][0] = CAR_STORAGE_CAPACITY - i;
        }

        return data;
    }

    @Test(description = "Check updating when request must not be send",
          groups = "Car storage controller",
          dataProvider = "Create car current count when request must not be send")
    public void checkUpdateWhenRequestMustNotBeSend(int carCurrentCount) {
        // prepare
        StorageMovingContext storageMovingContext = new StorageMovingContext(carCurrentCount, CAR_STORAGE_CAPACITY);

        Mockito.clearInvocations(workerDepartment);
        Mockito.when(workerDepartment.getQueueSize()).thenReturn(0);

        // do
        carStorageController.update(storageMovingContext);

        // check
        Mockito.verify(workerDepartment, Mockito.never())
               .addTask(Mockito.any(Task.class));
    }
}