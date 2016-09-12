package com.zyao.zutils.task;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class: CustomThreadPoolExecutor
 * Description: 具有对比的线程池
 * Author: Zyao89
 * Time: 2016/7/28 17:00
 */
public class CustomThreadPoolExecutor implements Executor
{
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final Comparator<Runnable> FIFO_CMP = new Comparator<Runnable>()
    {
        @Override
        public int compare (Runnable lhs, Runnable rhs)
        {
            if (lhs instanceof CustomFutureTask && rhs instanceof CustomFutureTask)
            {
                CustomFutureTask l0 = (CustomFutureTask) lhs;
                CustomFutureTask l1 = (CustomFutureTask) rhs;
                return l1.compareTo(l0);
            }
            else if (lhs instanceof CustomRunnable && rhs instanceof CustomRunnable)
            {
                CustomRunnable l0 = (CustomRunnable) lhs;
                CustomRunnable l1 = (CustomRunnable) rhs;
                return l1.compareTo(l0);
            }
            return 0;
        }
    };

    private static final Comparator<Runnable> FILO_CMP = new Comparator<Runnable>()
    {
        @Override
        public int compare (Runnable lhs, Runnable rhs)
        {
            if (lhs instanceof CustomFutureTask && rhs instanceof CustomFutureTask)
            {
                CustomFutureTask l0 = (CustomFutureTask) lhs;
                CustomFutureTask l1 = (CustomFutureTask) rhs;
                return l0.compareTo(l1);
            }
            else if (lhs instanceof CustomRunnable && rhs instanceof CustomRunnable)
            {
                CustomRunnable l0 = (CustomRunnable) lhs;
                CustomRunnable l1 = (CustomRunnable) rhs;
                return l0.compareTo(l1);
            }
            return 0;
        }
    };

    private final ThreadPoolExecutor mThreadPoolExecutor;

    public CustomThreadPoolExecutor (int poolSize, boolean fifo)
    {
        BlockingQueue<Runnable> poolWorkQueue = new PriorityBlockingQueue<Runnable>(MAXIMUM_POOL_SIZE, fifo ? FIFO_CMP : FILO_CMP);
        mThreadPoolExecutor = new ThreadPoolExecutor(poolSize, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, poolWorkQueue, new DefaultThreadFactory("Priority"));
    }

    @Override
    public void execute (Runnable command)
    {
        mThreadPoolExecutor.execute(command);
    }

    public Future<?> submitTask (Runnable task)
    {
        return mThreadPoolExecutor.submit(task);
    }

    public void clear ()
    {
        mThreadPoolExecutor.purge();
    }

    public boolean isShutdown ()
    {
        return mThreadPoolExecutor.isShutdown();
    }

    public void shutdown ()
    {
        mThreadPoolExecutor.shutdown();
    }

}
