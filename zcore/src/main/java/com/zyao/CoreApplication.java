package com.zyao;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.zyao.zutils.Z;
import com.zyao.zutils.log.LogLevel;


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
        Z.Ext.init(this);
        Z.utils().crash().setSdCardPath(CRASH_PATH);
        Z.log().getSettings().logLevel(LogLevel.FULL);
        Z.log().openStreamPrint(LOG_PATH_FILE);
    }

    @Override
    public void onCreate ()
    {
        super.onCreate();
        Z.image().init(this);
    }
}
