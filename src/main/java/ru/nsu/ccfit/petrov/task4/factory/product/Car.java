package ru.nsu.ccfit.petrov.task4.factory.product;

import lombok.Getter;

@Getter
public class Car
    extends Product {

    private final Engine engine;
    private final Body body;
    private final SeatCover seatCover;

    public Car(Engine engine, Body body, SeatCover seatCover) {
        super("Car");
        this.engine = engine;
        this.body = body;
        this.seatCover = seatCover;
    }
}
