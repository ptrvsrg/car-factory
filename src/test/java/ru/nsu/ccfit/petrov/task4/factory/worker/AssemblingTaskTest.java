package ru.nsu.ccfit.petrov.task4.factory.worker;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.factory.product.Body;
import ru.nsu.ccfit.petrov.task4.factory.product.Car;
import ru.nsu.ccfit.petrov.task4.factory.product.Engine;
import ru.nsu.ccfit.petrov.task4.factory.product.SeatCover;
import ru.nsu.ccfit.petrov.task4.factory.storage.Storage;

public class AssemblingTaskTest
    extends Assertions {

    private Storage<Engine> engineStorage;
    private Storage<Body> bodyStorage;
    private Storage<SeatCover> accessoryStorage;
    private Storage<Car> carStorage;
    private AssemblingTask assemblingTask;

    @BeforeClass
    public void beforeClass() {
        engineStorage = Mockito.mock(Storage.class);
        bodyStorage = Mockito.mock(Storage.class);
        accessoryStorage = Mockito.mock(Storage.class);
        carStorage = Mockito.mock(Storage.class);
        assemblingTask = new AssemblingTask(engineStorage, bodyStorage, accessoryStorage, carStorage);
    }

    @DataProvider(name = "Create details")
    private Object[][] createDetails() {
        return new Object[][]{
            new Object[]{
                null,
                new Body(),
                new SeatCover()
            },
            new Object[]{
                new Engine(),
                null,
                new SeatCover()
            },
            new Object[]{
                new Engine(),
                new Body(),
                null
            }
        };
    }

    @Test(description = "Check execution task when detail is null",
          groups = "Worker task tests",
          dataProvider = "Create details")
    public void checkExecuteWhenDetailIsNull(Engine engine, Body body, SeatCover seatCover) {
        // prepare
        Mockito.when(engineStorage.takeProduct()).thenReturn(engine);
        Mockito.when(bodyStorage.takeProduct()).thenReturn(body);
        Mockito.when(accessoryStorage.takeProduct()).thenReturn(seatCover);

        // do
        assemblingTask.execute();

        // check
        Mockito.verify(engineStorage, Mockito.times(1)).putProduct(engine);
        Mockito.verify(bodyStorage, Mockito.times(1)).putProduct(body);
        Mockito.verify(accessoryStorage, Mockito.times(1)).putProduct(seatCover);
    }

    @Test(description = "Check execution task",
          groups = "Worker task tests")
    public void checkExecute() {
        // prepare
        Mockito.when(engineStorage.takeProduct()).thenReturn(new Engine());
        Mockito.when(bodyStorage.takeProduct()).thenReturn(new Body());
        Mockito.when(accessoryStorage.takeProduct()).thenReturn(new SeatCover());
        Mockito.doAnswer(invocationOnMock -> null).when(carStorage).putProduct(Mockito.any(Car.class));

        // do
        assemblingTask.execute();

        // check
        Mockito.verify(engineStorage, Mockito.times(1)).takeProduct();
        Mockito.verify(bodyStorage, Mockito.times(1)).takeProduct();
        Mockito.verify(accessoryStorage, Mockito.times(1)).takeProduct();
        Mockito.verify(carStorage, Mockito.times(1)).putProduct(Mockito.any(Car.class));
    }
}