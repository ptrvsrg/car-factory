package ru.nsu.ccfit.petrov.task4.factory.worker;

import java.lang.reflect.Field;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.task4.threadpool.Task;
import ru.nsu.ccfit.petrov.task4.threadpool.TaskThread;
import ru.nsu.ccfit.petrov.task4.threadpool.ThreadPool;

/**
 * Business logic tested in {@link ru.nsu.ccfit.petrov.task4.threadpool.ThreadPoolTest ThreadPoolTest}
 */
public class WorkerDepartmentTest
    extends Assertions {

    private static final int WORKER_COUNT = 10;
    private WorkerDepartment workerDepartment;

    @BeforeMethod
    public void beforeMethod() {
        workerDepartment = new WorkerDepartment(WORKER_COUNT);
    }

    private ThreadPool getThreadPool(WorkerDepartment workerDepartment)
        throws NoSuchFieldException, IllegalAccessException {
        Field threadPoolField = workerDepartment.getClass().getDeclaredField("threadPool");
        threadPoolField.setAccessible(true);
        ThreadPool threadPool = (ThreadPool) threadPoolField.get(workerDepartment);
        return threadPool;
    }

    private void setThreadPool(WorkerDepartment workerDepartment, ThreadPool threadPool)
        throws NoSuchFieldException, IllegalAccessException {
        Field threadPoolField = workerDepartment.getClass().getDeclaredField("threadPool");
        threadPoolField.setAccessible(true);
        threadPoolField.set(workerDepartment, threadPool);
    }

    private List<TaskThread> getThreadList(ThreadPool threadPool)
        throws NoSuchFieldException, IllegalAccessException {
        Field threadsField = threadPool.getClass().getDeclaredField("threads");
        threadsField.setAccessible(true);
        List<TaskThread> threads = (List<TaskThread>) threadsField.get(threadPool);
        return threads;
    }

    @Test(description = "Check constructor",
          groups = "Worker department test")
    public void checkConstructor()
        throws NoSuchFieldException, IllegalAccessException {
        // check
        int actualWorkerCount = getThreadList(getThreadPool(workerDepartment)).size();
        assertThat(actualWorkerCount).isEqualTo(WORKER_COUNT);
    }

    @Test(description = "Check getting queue size",
          groups = "Worker department tests")
    public void checkGetQueueSize()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        ThreadPool threadPool = Mockito.mock(ThreadPool.class);
        WorkerDepartment workerDepartment = new WorkerDepartment(WORKER_COUNT);
        setThreadPool(workerDepartment, threadPool);

        // do
        workerDepartment.getQueueSize();

        // check
        Mockito.verify(threadPool, Mockito.times(1)).getQueueSize();
    }

    @Test(description = "Check adding tasks",
          groups = "Worker department tests")
    public void checkAddTask()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        ThreadPool threadPool = Mockito.mock(ThreadPool.class);
        WorkerDepartment workerDepartment = new WorkerDepartment(WORKER_COUNT);
        setThreadPool(workerDepartment, threadPool);

        // do
        workerDepartment.addTask(() -> {});

        // check
        Mockito.verify(threadPool, Mockito.times(1)).addTask(Mockito.any(Task.class));
    }

    @Test(description = "Check starting worker department",
          groups = "Worker department tests")
    public void checkStart()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        ThreadPool threadPool = Mockito.mock(ThreadPool.class);
        WorkerDepartment workerDepartment = new WorkerDepartment(WORKER_COUNT);
        setThreadPool(workerDepartment, threadPool);

        // do
        workerDepartment.start();

        // check
        Mockito.verify(threadPool, Mockito.times(1)).start();
    }

    @Test(description = "Check stopping worker department",
          groups = "Worker department tests")
    public void checkStop()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        ThreadPool threadPool = Mockito.mock(ThreadPool.class);
        WorkerDepartment workerDepartment = new WorkerDepartment(WORKER_COUNT);
        setThreadPool(workerDepartment, threadPool);

        // do
        workerDepartment.stop();

        // check
        Mockito.verify(threadPool, Mockito.times(1)).stop();
    }
}