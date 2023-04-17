package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.concurrent.BlockingQueue;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TaskThread
    extends Thread {

    @Setter private BlockingQueue<Task> queue;

    @Override
    public void run() {
        if (queue == null) {
            log.error("Queue is not set");
            return;
        }

        while (true) {
            try {
                Task task = queue.take();
                task.execute();
            } catch (InterruptedException e) {
                log.warn(e);
            }
        }
    }
}
