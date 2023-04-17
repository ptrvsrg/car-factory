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
        while (true) {
            if (queue == null) {
                log.warn("Queue is not set");
                continue;
            }

            Task task;
            try {
                task = queue.take();
            } catch (InterruptedException e) {
                log.error(e);
                return;
            }

            task.execute();
        }
    }
}
