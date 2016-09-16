package com.zyao;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;
import com.zyao.zcore2.di.component.ApplicationComponent;
import com.zyao.zcore2.di.component.DaggerApplicationComponent;
import com.zyao.zcore2.di.module.ApplicationModule;
import com.zyao.zutils.Z;
import com.zyao.zutils.log.LogLevel;

import butterknife.ButterKnife;


/**
 * 核心Application类
 */
public class CoreApplication extends Application
{
    private static final String LOG_PATH_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zyao89/log.txt";
    private static final String CRASH_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zyao89/crash";

    @Override
    protected void attachBaseContext (Context base)
    {
        super.attachBaseContext(base);

        //        Z.Ext.init(this);
        createComponent();

        Z.utils().crash().setSdCardPath(CRASH_PATH);
        Z.log().getSettings().logLevel(LogLevel.FULL);
        Z.log().openStreamPrint(LOG_PATH_FILE);
    }

    @Override
    public void onCreate ()
    {
        super.onCreate();
        Z.image().init(this);
        ButterKnife.setDebug(true);
        //初始化内存泄漏检测
        LeakCanary.install(this);
    }

    private void createComponent ()
    {
        ApplicationComponent component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        Z.Ext.init(component);
    }
}
