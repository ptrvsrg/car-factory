package ru.nsu.ccfit.petrov.task4.factory.supplier;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Detail;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

/**
 * The type {@code Supplier} is class that describes supplier thread that makes details and puts them into storage.
 *
 * @param <T> the type parameter
 * @author ptrvsrg
 */
@Log4j2
@RequiredArgsConstructor
public class Supplier<T extends Detail>
    extends Thread {

    private final UUID id = UUID.randomUUID();
    private final Storage<T> storage;
    private final Class<T> detailClass;
    @Setter private Integer productionTime;

    /**
     * If this thread was constructed using a separate {@code Runnable} run object, then that {@code Runnable} object's
     * {@code run} method is called; otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of {@code Thread} should override this method.
     */
    @Override
    public void run() {
        while (isAlive()) {
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
                return;
            }

            storage.putProduct(product);
            log.info(String.format("%s delivered %s", this, product));
        }
    }

    /**
     * Returns a string representation of the object. In general, the {@code toString} method returns a string that
     * "textually represents" this object. The result should be a concise but informative representation that is easy
     * for a person to read. It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object} returns a string consisting of the name of the class of
     * which the object is an instance, the at-sign character `{@code @}', and the unsigned hexadecimal representation
     * of the hash code of the object. In other words, this method returns a string equal to the value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("Supplier <%s>", id);
    }
}
