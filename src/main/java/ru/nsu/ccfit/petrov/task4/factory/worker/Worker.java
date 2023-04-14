package ru.nsu.ccfit.petrov.task4.factory.worker;

import java.util.UUID;
import ru.nsu.ccfit.petrov.task4.threadpool.TaskThread;

public class Worker
    extends TaskThread {

    private final UUID id = UUID.randomUUID();
}
