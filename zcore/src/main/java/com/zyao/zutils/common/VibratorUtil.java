package com.zyao.zutils.common;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Class: VibratorUtil
 * Description: 手机震动工具类
 * Author: Zyao89
 * Time: 2016/7/27 17:52
 */
public class VibratorUtil
{
    private static VibratorUtil mInstance = null;
    private final Context mContext;

    private VibratorUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static VibratorUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (VibratorUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new VibratorUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 震动一定时间
     *
     * @param milliseconds 震动的时长，单位是毫秒
     */
    public void Vibrate (long milliseconds)
    {
        Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 自定义震动
     *
     * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public void Vibrate (long[] pattern, boolean isRepeat)
    {
        Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
