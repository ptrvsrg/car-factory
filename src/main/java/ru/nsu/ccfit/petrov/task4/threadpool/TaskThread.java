package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.concurrent.BlockingQueue;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * The type {@code TaskThread} is class that describes thread for executing tasks from thread pool queue.
 *
 * @author ptrvsrg
 */
@Log4j2
public class TaskThread
    extends Thread {

    @Setter private BlockingQueue<Task> queue;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            if (queue == null) {
                log.warn("Queue is not set");
                continue;
            }

            Task task;
            try {
                task = queue.take();
            } catch (InterruptedException e) {
                return;
            }

            task.execute();
        }
    }
}
