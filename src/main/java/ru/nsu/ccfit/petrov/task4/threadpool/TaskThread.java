package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskThread
    extends Thread {

    private static final Logger logger = LogManager.getLogger();
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
                logger.warn(e);
            }
        }
    }
}
