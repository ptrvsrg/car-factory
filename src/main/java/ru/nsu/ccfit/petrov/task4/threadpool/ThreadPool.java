package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    private final List<TaskThread> threads;
    private final BlockingQueue<Task> queue;

    public ThreadPool(int threadCount) {
        threads = new ArrayList<>();
        queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < threadCount; i++) {
            threads.add(new TaskThread(queue));
        }
    }

    public boolean addTask(Task task) {
        return queue.offer(task);
    }

    public void start() {
        for (TaskThread thread : threads) {
            thread.start();
        }
    }
}
