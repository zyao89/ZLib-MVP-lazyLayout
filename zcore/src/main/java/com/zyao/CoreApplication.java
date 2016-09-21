package com.zyao;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;
import com.zyao.config.Configs;
import com.zyao.zcore2.di.component.DaggerApplicationComponent;
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
        createComponent();//改进使用dagger2

        Z.Ext.setDebug(Configs.DEBUG);

        Z.utils().crash().setSdCardPath(CRASH_PATH);

        Z.log().getSettings().logLevel(Z.isDebug() ? LogLevel.FULL : LogLevel.NONE);

        Z.log().openStreamPrint(Z.isDebug() ? LOG_PATH_FILE : null);
    }

    @Override
    public void onCreate ()
    {
        super.onCreate();
        //初始化图片加载工具
        Z.image().init(this);
        //ButterKnife
        ButterKnife.setDebug(false);
        //初始化内存泄漏检测
        LeakCanary.install(this);
    }

    private void createComponent ()
    {
        ApplicationComponent component = DaggerApplicationComponent.builder().applicationModule(getApplicationModule()).helperModule(getHelperModule()).build();
        Z.Ext.init(component);
    }

    private ApplicationModule getApplicationModule ()
    {
        return new ApplicationModule(this);
    }

    private HelperModule getHelperModule ()
    {
        return new HelperModule(this);
    }
}
