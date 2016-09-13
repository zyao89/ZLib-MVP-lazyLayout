package com.zyao.zutils.log;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class: LogReader
 * Description: 日志打印
 * Author: Zyao89
 * Time: 2016/7/19 15:47
 */
public class LogReader extends Thread
{
    public static final String TAG = "LogReader";
    private static final SimpleDateFormat mFileFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
    public static boolean open = true;
    private static LogReader instance = null;
    private static Process mLogcatProc = null;
    private BufferedReader mReader = null;
    private String packageName = "*";
    private String fileRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZUtils/log.txt";

    public static void startCatchLog (String packageName, String fileRootPath)
    {
        if (!open)
        {
            return;
        }
        if (instance == null)
        {
            instance = new LogReader();
            instance.packageName = packageName;
            instance.fileRootPath = fileRootPath;
            instance.start();
        }
    }

    public static void stopCatchLog ()
    {
        if (!open)
        {
            return;
        }
        if (mLogcatProc != null)
        {
            mLogcatProc.destroy();
            mLogcatProc = null;
        }
    }

    @Override
    public void run ()
    {
        Log.i(TAG, "log reader(catcher) is running..---------------------------");
        BufferedWriter bw = null;
        try
        {
            mLogcatProc = Runtime.getRuntime().exec("logcat " + packageName + ":I");
            mReader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));

            // 打印系统信息。
            logSystemInfo();

            String line;
            File file = new File(fileRootPath);
            if (file.exists() && file.isDirectory())
            {
                file.delete();
            }
            if (!file.exists() && file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }
            if (file.exists() && isFileSizeOutof10M(file))
            {
                file.delete();
            }
            if (file.exists())
            {
                System.out.println("log file size is :" + FormatFileSize(file.length()));
            }
            else
            {
                file.createNewFile(); //需要权限
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            while ((line = mReader.readLine()) != null)
            {
                bw.append(line);
                bw.newLine();
                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Log.i(TAG, "Log reader(catcher) and bufferwriter has closed. ------------------");
            try
            {
                if (mReader != null)
                {
                    mReader.close();
                    mReader = null;
                }
                if (bw != null)
                {
                    bw.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            instance = null;
        }

    }

    private String FormatFileSize (long fileS)
    {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 判断文件是否大于10M。
     *
     * @param file
     *
     * @return
     *
     * @throws Exception
     */
    private boolean isFileSizeOutof10M (File file) throws Exception
    {
        if (file == null)
        {
            return false;
        }
        return file.length() >= 10485760;
    }

    private void logSystemInfo ()
    {
        Date date = new Date(System.currentTimeMillis());
        String time = mFileFormat.format(date);
        Log.w("system", "New Start $$$$$$$$$$$$$$###########   " + time + "############$$$$$$$$$$$$$$$");
        Log.w("system", "android.os.Build.BOARD:" + android.os.Build.BOARD);
        Log.w("system", "android.os.Build.DEVICE:" + android.os.Build.DEVICE);
        Log.w("system", "android.os.Build.MANUFACTURER:" + android.os.Build.MANUFACTURER);
        Log.w("system", "android.os.Build.MODEL:" + android.os.Build.MODEL);
        Log.w("system", "android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
        Log.w("system", "android.os.Build.VERSION.CODENAME:" + android.os.Build.VERSION.CODENAME);
        Log.w("system", "android.os.Build.VERSION.RELEASE:" + android.os.Build.VERSION.RELEASE);
        Log.w("system", "android.os.Build.VERSION.SDK:" + android.os.Build.VERSION.SDK);
    }

}
