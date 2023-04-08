package ru.nsu.ccfit.petrov.task4.factory.product;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Product {

    private final UUID id = UUID.randomUUID();
}
