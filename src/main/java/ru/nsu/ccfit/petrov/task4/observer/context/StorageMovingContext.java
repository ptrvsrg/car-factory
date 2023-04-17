package ru.nsu.ccfit.petrov.task4.observer.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The type {@code StorageMovingContext} is  class that describes the state of the changed storage, contains the
 * parameters that were changed when products were added and removed from the storage.
 *
 * @author ptrvsrg
 */
@Getter
@RequiredArgsConstructor
public class StorageMovingContext
    implements Context {

    private final int currentProductCount;
    private final int totalProductCount;
}
