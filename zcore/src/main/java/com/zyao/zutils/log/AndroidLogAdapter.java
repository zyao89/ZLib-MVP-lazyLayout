package com.zyao.zutils.log;

import android.util.Log;

/**
 * Class: AndroidLogAdapter
 * Description: 系统日志接口实现
 * Author: Zyao89
 * Time: 2016/7/21 13:44
 */
class AndroidLogAdapter implements LoggerAdapter
{
    @Override
    public void d (String tag, String message)
    {
        Log.d(tag, message);
    }

    @Override
    public void e (String tag, String message)
    {
        Log.e(tag, message);
    }

    @Override
    public void w (String tag, String message)
    {
        Log.w(tag, message);
    }

    @Override
    public void i (String tag, String message)
    {
        Log.i(tag, message);
    }

    @Override
    public void v (String tag, String message)
    {
        Log.v(tag, message);
    }

    @Override
    public void wtf (String tag, String message)
    {
        Log.wtf(tag, message);
    }
}
