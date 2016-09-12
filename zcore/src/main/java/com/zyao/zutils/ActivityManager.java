package com.zyao.zutils;

import com.zyao.zcore.BaseActivity;

/**
 * Interface: ActivityManager
 * Description: activity收集工具，用于退出应用
 * Author: Zyao89
 * Time: 2016/7/19 20:03
 */
public interface ActivityManager
{
    void addActivity (BaseActivity activity);

    BaseActivity currentActivity ();

    void removeLastActivity ();

    void removeActivity (BaseActivity activity);

    void removeActivity (Class<? extends BaseActivity> clazz);

    BaseActivity getActivity (Class<? extends BaseActivity> clazz);

    boolean containsActivity (BaseActivity activity);

    void finishAllActivity ();

    void AppExit ();
}
