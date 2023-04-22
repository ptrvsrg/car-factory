package ru.nsu.ccfit.petrov.task4.factory.product;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CarTest
    extends Assertions {

    private Car car;

    @BeforeClass
    public void beforeClass() {
        car = new Car(new Engine(), new Body(), new SeatCover());
    }

    @DataProvider(name = "Create details when some detail is null")
    private Object[][] createDetailWhenSomeDetailIsNull() {
        return new Object[][]{
            new Object[]{
                new Engine(),
                new Body(),
                null
            },
            new Object[]{
                new Engine(),
                null,
                new SeatCover()
            },
            new Object[]{
                null,
                new Body(),
                new SeatCover()
            },
            };
    }

    @Test(description = "Check constructor when some detail is null",
          groups = "Car tests",
          dataProvider = "Create details when some detail is null")
    public void checkConstructorWhenSomeDetailIsNull(Engine engine, Body body, SeatCover seatCover) {
        // check
        assertThatThrownBy(() -> new Car(engine, body, seatCover)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test(description = "Check converting to String",
          groups = "Car tests")
    public void checkTestToString() {
        // prepare
        String uuidRegex = "[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}";
        String engineRegex = String.format("Engine <%s>", uuidRegex);
        String bodyRegex = String.format("Body <%s>", uuidRegex);
        String seatCoverRegex = String.format("SeatCover <%s>", uuidRegex);
        String carRegex = String.format("Car <%s> \\(%s, %s, %s\\)", uuidRegex, engineRegex, bodyRegex, seatCoverRegex);

        // do
        String carStr = car.toString();

        // check
        assertThat(carStr).matches(carRegex);
    }
}