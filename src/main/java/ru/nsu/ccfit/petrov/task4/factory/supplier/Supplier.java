package ru.nsu.ccfit.petrov.task4.factory.supplier;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Detail;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

@Log4j2
@RequiredArgsConstructor
public class Supplier<T extends Detail>
    extends Thread {

    private final UUID id = UUID.randomUUID();
    private final Storage<T> storage;
    private final Class<T> detailClass;
    @Setter private Integer productionTime;

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
            if (productionTime == null) {
                log.warn("Production time is not set");
                continue;
            }
            if (productionTime <= 0) {
                log.warn("Production time is zero or negative");
                continue;
            }

            T product;
            try {
                Thread.sleep(productionTime);
                product = detailClass.getDeclaredConstructor().newInstance();
            } catch (InterruptedException | IllegalAccessException | InvocationTargetException |
                     InstantiationException | NoSuchMethodException e) {
                log.error(e);
                return;
            }

            storage.putProduct(product);
            log.info(String.format("Supplier <%s> delivered %s", id, product));
        }
    }
}
