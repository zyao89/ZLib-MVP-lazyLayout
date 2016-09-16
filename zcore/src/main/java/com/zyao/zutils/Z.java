package com.zyao.zutils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.component.DaggerApplicationComponent;
import com.zyao.zcore2.di.module.ApplicationModule;
import com.zyao.zutils.app.ActivityManagerImpl;
import com.zyao.zutils.app.AppInfoManagerImpl;
import com.zyao.zutils.common.CommonUtilsManagerImpl;
import com.zyao.zutils.image.ImageManagerImpl;
import com.zyao.zutils.log.LoggerManagerImpl;
import com.zyao.zutils.task.TaskControllerImpl;

import java.lang.reflect.Method;

/**
 * Class: Z
 * Description: 任务控制中心, http, image, db, view注入等接口的入口.
 * 需要在在application的onCreate中初始化: x.Ext.initPresenter(this);
 * Author: Zyao89
 * Time: 2016/7/18 16:42
 */
public final class Z
{
    private Z ()
    {
    }

    public static boolean isDebug ()
    {
        return Ext.debug;
    }

    public static Application app ()
    {
        if (Ext.app == null)
        {
            try
            {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            }
            catch (Throwable ignored)
            {
                throw new RuntimeException("please invoke x.Ext.initPresenter(android.support.v4.app) on Application#onCreate()" + " and register your Application in manifest.");
            }
        }
        return Ext.app;
    }

    public static ApplicationComponent appComponent ()
    {
        if (Ext.appComponent == null)
        {
            Ext.appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(Z.app())).build();
        }
        return Ext.appComponent;
    }

    public static TaskController task ()
    {
        return Ext.taskController;
    }

    public static LoggerManager log ()
    {
        if (Ext.loggerManager == null)
        {
            LoggerManagerImpl.registerInstance();
        }
        return Ext.loggerManager;
    }

    public static ImageManager image ()
    {
        if (Ext.imageManager == null)
        {
            ImageManagerImpl.registerInstance();
        }
        return Ext.imageManager;
    }

    public static AppInfoManager appInfo ()
    {
        if (Ext.imageManager == null)
        {
            AppInfoManagerImpl.registerInstance();
        }
        return Ext.appInfoManager;
    }

    public static ActivityManager activityCtrl ()
    {
        if (Ext.activityManager == null)
        {
            ActivityManagerImpl.registerInstance();
        }
        return Ext.activityManager;
    }

    public static CommonUtilsManager utils ()
    {
        if (Ext.commonUtilsManager == null)
        {
            CommonUtilsManagerImpl.registerInstance(Z.app());
        }
        return Ext.commonUtilsManager;
    }

    public static class Ext
    {
        private static boolean debug;
        private static Application app;
        private static ApplicationComponent appComponent;
        private static TaskController taskController;
        private static ImageManager imageManager;
        private static AppInfoManager appInfoManager;
        private static LoggerManager loggerManager;
        private static ActivityManager activityManager;
        private static CommonUtilsManager commonUtilsManager;

        static
        {
            TaskControllerImpl.registerInstance();
        }

        private Ext ()
        {
        }

        public static void init (@NonNull Application application)
        {
            Ext.app = application;
        }

        public static void init (@NonNull Context context)
        {
            if (context.getApplicationContext() instanceof Application)
            {
                Ext.app = (Application) context.getApplicationContext();
            }
        }

        public static void init (@NonNull ApplicationComponent component)
        {
            Ext.appComponent = component;
            Ext.app = component.getApplication();
        }

        public static void setDebug (boolean debug)
        {
            Ext.debug = debug;
        }

        public static void setImageManager (@NonNull ImageManager imageManager)
        {
            Ext.imageManager = imageManager;
        }

        public static void setAppInfoManager (@NonNull AppInfoManager appInfoManager)
        {
            Ext.appInfoManager = appInfoManager;
        }

        public static void setTaskController (@NonNull TaskController taskController)
        {
            if (Ext.taskController == null)
            {
                Ext.taskController = taskController;
            }
        }

        public static void setLoggerManager (@NonNull LoggerManager loggerManager)
        {
            Ext.loggerManager = loggerManager;
        }

        public static void setActivityManager (@NonNull ActivityManager activityManager)
        {
            Ext.activityManager = activityManager;
        }

        public static void setCommonUtilsManager (@NonNull CommonUtilsManager commonUtilsManager)
        {
            Ext.commonUtilsManager = commonUtilsManager;
        }
    }

    private static class MockApplication extends Application
    {
        public MockApplication (Context baseContext)
        {
            this.attachBaseContext(baseContext);
        }
    }
}
