package ru.nsu.ccfit.petrov.task4.observer.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StorageMovingContext
    implements Context {

    private final int currentProductCount;
    private final int capacity;
    private final int totalProductCount;
}
