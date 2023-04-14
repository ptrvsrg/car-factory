package ru.nsu.ccfit.petrov.task4.factory.product;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Product {

    private final UUID id = UUID.randomUUID();

    @Override
    public String toString() {
        return String.format("%s <%s>", name, id);
    }
}
