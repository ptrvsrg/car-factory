package ru.nsu.ccfit.petrov.task4.factory.storage;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.factory.product.Product;

public class StorageTest
    extends Assertions {

    private static final int CAPACITY = 10;
    private Storage<Product> storage;

    private class PutThread
        extends Thread {

        /**
         * If this thread was constructed using a separate {@code Runnable} run object, then that {@code Runnable}
         * object's {@code run} method is called; otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of {@code Thread} should override this method.
         *
         * @see #start()
         * @see #stop()
         */
        @Override
        public void run() {
            storage.putProduct(new Product());
        }
    }

    private class TakeThread
        extends Thread {

        @Getter private Product product = null;

        /**
         * If this thread was constructed using a separate {@code Runnable} run object, then that {@code Runnable}
         * object's {@code run} method is called; otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of {@code Thread} should override this method.
         *
         * @see #start()
         * @see #stop()
         */
        @Override
        public void run() {
            product = storage.takeProduct();
        }
    }

    @BeforeClass
    public void beforeClass() {
        storage = new Storage<>(CAPACITY);
    }

    @Test(description = "Check getting capacity",
          groups = "Storage tests")
    public void checkGetCapacity() {
        // do
        int actualCapacity = storage.getCapacity();

        // check
        assertThat(actualCapacity).isEqualTo(CAPACITY);
    }

    @Test(description = "Check putting product in storage",
          groups = "Storage tests",
          timeOut = 10000)
    public void checkPutProduct()
        throws InterruptedException {
        // prepare
        List<PutThread> putThreads = new ArrayList<>();
        for (int i = 0; i < CAPACITY; ++i) {
            putThreads.add(new PutThread());
        }

        // do
        for (PutThread putThread : putThreads) {
            putThread.start();
            putThread.join();
        }

        // check
        int currentProductCount = storage.getCurrentProductCount();
        assertThat(currentProductCount).isEqualTo(CAPACITY);
    }

    @Test(description = "Check putting product in storage when storage is full",
          groups = "Storage tests",
          dependsOnMethods = "checkPutProduct")
    public void checkPutProductWhenStorageIsFull() {
        // prepare
        PutThread waitThread = new PutThread();

        // do
        waitThread.start();

        // check
        int currentProductCount = storage.getCurrentProductCount();
        boolean isWaitThreadAlive = waitThread.isAlive();
        assertThat(currentProductCount).isEqualTo(CAPACITY);
        assertThat(isWaitThreadAlive).isTrue();

        // restore
        waitThread.interrupt();
    }

    @Test(description = "Check taking product from storage",
          groups = "Storage tests",
          timeOut = 10000,
          dependsOnMethods = "checkPutProductWhenStorageIsFull")
    public void checkTakeProduct()
        throws InterruptedException {
        // prepare
        List<TakeThread> takeThreads = new ArrayList<>();
        for (int i = 0; i < CAPACITY; ++i) {
            takeThreads.add(new TakeThread());
        }

        // do
        for (TakeThread takeThread : takeThreads) {
            takeThread.start();
            takeThread.join();
        }

        // check
        int currentProductCount = storage.getCurrentProductCount();
        assertThat(currentProductCount).isZero();

        for (TakeThread takeThread : takeThreads) {
            Product takenProduct = takeThread.getProduct();
            assertThat(takenProduct).isNotNull();
        }
    }

    @Test(description = "Check taking product from storage when storage is empty",
          groups = "Storage tests",
          dependsOnMethods = "checkTakeProduct")
    public void checkTakeProductWhenStorageIsEmpty() {
        // prepare
        TakeThread waitThread = new TakeThread();

        // do
        waitThread.start();

        // check
        int currentProductCount = storage.getCurrentProductCount();
        boolean isWaitThreadAlive = waitThread.isAlive();
        assertThat(currentProductCount).isZero();
        assertThat(isWaitThreadAlive).isTrue();

        // restore
        waitThread.interrupt();
    }

    @Test(description = "Check take product when storage is empty and thread is interrupted",
          groups = "Storage tests",
          dependsOnMethods = "checkTakeProductWhenStorageIsEmpty")
    public void checkTakeProductWhenStorageIsEmptyAndThreadIsInterrupted() {
        // prepare
        TakeThread takeThread = new TakeThread();

        // do
        takeThread.start();
        takeThread.interrupt();

        // check
        Product product = takeThread.getProduct();
        assertThat(product).isNull();
    }
}