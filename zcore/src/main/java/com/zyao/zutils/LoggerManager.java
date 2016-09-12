package com.zyao.zutils;

import com.zyao.zutils.log.LoggerSettings;

/**
 * Interface: LoggerManager
 * Description: Logger是一个日志的接口
 * Author: Zyao89
 * Time: 2016/7/19 15:30
 */
public interface LoggerManager
{
    int DEBUG = 3;
    int ERROR = 6;
    int ASSERT = 7;
    int INFO = 4;
    int VERBOSE = 2;
    int WARN = 5;

    LoggerManager t (String tag);

    LoggerManager t (int methodCount);

    LoggerManager t (String tag, int methodCount);

    LoggerSettings init (String tag);

    LoggerSettings getSettings ();

    void v (String tag, String message);

    void v (String message, Object... args);

    void d (String tag, String message);

    void d (String message, Object... args);

    void d (Object object);

    void i (String tag, String message);

    void i (String message, Object... args);

    void w (String tag, String message);

    void w (String message, Object... args);

    void wtf (String message, Object... args);

    void e (String tag, String message);

    void e (String tag, String message, Throwable ex);

    void e (String message, Object... args);

    void e (Throwable throwable, String message, Object... args);

    void openStreamPrint (String filePath);

    void closeStreamPrint ();

    void println (int priority, String tag, String message);

    void json (String json);

    void xml (String xml);

    void log (int priority, String tag, String message, Throwable throwable);

    void resetSettings ();

}
