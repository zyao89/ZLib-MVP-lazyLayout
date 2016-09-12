package com.zyao.zutils.log;

/**
 * Class: LoggerSettings
 * Description: 日志设置
 * Author: Zyao89
 * Time: 2016/7/21 13:40
 */
public class LoggerSettings
{
    private int methodCount = 2;
    private boolean showThreadInfo = true;
    private int methodOffset = 0;
    private LoggerAdapter logAdapter;

    /**
     * Determines to how logs will be printed
     */
    private LogLevel logLevel = LogLevel.NONE;

    public LoggerSettings hideThreadInfo ()
    {
        showThreadInfo = false;
        return this;
    }

    public LoggerSettings methodCount (int methodCount)
    {
        if (methodCount < 0)
        {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public LoggerSettings logLevel (LogLevel logLevel)
    {
        this.logLevel = logLevel;
        return this;
    }

    public LoggerSettings methodOffset (int offset)
    {
        this.methodOffset = offset;
        return this;
    }

    public LoggerSettings logAdapter (LoggerAdapter logAdapter)
    {
        this.logAdapter = logAdapter;
        return this;
    }

    public int getMethodCount ()
    {
        return methodCount;
    }

    public boolean isShowThreadInfo ()
    {
        return showThreadInfo;
    }

    public LogLevel getLogLevel ()
    {
        return logLevel;
    }

    public int getMethodOffset ()
    {
        return methodOffset;
    }

    public LoggerAdapter getLogAdapter ()
    {
        if (logAdapter == null)
        {
            logAdapter = new AndroidLogAdapter();
        }
        return logAdapter;
    }

    public void reset ()
    {
        methodCount = 2;
        methodOffset = 0;
        showThreadInfo = true;
        logLevel = LogLevel.FULL;
    }
}
