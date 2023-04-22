package ru.nsu.ccfit.petrov.task4.observer;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;
import ru.nsu.ccfit.petrov.task4.observer.context.StorageMovingContext;

public class StorageMovingContextTest
    extends Assertions {
    private Observable observable;

    @BeforeClass
    public void beforeClass() {
        observable = new Observable();
    }

    @Test(description = "Check sending game over context",
          groups = "Context tests")
    void checkGameOverContext() {
        // prepare
        int currentProductCount = 5;
        int totalProductCount = 10;
        Observer observer = new Observer() {

            /**
             * Handles the context of the {@link Observable Observable} object message.
             *
             * @param context the context
             */
            @Override
            public void update(Context context) {
                // check
                assertThat(context.getClass()).isEqualTo(StorageMovingContext.class);

                int actualCurrentProductCount = ((StorageMovingContext) context).getCurrentProductCount();
                int actualTotalProductCount = ((StorageMovingContext) context).getTotalProductCount();
                assertThat(actualCurrentProductCount).isEqualTo(currentProductCount);
                assertThat(actualTotalProductCount).isEqualTo(totalProductCount);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new StorageMovingContext(currentProductCount, totalProductCount));

        // restore
        observable.removeObserver(observer);
    }
}