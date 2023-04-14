package ru.nsu.ccfit.petrov.task4.factory.dealer;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

@Log4j2
@RequiredArgsConstructor
public class Dealer
    extends Thread {

    private static final int MINUTE = 60000;
    private final UUID id = UUID.randomUUID();
    private final Storage<Car> carStorage;
    @Setter private int saleTime = MINUTE;

    /**
     * If this thread was constructed using a separate {@code Runnable} run object, then that
     * {@code Runnable} object's {@code run} method is called; otherwise, this method does nothing
     * and returns.
     * <p>
     * Subclasses of {@code Thread} should override this method.
     */
    @Override
    public void run() {
        while (true) {
            if (saleTime > 0) {
                try {
                    Thread.sleep(saleTime);

                } catch (InterruptedException e) {
                    log.warn(e);
                    return;
                }
            }

            Car car = carStorage.takeProduct();
            log.info("Dealer " + id + " bought car" + car.getId());
        }
    }
}
