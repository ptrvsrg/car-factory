package ru.nsu.ccfit.petrov.task4.factory.supplier;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.petrov.task4.factory.product.Detail;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

public class Supplier<T extends Detail>
    extends Thread {

    private static final Logger logger = LogManager.getLogger(Supplier.class);
    private final UUID id;
    private final Storage<T> storage;
    private final Class<T> detailClass;
    @Setter
    private int productionTime;

    public Supplier(Storage<T> storage, Class<T> detailClass, int productionTime) {
        this.id = UUID.randomUUID();
        this.productionTime = productionTime;
        this.detailClass = detailClass;
        this.storage = storage;
    }

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
                logger.info(
                    product.getName() + " supplier " + id + " create " + product.getName() + " "
                        + product.getId());
            } catch (InterruptedException | IllegalAccessException | InvocationTargetException |
                     InstantiationException | NoSuchMethodException e) {
                logger.warn(e);
                return;
            } catch (IllegalAccessException | InvocationTargetException |
                InstantiationException | NoSuchMethodException e) {
                logger.warn(e);
            }
        }
    }
}
