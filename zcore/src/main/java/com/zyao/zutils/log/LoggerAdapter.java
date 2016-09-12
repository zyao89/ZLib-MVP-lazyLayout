package com.zyao.zutils.log;

/**
 * Interface: LoggerAdapter
 * Description: 系统日志接口
 * Author: Zyao89
 * Time: 2016/7/21 13:43
 */
public interface LoggerAdapter
{
    void d (String tag, String message);

    void e (String tag, String message);

    void w (String tag, String message);

    void i (String tag, String message);

    void v (String tag, String message);

    void wtf (String tag, String message);
}
