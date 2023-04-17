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

    private final UUID id = UUID.randomUUID();
    private final Storage<Car> carStorage;
    @Setter private Integer saleTime;

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
            if (saleTime == null) {
                log.warn("Sale time is not set");
                continue;
            }
            if (saleTime <= 0) {
                log.warn("Sale time is zero or negative");
                continue;
            }

            try {
                Thread.sleep(saleTime);
            } catch (InterruptedException e) {
                log.error(e);
                return;
            }

            Car car = carStorage.takeProduct();
            log.info(String.format("Dealer <%s> bought %s", id, car));
        }
    }
}
