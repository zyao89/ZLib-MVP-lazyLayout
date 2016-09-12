package com.zyao.zutils.task;

import java.util.concurrent.FutureTask;

/**
 * Class: CustomFutureTask
 * Description: FutureTask
 * Author: Zyao89
 * Time: 2016/7/29 9:42
 */
public class CustomFutureTask extends FutureTask<Object> implements Comparable<CustomFutureTask>
{
    private static long counter = 0;
    private final long priority;

    public CustomFutureTask (Runnable runnable)
    {
        super(runnable, new Object());
        priority = counter++;
    }

    public long getPriority ()
    {
        return priority;
    }

    @Override
    public int compareTo (CustomFutureTask another)
    {
        return priority > another.getPriority() ? -1 : 1;
    }
}
