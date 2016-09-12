package com.zyao.zutils.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zyao.zutils.AppInfoManager;
import com.zyao.zutils.Z;

import java.util.List;

/**
 * Class: AppInfoManagerImpl
 * Description: App信息管理类实现
 * Author: Zyao89
 * Time: 2016/7/18 20:27
 */
public class AppInfoManagerImpl implements AppInfoManager
{
    private static volatile AppInfoManager instance;

    private AppInfoManagerImpl ()
    {
    }

    public static void registerInstance ()
    {
        if (instance == null)
        {
            synchronized (AppInfoManager.class)
            {
                if (instance == null)
                {
                    instance = new AppInfoManagerImpl();
                }
            }
        }
        Z.Ext.setAppInfoManager(instance);
    }

    @Override
    public String getAppName ()
    {
        ApplicationInfo applicationInfo = null;
        PackageManager packageManager = null;
        try
        {
            packageManager = Z.app().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(Z.app().getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    @Override
    public int getCurVersionCode ()
    {
        try
        {
            PackageInfo packageInfo = Z.app().getPackageManager().getPackageInfo(Z.app().getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getAppVersion ()
    {
        try
        {
            PackageInfo packageInfo = Z.app().getPackageManager().getPackageInfo(Z.app().getPackageName(), 0);
            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean isTopActivity ()
    {
        ActivityManager activityManager = (ActivityManager) Z.app().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0)
        {
            String name = tasksInfo.get(0).topActivity.getPackageName().trim();

            if (Z.app().getPackageName().trim().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getAppLocaleLanguage ()
    {
        return Z.app().getResources().getConfiguration().locale.toString();
    }

    @Override
    public String getAppPackageName ()
    {
        return Z.app().getPackageName();
    }
}
