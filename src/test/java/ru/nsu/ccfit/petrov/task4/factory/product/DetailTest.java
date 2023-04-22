package ru.nsu.ccfit.petrov.task4.factory.product;

import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DetailTest
    extends Assertions {

    @DataProvider(name = "Create detail and regex")
    private Object[][] createDetailAndRegex() {
        return new Object[][]{
            new Object[]{
                new Engine(), "Engine <[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}>"
            },
            new Object[]{
                new Body(), "Body <[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}>"
            },
            new Object[]{
                new SeatCover(), "SeatCover <[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}>"
            }
        };
    }

    @Test(description = "Check converting to String",
          groups = "Detail tests",
          dataProvider = "Create detail and regex")
    public void checkToString(Detail detail, String regex) {
        // do
        String detailStr = detail.toString();

        // check
        assertThat(detailStr).matches(regex);
    }
}