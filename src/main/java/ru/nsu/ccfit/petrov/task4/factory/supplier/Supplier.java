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

    private static final int MINUTE = 60000;
    private final UUID id = UUID.randomUUID();
    private final Storage<T> storage;
    private final Class<T> detailClass;
    @Setter private int productionTime = MINUTE;

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
            try {
                if (productionTime > 0) {
                    Thread.sleep(productionTime);
                }

                T product = detailClass.getDeclaredConstructor().newInstance();
                storage.putProduct(product);

                log.info(String.format("Supplier <%s> delivered %s", id, product));
            } catch (InterruptedException | IllegalAccessException | InvocationTargetException |
                     InstantiationException | NoSuchMethodException e) {
                log.warn(e);
                return;
            }
        }
    }
}
