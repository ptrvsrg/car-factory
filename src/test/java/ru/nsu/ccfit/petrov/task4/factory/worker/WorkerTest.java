package ru.nsu.ccfit.petrov.task4.factory.worker;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WorkerTest
    extends Assertions {

    private Worker worker;

    @BeforeClass
    public void beforeClass() {
        worker = new Worker();
    }

    @Test(description = "Check converting to String",
          groups = "Worker tests")
    public void checkToString() {
        // do
        String workerStr = worker.toString();

        // check
        assertThat(workerStr).matches(
            "Worker <[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}>");
    }
}