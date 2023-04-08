package ru.nsu.ccfit.petrov.task4.factory.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Car
    extends Product {

    private final Engine engine;
    private final Body body;
    private final SeatCover seatCover;
}
