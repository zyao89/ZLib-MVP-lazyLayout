package com.zyao.zutils.app;

import android.content.Context;

import com.zyao.zcore.BaseActivity;
import com.zyao.zutils.ActivityManager;
import com.zyao.zutils.Z;

import java.util.Stack;

/**
 * Class: ActivityManagerImpl
 * Description: Activity管理类
 * Author: Zyao89
 * Time: 2016/7/19 20:07
 */
public class ActivityManagerImpl implements ActivityManager
{
    private static final String TAG = "ActivityManagerImpl";

    private static volatile ActivityManager instance;
    private Stack<BaseActivity> mActivityStack;

    private ActivityManagerImpl ()
    {
        if (mActivityStack == null)
        {
            mActivityStack = new Stack<>();
        }
    }

    public static void registerInstance ()
    {
        if (instance == null)
        {
            synchronized (ActivityManager.class)
            {
                if (instance == null)
                {
                    instance = new ActivityManagerImpl();
                }
            }
        }
        Z.Ext.setActivityManager(instance);
    }

    @Override
    public synchronized void addActivity (BaseActivity activity)
    {
        if (activity == null)
        {
            return;
        }
        mActivityStack.add(activity);
    }

    @Override
    public synchronized BaseActivity currentActivity ()
    {
        return mActivityStack.lastElement();
    }

    @Override
    public synchronized void removeLastActivity ()
    {
        removeActivity(mActivityStack.lastElement());
    }

    @Override
    public synchronized void removeActivity (BaseActivity activity)
    {
        if (activity == null)
        {
            return;
        }
        mActivityStack.remove(activity);
        activity.finish();
    }

    @Override
    public synchronized void removeActivity (Class<? extends BaseActivity> clazz)
    {
        if (null == clazz)
        {
            return;
        }
        for (BaseActivity activity : mActivityStack)
        {
            if (activity.getClass().equals(clazz))
            {
                removeActivity(activity);
            }
        }
    }

    @Override
    public synchronized BaseActivity getActivity (Class<? extends BaseActivity> clazz)
    {
        if (null == clazz)
        {
            return null;
        }
        for (BaseActivity activity : mActivityStack)
        {
            if (activity.getClass().equals(clazz))
            {
                return activity;
            }
        }
        return null;
    }

    @Override
    public synchronized boolean containsActivity (BaseActivity activity)
    {
        if (null == activity)
        {
            return false;
        }
        return mActivityStack.contains(activity);
    }

    @Override
    public synchronized void finishAllActivity ()
    {
        for (int i = 0, size = mActivityStack.size(); i < size; i++)
        {
            if (null != mActivityStack.get(i))
            {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    @Override
    public synchronized void AppExit ()
    {
        try
        {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) Z.app().getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(Z.app().getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Z.log().d("AppExit ==>> 退出程序！！");
        }
    }
}
