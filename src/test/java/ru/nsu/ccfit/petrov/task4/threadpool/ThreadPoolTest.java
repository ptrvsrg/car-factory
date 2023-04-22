package ru.nsu.ccfit.petrov.task4.threadpool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ThreadPoolTest
    extends Assertions {

    private static final int THREAD_POOL_SIZE = 4;
    private ThreadPool threadPool;
    private Task task;

    @BeforeClass
    public void beforeClass() {
        task = Mockito.mock(Task.class);
    }

    @BeforeMethod
    public void beforeMethod() {
        threadPool = new ThreadPool();
    }

    private List<TaskThread> getThreadList()
        throws NoSuchFieldException, IllegalAccessException {
        Field threadsField = threadPool.getClass().getDeclaredField("threads");
        threadsField.setAccessible(true);
        List<TaskThread> threads = (List<TaskThread>) threadsField.get(threadPool);
        return threads;
    }

    private BlockingQueue<Task> getTaskQueue()
        throws NoSuchFieldException, IllegalAccessException {
        Field queueField = threadPool.getClass().getDeclaredField("queue");
        queueField.setAccessible(true);
        BlockingQueue<Task> queue = (BlockingQueue<Task>) queueField.get(threadPool);
        return queue;
    }

    @Test(description = "Check getting queue size",
          groups = "Thread pool tests")
    public void checkGetQueueSize() {
        // prepare
        int taskCount = 5;
        for (int i = 0; i < THREAD_POOL_SIZE; ++i) {
            threadPool.addThread(new TaskThread());
        }

        // do
        for (int i = 0; i < taskCount; ++i) {
            threadPool.addTask(() -> {});
        }

        // check
        int queueSize = threadPool.getQueueSize();
        assertThat(queueSize).isEqualTo(taskCount);
    }

    @Test(description = "Check adding task",
          groups = "Thread pool tests")
    public void checkAddTask()
        throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        // prepare
        int taskCount = 10;
        BlockingQueue<Task> expectedTasks = new LinkedBlockingQueue<>();

        // do
        for (int i = 0; i < taskCount; ++i) {
            Task task = () -> {};
            expectedTasks.add(task);
            threadPool.addTask(task);
        }

        // check
        BlockingQueue<Task> actualTasks = getTaskQueue();
        int actualQueueSize = actualTasks.size();

        assertThat(actualQueueSize).isEqualTo(taskCount);

        for (Task expectedTask : expectedTasks) {
            Task actualTask = actualTasks.take();
            assertThat(actualTask).isEqualTo(expectedTask);
        }
    }

    @Test(description = "Check adding thread",
          groups = "Thread pool tests")
    public void checkAddThread()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        int expectedThreadCount = 10;
        List<TaskThread> expectedThreads = new ArrayList<>();

        // do
        for (int i = 0; i < expectedThreadCount; ++i) {
            TaskThread taskThread = new TaskThread();
            expectedThreads.add(taskThread);
            threadPool.addThread(taskThread);
        }

        // check
        List<TaskThread> actualThreads = getThreadList();
        int actualThreadCount = actualThreads.size();

        assertThat(actualThreadCount).isEqualTo(expectedThreadCount);

        for (int i = 0; i < actualThreadCount; ++i) {
            TaskThread expectedThread = expectedThreads.get(i);
            TaskThread actualThread = actualThreads.get(i);

            assertThat(actualThread).isEqualTo(expectedThread);
        }
    }

    @Test(description = "Check starting thread pool",
          groups = "Thread pool tests")
    public void checkStart()
        throws InterruptedException {
        // prepare
        int taskCount = 10;
        for (int i = 0; i < THREAD_POOL_SIZE; ++i) {
            threadPool.addThread(new TaskThread());
        }
        for (int i = 0; i < taskCount; ++i) {
            threadPool.addTask(task);
        }

        // do
        threadPool.start();
        while (threadPool.getQueueSize() != 0) {}
        Thread.sleep(100);

        // check
        Mockito.verify(task, Mockito.times(taskCount)).execute();
    }

    @Test(description = "Check stopping thread pool",
          groups = "Thread pool tests")
    public void checkStop() {
        // prepare
        List<TaskThread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_POOL_SIZE; ++i) {
            TaskThread thread = new TaskThread();
            threads.add(thread);
            threadPool.addThread(thread);
        }

        // do
        threadPool.start();
        threadPool.stop();

        // check
        for (TaskThread thread : threads) {
            assertThat(thread.isAlive()).isTrue();
        }
    }
}