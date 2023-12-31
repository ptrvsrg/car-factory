package ru.nsu.ccfit.petrov.task4.factory.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;
import ru.nsu.ccfit.petrov.task4.threadpool.Task;

/**
 * The type {@code AssemblingTask} is class that implements {@link ru.nsu.ccfit.petrov.task4.threadpool.Task Task} for
 * {@link ru.nsu.ccfit.petrov.task4.threadpool.ThreadPool ThreadPool} and assembles car (takes engine, body and set
 * cover from storages and makes car from these details)
 *
 * @author ptrvsrg
 */
@Log4j2
@RequiredArgsConstructor
public class AssemblingTask
    implements Task {

    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<SeatCover> accessoryStorage;
    private final Storage<Car> carStorage;

    /**
     * Execute task.
     */
    @Override
    public void execute() {
        // Take car details
        Engine engine = engineStorage.takeProduct();
        Body body = bodyStorage.takeProduct();
        SeatCover seatCover = accessoryStorage.takeProduct();

        // If some detail is not taken, then return the taken details to the storage and finish the task
        if (engine == null || body == null || seatCover == null) {
            engineStorage.putProduct(engine);
            bodyStorage.putProduct(body);
            accessoryStorage.putProduct(seatCover);
            return;
        }

        Car car = new Car(engine, body, seatCover);

        carStorage.putProduct(car);
        log.info(String.format("%s assembly %s", Thread.currentThread(), car));
    }
}
