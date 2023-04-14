package ru.nsu.ccfit.petrov.task4.factory.worker;

import ru.nsu.ccfit.petrov.task4.threadpool.Task;
import ru.nsu.ccfit.petrov.task4.threadpool.ThreadPool;

public class WorkerDepartment {

    private final ThreadPool threadPool = new ThreadPool();

    public WorkerDepartment(int workerCount) {
        for (int i = 0; i < workerCount; ++i) {
            threadPool.addThread(new Worker());
        }
    }

    public void start() {
        threadPool.start();
    }

    public void stop() {
        threadPool.stop();
    }

    public boolean addTask(Task task) {
        return threadPool.addTask(task);
    }
}
