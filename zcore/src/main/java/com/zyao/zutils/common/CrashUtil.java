package com.zyao.zutils.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;

import com.zyao.zutils.Z;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class: CrashUtil
 * Description: 系统异常捕获工具
 * Author: Zyao89
 * Time: 2016/7/27 17:25
 */
public class CrashUtil implements Thread.UncaughtExceptionHandler
{
    private static String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZUtils/crash";
    /** 类单例 */
    private static CrashUtil mInstance = null;
    /** 存储设备信息和异常信息 */
    private final Map<String, String> infos = new HashMap<>();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    /** 格式化日期，作为日志文件名的一部分 */
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
    private Context mContext;

    private CrashUtil (Context context)
    {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context;
    }

    /* package */
    static CrashUtil with (Context context)
    {
        if (null == mInstance)
        {
            synchronized (CrashUtil.class)
            {
                if (null == mInstance)
                {
                    mInstance = new CrashUtil(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void uncaughtException (Thread thread, Throwable ex)
    {
        handleException(ex);

        if (mDefaultCrashHandler != null)
        {
            // 让系统默认的处理器处理
            SystemClock.sleep(500);
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }
    }

    public void setSdCardPath (String path)
    {
        SD_CARD_PATH = path;
    }

    /** 自定义错误处理，收集错误信息等 */
    private boolean handleException (Throwable ex)
    {
        // 处理异常，可以提示用户崩溃了，或保存重要信息，尝试恢复现场，或者直接杀死进程
        if (ex == null)
        {
            return false;
        }

        // 收集设备信息
        collectDeviceInfo();
        // 保存日志文件
        saveCrashInfoToFile(ex);
        return true;
    }

    /** 收集设备信息 */
    private void collectDeviceInfo ()
    {
        try
        {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null)
            {
                // 应用版本信息
                infos.put("App Version", pi.versionName + '_' + pi.versionCode + "\n");
                // Android 系统版本信息
                infos.put("OS Version", Build.VERSION.RELEASE + '_' + Build.VERSION.SDK_INT + "\n");
                // 设备ID
                infos.put("Device ID", Build.ID + "\n");
                // 设备序列号
                infos.put("Device Serial", Build.SERIAL + "\n");
                // 手机制造商
                infos.put("Manufacturer", Build.MANUFACTURER + "\n");
                // 手机型号
                infos.put("Model", Build.MODEL + "\n");
                // CPU架构
                infos.put("CPU ABI", Build.CPU_ABI + "\n");
                // 手机品牌
                infos.put("Brand", Build.BRAND + "\n");
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Z.log().e("an error occurred when collect package info", e);
        }
    }

    /** 保存错误信息到文件中 */
    private String saveCrashInfoToFile (Throwable ex)
    {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key);
            sb.append(" : ");
            sb.append(value);
            sb.append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null)
        {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append("\n");
        sb.append(result);

        try
        {
            long currentTime = System.currentTimeMillis();
            String time = formatter.format(new Date(currentTime));
            String fileName = "crash_" + time + "_" + currentTime + ".log";

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                File dir = new File(SD_CARD_PATH);
                if (!dir.exists())
                {
                    boolean s = dir.mkdirs();
                    Z.log().d(s);
                }

                FileOutputStream fileOutputStream = new FileOutputStream(SD_CARD_PATH + "/" + fileName);
                fileOutputStream.write(sb.toString().getBytes());
                fileOutputStream.close();
            }

            return fileName;
        }
        catch (Exception e)
        {
            Z.log().e("an error occurred while writing file...", e);
        }
        return "";
    }
}
