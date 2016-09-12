package com.zyao.zutils;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Interface: TaskController
 * Description: 任务管理接口
 * Author: Zyao89
 * Time: 2016/7/19 10:19
 */
public interface TaskController
{
    /**
     * 在UI线程执行runnable.
     * 如果已在UI线程, 则直接执行.
     *
     * @param runnable
     */
    void autoPost (Runnable runnable);

    /**
     * 在UI线程执行runnable.
     * post到msg queue.
     *
     * @param runnable
     */
    void postUI (Runnable runnable);

    void postBG (Runnable runnable);

    /**
     * 在UI线程执行runnable.
     *
     * @param runnable
     * @param delayMillis 延迟时间(单位毫秒)
     */
    void postDelayedUI (Runnable runnable, long delayMillis);

    /**
     * 在后台线程执行runnable.
     *
     * @param runnable
     * @param delayMillis
     */
    void postDelayedBG (Runnable runnable, long delayMillis);

    /**
     * 在后台线程执行runnable
     *
     * @param runnable
     */
    Future<?> runFIFO (Runnable runnable);

    Future<?> runFILO (Runnable runnable);

    void run (Runnable runnable);

    void runSingle (Runnable runnable);

    ScheduledFuture<?> runScheduled (Runnable runnable, long delay);

    ScheduledFuture<?> runScheduled (Runnable runnable, long initialDelay, long delay, TimeUnit unit);

    ScheduledFuture<?> runSingleScheduled (Runnable runnable, long delay);

    ScheduledFuture<?> runSingleScheduled (Runnable runnable, long initialDelay, long delay, TimeUnit unit);

    /**
     * 移除post或postDelayed提交的, 未执行的runnable
     *
     * @param runnable
     */
    void removeCallbacks (Runnable runnable);
}
