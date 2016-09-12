package com.zyao.zutils.task;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Class: TaskProxy
 * Description: 线程代理类
 * Author: Zyao89
 * Time: 2016/7/28 15:46
 */
/*package*/ class TaskProxy
{
    private final static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /** 线程名称 */
    private static final String BG_LOOPER_THREAD_NAME = "[ZUtils]BG_LoopThread";
    private final static HandlerThread mBGThread = new HandlerThread(BG_LOOPER_THREAD_NAME);
    /** 单例 */
    private static TaskProxy mSingleton = null;
    private final Handler mUIHandler;
    private final Handler mBGHandler;
    private ExecutorService fixedThreadPool;//永久保存，没有超时机制
    private ExecutorService cachedThreadPool;//执行大量的耗时较少的任务
    private ExecutorService singleThreadExecutor;//单线程，同步任务
    private ScheduledExecutorService scheduledThreadPool;//定时任务和固定周期的重复任务
    private ScheduledExecutorService singleThreadScheduledExecutor;
    private CustomThreadPoolExecutor fifoThreadPool;
    private CustomThreadPoolExecutor filoThreadPool;

    private TaskProxy ()
    {
        fixedThreadPool = Executors.newFixedThreadPool(CPU_COUNT, new DefaultThreadFactory("fixed"));//永久保存，没有超时机制
        cachedThreadPool = Executors.newCachedThreadPool(new DefaultThreadFactory("cached"));//执行大量的耗时较少的任务
        singleThreadExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("singleThread"));//单线程，同步任务
        scheduledThreadPool = Executors.newScheduledThreadPool(CPU_COUNT, new DefaultThreadFactory("scheduled"));//定时任务和固定周期的重复任务
        singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("singleScheduled"));
        fifoThreadPool = new CustomThreadPoolExecutor(CPU_COUNT, true);
        filoThreadPool = new CustomThreadPoolExecutor(CPU_COUNT, false);
        mUIHandler = new Handler(Looper.getMainLooper());
        mBGThread.start();
        mBGHandler = new Handler(mBGThread.getLooper());
    }

    static TaskProxy getInstance ()
    {
        if (mSingleton == null)
        {
            mSingleton = new TaskProxy();
        }
        return mSingleton;
    }

    public static int getCpuCount ()
    {
        return CPU_COUNT;
    }

    public ExecutorService getFixedThreadPool ()
    {
        if (fixedThreadPool == null)
        {
            fixedThreadPool = Executors.newFixedThreadPool(CPU_COUNT);//永久保存，没有超时机制
        }
        return fixedThreadPool;
    }

    public ExecutorService getCachedThreadPool ()
    {
        if (cachedThreadPool == null)
        {
            cachedThreadPool = Executors.newFixedThreadPool(CPU_COUNT);//永久保存，没有超时机制
        }
        return cachedThreadPool;
    }

    public ExecutorService getSingleThreadExecutor ()
    {
        return singleThreadExecutor;
    }

    public ScheduledExecutorService getScheduledThreadPool ()
    {
        return scheduledThreadPool;
    }

    public ScheduledExecutorService getSingleThreadScheduledExecutor ()
    {
        return singleThreadScheduledExecutor;
    }

    public CustomThreadPoolExecutor getFifoThreadPool ()
    {
        return fifoThreadPool;
    }

    public CustomThreadPoolExecutor getFiloThreadPool ()
    {
        return filoThreadPool;
    }

    public Handler getUIHandler ()
    {
        return mUIHandler;
    }

    public Handler getBGHandler ()
    {
        return mBGHandler;
    }

    public void exit ()
    {
        if (mBGThread != null && mBGThread.isAlive())
        {
            mBGThread.quit();
        }
        if (fixedThreadPool != null && !fixedThreadPool.isShutdown())
        {
            fixedThreadPool.shutdown();
        }
        if (cachedThreadPool != null && !cachedThreadPool.isShutdown())
        {
            cachedThreadPool.shutdown();
        }
        if (singleThreadExecutor != null && !singleThreadExecutor.isShutdown())
        {
            singleThreadExecutor.shutdown();
        }
        if (scheduledThreadPool != null && !scheduledThreadPool.isShutdown())
        {
            scheduledThreadPool.shutdown();
        }
        if (singleThreadScheduledExecutor != null && !singleThreadScheduledExecutor.isShutdown())
        {
            singleThreadScheduledExecutor.shutdown();
        }
        if (fifoThreadPool != null && !fifoThreadPool.isShutdown())
        {
            fifoThreadPool.shutdown();
        }
        if (filoThreadPool != null && !filoThreadPool.isShutdown())
        {
            filoThreadPool.shutdown();
        }
    }
}
