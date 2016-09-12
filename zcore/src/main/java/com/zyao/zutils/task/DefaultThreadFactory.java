package com.zyao.zutils.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class: DefaultThreadFactory
 * Description: The default thread factory
 * Author: Zyao89
 * Time: 2016/7/29 13:24
 */
/*package*/ final class DefaultThreadFactory implements ThreadFactory
{
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    /*package*/ DefaultThreadFactory (String tag)
    {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = tag + "-pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    public Thread newThread (Runnable r)
    {
        Thread t = new Thread(group, r, "[ZUtils]_TID # " + namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
        {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY)
        {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
