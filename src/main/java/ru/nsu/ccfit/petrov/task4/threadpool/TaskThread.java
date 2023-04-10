package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.concurrent.BlockingQueue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TaskThread
    extends Thread {

    private final BlockingQueue<Task> queue;

    public TaskThread(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
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
