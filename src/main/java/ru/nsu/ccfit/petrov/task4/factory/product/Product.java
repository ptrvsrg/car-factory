package ru.nsu.ccfit.petrov.task4.factory.product;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Product {

    private final UUID id = UUID.randomUUID();
    private final String name;

    public Product(String name) {
        this.name = name;
    }
}
