package com.zyao.zutils.task;

import android.os.Looper;

import com.zyao.zutils.TaskController;
import com.zyao.zutils.Z;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Class: TaskControllerImpl
 * Description: 异步任务的管理类
 * Author: Zyao89
 * Time: 2016/7/19 10:27
 */
public class TaskControllerImpl implements TaskController
{
    private static final String TAG = "TaskControllerImpl";
    private static volatile TaskController instance;

    private TaskControllerImpl ()
    {
    }

    public static void registerInstance ()
    {
        if (instance == null)
        {
            synchronized (TaskController.class)
            {
                if (instance == null)
                {
                    instance = new TaskControllerImpl();
                }
            }
        }
        Z.Ext.setTaskController(instance);
    }

    @Override
    public void autoPost (Runnable runnable)
    {
        if (runnable == null)
        {
            return;
        }
        if (Thread.currentThread() == Looper.getMainLooper().getThread())
        {
            runnable.run();
        }
        else
        {
            TaskProxy.getInstance().getUIHandler().post(runnable);
        }
    }

    @Override
    public void postUI (Runnable runnable)
    {
        if (runnable == null)
        {
            return;
        }
        TaskProxy.getInstance().getUIHandler().post(runnable);
    }

    @Override
    public void postBG (Runnable runnable)
    {
        if (runnable == null)
        {
            return;
        }
        TaskProxy.getInstance().getBGHandler().post(runnable);
    }

    @Override
    public void postDelayedUI (Runnable runnable, long delayMillis)
    {
        if (runnable == null)
        {
            return;
        }
        TaskProxy.getInstance().getUIHandler().postDelayed(runnable, delayMillis);
    }

    @Override
    public void postDelayedBG (Runnable runnable, long delayMillis)
    {
        if (runnable == null)
        {
            return;
        }
        TaskProxy.getInstance().getBGHandler().postDelayed(runnable, delayMillis);
    }

    @Override
    public Future<?> runFIFO (Runnable runnable)
    {
        if (runnable == null)
        {
            return null;
        }
        return TaskProxy.getInstance().getFifoThreadPool().submitTask(runnable);
    }

    @Override
    public Future<?> runFILO (Runnable runnable)
    {
        if (runnable == null)
        {
            return null;
        }
        return TaskProxy.getInstance().getFiloThreadPool().submitTask(runnable);
    }

    @Override
    public void run (Runnable runnable)
    {
        if (runnable == null)
        {
            return;
        }
        TaskProxy.getInstance().getCachedThreadPool().execute(runnable);
    }

    @Override
    public void runSingle (Runnable runnable)
    {
        if (runnable == null)
        {
            return;
        }
        TaskProxy.getInstance().getSingleThreadExecutor().execute(runnable);
    }

    @Override
    public ScheduledFuture<?> runScheduled (Runnable runnable, long delay)
    {
        return runScheduled(runnable, 0, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture<?> runScheduled (Runnable runnable, long initialDelay, long delay, TimeUnit unit)
    {
        if (runnable == null)
        {
            return null;
        }
        return TaskProxy.getInstance().getScheduledThreadPool().scheduleAtFixedRate(runnable, initialDelay, delay, unit);
    }

    @Override
    public ScheduledFuture<?> runSingleScheduled (Runnable runnable, long delay)
    {
        return runSingleScheduled(runnable, 0, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture<?> runSingleScheduled (Runnable runnable, long initialDelay, long delay, TimeUnit unit)
    {
        if (runnable == null)
        {
            return null;
        }
        return TaskProxy.getInstance().getSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, initialDelay, delay, unit);
    }

    @Override
    public void removeCallbacks (Runnable runnable)
    {
        TaskProxy.getInstance().getUIHandler().removeCallbacks(runnable);
        TaskProxy.getInstance().getBGHandler().removeCallbacks(runnable);
    }

}
