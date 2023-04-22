package ru.nsu.ccfit.petrov.task4.threadpool;

import java.util.concurrent.BlockingQueue;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TaskThreadTest
    extends Assertions {

    private TaskThread taskThread;
    private BlockingQueue<Task> queue;
    private Task task;

    @BeforeClass
    public void beforeClass() {
        queue = Mockito.mock(BlockingQueue.class);
        task = Mockito.mock(Task.class);
    }

    @BeforeMethod
    public void beforeMethod() {
        taskThread = new TaskThread();
    }

    @Test(description = "Check running when queue is not set",
          groups = "Task thread tests")
    public void checkRunWhenQueueIsNotSet()
        throws InterruptedException {
        // do
        taskThread.start();
        Thread.sleep(100);

        // check
        Mockito.verify(task, Mockito.never()).execute();
        Mockito.verify(queue, Mockito.never()).take();

        // restore
        taskThread.interrupt();
    }

    @Test(description = "Check running when queue is set",
          groups = "Task thread tests")
    public void checkRunWhenQueueIsSet()
        throws InterruptedException {
        // prepare
        taskThread.setQueue(queue);
        Mockito.when(queue.take()).thenReturn(task);

        // do
        taskThread.start();
        Thread.sleep(100);

        // check
        Mockito.verify(task, Mockito.atLeastOnce()).execute();
        Mockito.verify(queue, Mockito.atLeastOnce()).take();

        // restore
        taskThread.interrupt();
    }
}