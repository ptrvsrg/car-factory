package ru.nsu.ccfit.petrov.task4.factory.dealer;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

public class DealerTest
    extends Assertions {

    private static final int WAIT_DELAY = 100;
    private static final int CAPACITY = 10;
    private Storage<Car> carStorage;
    private Dealer dealer;

    @BeforeMethod
    public void beforeMethod() {
        carStorage = new Storage<>(CAPACITY);
        dealer = new Dealer(carStorage);
    }

    @Test(description = "Check setting null sale time",
          groups = "Dealer tests")
    public void checkSetNullSaleTime()
        throws InterruptedException {
        // prepare
        int previousProductCount = carStorage.getCurrentProductCount();

        // do
        dealer.setSaleTime(null);
        dealer.start();
        Thread.sleep(WAIT_DELAY);

        // check
        int currentProductCount = carStorage.getCurrentProductCount();
        boolean isDealerAlive = dealer.isAlive();
        assertThat(currentProductCount).isEqualTo(previousProductCount);
        assertThat(isDealerAlive).isTrue();

        // restore
        dealer.interrupt();
    }

    @Test(description = "Check setting zero sale time",
          groups = "Dealer tests")
    public void checkSetZeroSaleTime()
        throws InterruptedException {
        // prepare
        int saleTime = 0;
        int previousProductCount = carStorage.getCurrentProductCount();

        // do
        dealer.setSaleTime(saleTime);
        dealer.start();
        Thread.sleep(WAIT_DELAY);

        // check
        int currentProductCount = carStorage.getCurrentProductCount();
        boolean isDealerAlive = dealer.isAlive();
        assertThat(currentProductCount).isEqualTo(previousProductCount);
        assertThat(isDealerAlive).isTrue();

        // restore
        dealer.interrupt();
    }

    @Test(description = "Check setting negative sale time",
          groups = "Dealer tests")
    public void checkSetNegativeSaleTime()
        throws InterruptedException {
        // prepare
        int saleTime = -10;
        int previousProductCount = carStorage.getCurrentProductCount();

        // do
        dealer.setSaleTime(saleTime);
        dealer.start();
        Thread.sleep(WAIT_DELAY);

        // check
        int currentProductCount = carStorage.getCurrentProductCount();
        boolean isDealerAlive = dealer.isAlive();
        assertThat(currentProductCount).isEqualTo(previousProductCount);
        assertThat(isDealerAlive).isTrue();

        // restore
        dealer.interrupt();
    }

    @Test(description = "Check buying car",
          groups = "Dealer tests",
          timeOut = 1000)
    public void checkBuyCar()
        throws InterruptedException {
        // prepare
        int saleTime = 200;
        carStorage.putProduct(new Car(new Engine(), new Body(), new SeatCover()));
        int previousProductCount = carStorage.getCurrentProductCount();

        // do
        dealer.setSaleTime(saleTime);
        dealer.start();
        Thread.sleep(saleTime + WAIT_DELAY);

        // check
        int currentProductCount = carStorage.getCurrentProductCount();
        boolean isDealerAlive = dealer.isAlive();
        assertThat(currentProductCount).isLessThan(previousProductCount);
        assertThat(isDealerAlive).isTrue();

        // restore
        dealer.interrupt();
    }

    @Test(description = "Check converting to String",
          groups = "Dealer tests")
    public void checkToString() {
        // do
        String dealerStr = dealer.toString();

        // check
        assertThat(dealerStr).matches(
            "Dealer <[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}>");
    }
}