package ru.nsu.ccfit.petrov.task4.factory.supplier;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.factory.product.Detail;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

public class SupplierTest
    extends Assertions {

    private static final int WAIT_DELAY = 100;
    private static final int CAPACITY = 10;
    private Storage<Detail> storage;
    private Supplier<Detail> supplier;

    @BeforeMethod
    public void beforeMethod() {
        storage = new Storage<>(CAPACITY);
        supplier = new Supplier<>(storage, Detail.class);
    }

    @Test(description = "Check setting null production time",
          groups = "Supplier tests")
    public void checkSetNullProductionTime()
        throws InterruptedException {
        // prepare
        int previousProductCount = storage.getCurrentProductCount();

        // do
        supplier.setProductionTime(null);
        supplier.start();
        Thread.sleep(WAIT_DELAY);

        // check
        int currentProductCount = storage.getCurrentProductCount();
        boolean isSupplierAlive = supplier.isAlive();
        assertThat(currentProductCount).isEqualTo(previousProductCount);
        assertThat(isSupplierAlive).isTrue();

        // restore
        supplier.interrupt();
    }

    @Test(description = "Check setting zero production time",
          groups = "Supplier tests")
    public void checkSetZeroProductionTime()
        throws InterruptedException {
        // prepare
        int productionTime = 0;
        int previousProductCount = storage.getCurrentProductCount();

        // do
        supplier.setProductionTime(productionTime);
        supplier.start();
        Thread.sleep(WAIT_DELAY);

        // check
        int currentProductCount = storage.getCurrentProductCount();
        boolean isSupplierAlive = supplier.isAlive();
        assertThat(currentProductCount).isEqualTo(previousProductCount);
        assertThat(isSupplierAlive).isTrue();

        // restore
        supplier.interrupt();
    }

    @Test(description = "Check setting negative production time",
          groups = "Supplier tests")
    public void checkSetNegativeProductionTime()
        throws InterruptedException {
        // prepare
        int productionTime = -10;
        int previousProductCount = storage.getCurrentProductCount();

        // do
        supplier.setProductionTime(productionTime);
        supplier.start();
        Thread.sleep(WAIT_DELAY);

        // check
        int currentProductCount = storage.getCurrentProductCount();
        boolean isSupplierAlive = supplier.isAlive();
        assertThat(currentProductCount).isEqualTo(previousProductCount);
        assertThat(isSupplierAlive).isTrue();

        // restore
        supplier.interrupt();
    }

    @Test(description = "Check supplying details",
          groups = "Supplier tests")
    public void checkSupplyDetails()
        throws InterruptedException {
        // prepare
        int productionTime = 200;
        int previousProductCount = storage.getCurrentProductCount();

        // do
        supplier.setProductionTime(productionTime);
        supplier.start();
        Thread.sleep(productionTime + WAIT_DELAY);

        // check
        int currentProductCount = storage.getCurrentProductCount();
        boolean isSupplierAlive = supplier.isAlive();
        assertThat(currentProductCount).isGreaterThan(previousProductCount);
        assertThat(isSupplierAlive).isTrue();

        // restore
        supplier.interrupt();
    }

    @Test(description = "Check converting to String",
          groups = "Supplier tests")
    public void checkToString() {
        // do
        String supplierStr = supplier.toString();

        // check
        assertThat(supplierStr).matches(
            "Supplier <[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}>");
    }
}