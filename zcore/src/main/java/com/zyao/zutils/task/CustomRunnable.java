package com.zyao.zutils.task;

/**
 * Class: CustomRunnable
 * Description: CustomRunnable
 * Author: Zyao89
 * Time: 2016/7/29 11:23
 */
public class CustomRunnable implements Runnable, Comparable<CustomRunnable>
{
    private static long counter = 0;
    private final Runnable runnable;
    private final long priority;

    public CustomRunnable (Runnable runnable)
    {
        priority = counter++;
        this.runnable = runnable;
    }

    @Override
    public final void run ()
    {
        this.runnable.run();
    }

    public long getPriority ()
    {
        return priority;
    }

    @Override
    public int compareTo (CustomRunnable another)
    {
        return priority > another.getPriority() ? -1 : 1;
    }
}
