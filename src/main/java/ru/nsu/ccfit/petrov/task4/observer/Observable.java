package ru.nsu.ccfit.petrov.task4.observer;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;

/**
 * The type {@code Observable} is class that stores
 * {@link ru.nsu.ccfit.petrov.task4.observer.Observer listeners} and notifies them if its state
 * change.
 *
 * @author ptrvsrg
 */
public class Observable {

    private List<Observer> observers;

    /**
     * Adds observer.
     *
     * @param observer the observer
     */
    public void addObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }

        observers.add(observer);
    }

    /**
     * Removes observer.
     *
     * @param observer the observer
     */
    public void removeObserver(Observer observer) {
        if (observers == null) {
            return;
        }

        observers.remove(observer);
    }

    /**
     * Removes all observers.
     */
    public void removeObservers() {
        if (observers == null) {
            return;
        }

        observers.clear();
    }

    /**
     * Notifies observers.
     *
     * @param context the context
     */
    public void notifyObservers(Context context) {
        if (observers == null) {
            return;
        }

        for (Observer observer : observers) {
            observer.update(context);
        }
    }
}
