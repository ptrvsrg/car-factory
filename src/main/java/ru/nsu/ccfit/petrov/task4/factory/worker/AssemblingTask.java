package ru.nsu.ccfit.petrov.task4.factory.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;
import ru.nsu.ccfit.petrov.task4.threadpool.Task;

@Log4j2
@RequiredArgsConstructor
public class AssemblingTask
    implements Task {

    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<SeatCover> seatCoverStorage;
    private final Storage<Car> carStorage;

    @Override
    public void execute() {
        Engine engine = engineStorage.takeProduct();
        Body body = bodyStorage.takeProduct();
        SeatCover seatCover = seatCoverStorage.takeProduct();

        Car car = new Car(engine, body, seatCover);

        carStorage.putProduct(car);
        log.info(String.format("%s assembly %s", Thread.currentThread(), car));
    }
}
