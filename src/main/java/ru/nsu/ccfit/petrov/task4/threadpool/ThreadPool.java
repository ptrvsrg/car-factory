package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type {@code Thread pool} is class that implements architecture pattern "Thread pool". It contains thread list and
 * task queue. Tasks are taken from the queue and executed by one of the threads.
 *
 * @author ptrvsrg
 */
public class ThreadPool {

    private final List<TaskThread> threads = new ArrayList<>();
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    /**
     * Gets queue size.
     *
     * @return the queue size
     */
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * Adds task.
     *
     * @param task the task
     * @return {@code true} - successful addition, {@code false} - unsuccessful addition
     */
    public boolean addTask(Task task) {
        return queue.offer(task);
    }

    /**
     * Adds thread.
     *
     * @param taskThread the task thread
     * @return {@code true} - successful addition, {@code false} - unsuccessful addition
     */
    public boolean addThread(TaskThread taskThread) {
        taskThread.setQueue(queue);
        return threads.add(taskThread);
    }

    /**
     * Start all threads.
     */
    public void start() {
        for (TaskThread thread : threads) {
            thread.start();
        }
    }

    /**
     * Stop all threads.
     */
    public void stop() {
        for (TaskThread thread : threads) {
            thread.interrupt();
        }
    }
}
