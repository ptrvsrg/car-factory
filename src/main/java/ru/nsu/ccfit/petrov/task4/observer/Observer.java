package ru.nsu.ccfit.petrov.task4.observer;

import ru.nsu.ccfit.petrov.task4.observer.context.Context;

/**
 * The type {@code Observer} is interface describing the behavior of listeners for objects of type
 * {@link ru.nsu.ccfit.petrov.task4.observer.Observable Observable}.
 *
 * @author ptrvsrg
 */
public interface Observer {

    /**
     * Handles the context of the {@link ru.nsu.ccfit.petrov.task4.observer.Observable Observable}
     * object message.
     *
     * @param context the context
     */
    void update(Context context);
}
