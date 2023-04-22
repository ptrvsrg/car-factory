package ru.nsu.ccfit.petrov.task4.observer;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;

public class ObserverTest
    extends Assertions {

    private Observable observable;
    private Observer observer;

    @BeforeMethod
    public void beforeMethod() {
        observable = new Observable();
        observer = Mockito.mock(Observer.class);
    }

    @Test(description = "Check notify with observer",
          groups = "Observer tests")
    void checkNotifyWithObserver() {
        // do
        boolean status = observable.addObserver(observer);
        observable.notifyObservers(new Context() {});

        // check
        assertThat(status).isTrue();
        Mockito.verify(observer, Mockito.times(1)).update(Mockito.any(Context.class));
    }

    @Test(description = "Check notify without observer",
          groups = "Observer tests")
    void checkNotifyWithoutObserver() {
        // do
        observable.notifyObservers(new Context() {});

        // check
        Mockito.verify(observer, Mockito.times(0)).update(Mockito.any(Context.class));
    }

    @Test(description = "Check remove observer without observer",
          groups = "Observer tests")
    void checkRemoveObserverWithoutObserver() {
        // do
        boolean status = observable.removeObserver(observer);

        // check
        assertThat(status).isTrue();
    }

    @Test(description = "Check remove observer with observer",
          groups = "Observer tests")
    void checkRemoveObserverWithObserver() {
        // do
        observable.addObserver(observer);
        boolean status = observable.removeObserver(observer);

        // check
        assertThat(status).isTrue();
    }
}