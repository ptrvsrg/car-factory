package ru.nsu.ccfit.petrov.task4.factory.worker;

import ru.nsu.ccfit.petrov.task4.threadpool.Task;
import ru.nsu.ccfit.petrov.task4.threadpool.ThreadPool;

/**
 * The type {@code WorkerDepartment} is class that contains
 * {@link ru.nsu.ccfit.petrov.task4.threadpool.ThreadPool thread pool} for
 * {@link ru.nsu.ccfit.petrov.task4.factory.worker.Worker workers}.
 *
 * @author ptrvsrg
 */
public class WorkerDepartment {

    private final ThreadPool threadPool = new ThreadPool();

    /**
     * Constructs a WorkerDepartment.
     *
     * @param workerCount the worker count
     */
    public WorkerDepartment(int workerCount) {
        for (int i = 0; i < workerCount; ++i) {
            threadPool.addThread(new Worker());
        }
    }

    /**
     * Gets queue size.
     *
     * @return the queue size
     */
    public int getQueueSize() {
        return threadPool.getQueueSize();
    }

    /**
     * Add task.
     *
     * @param task the task
     * @return {@code true} - successful addition, {@code false} - unsuccessful addition
     */
    public boolean addTask(Task task) {
        return threadPool.addTask(task);
    }

    /**
     * Start worker department.
     */
    public void start() {
        threadPool.start();
    }

    /**
     * Stop worker department.
     */
    public void stop() {
        threadPool.stop();
    }
}
