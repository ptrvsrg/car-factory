package ru.nsu.ccfit.petrov.task4.observer;

import java.util.HashSet;
import java.util.Set;
import ru.nsu.ccfit.petrov.task4.observer.context.Context;

/**
 * The type {@code Observable} is class that stores
 * {@link ru.nsu.ccfit.petrov.task4.observer.Observer listeners} and notifies them if its state
 * change.
 *
 * @author ptrvsrg
 */
public class Observable {

    private Set<Observer> observers;

    /**
     * Adds observer.
     *
     * @param observer the observer
     */
    public boolean addObserver(Observer observer) {
        if (observers == null) {
            observers = new HashSet<>();
        }

        return observers.add(observer);
    }

    /**
     * Removes observer.
     *
     * @param observer the observer
     */
    public boolean removeObserver(Observer observer) {
        if (observers == null) {
            return true;
        }

        return observers.remove(observer);
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
