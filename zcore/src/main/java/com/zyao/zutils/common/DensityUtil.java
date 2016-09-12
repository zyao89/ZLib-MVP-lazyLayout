package com.zyao.zutils.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Class: DensityUtil
 * Description: 屏幕宽高适配工具类
 * Author: Zyao89
 * Time: 2016/7/18 16:43
 */
public class DensityUtil
{
    private static float density = -1F;
    private static int widthPixels = -1;
    private static int heightPixels = -1;
    private static DensityUtil mInstance = null;
    private final Context mContext;

    private DensityUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static DensityUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (DensityUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new DensityUtil(context);
                }
            }
        }
        return mInstance;
    }

    public float getDensity ()
    {
        if (density <= 0F)
        {
            density = mContext.getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public int dip2px (float dpValue)
    {
        return (int) (dpValue * getDensity() + 0.5F);
    }

    public int px2dip (float pxValue)
    {
        return (int) (pxValue / getDensity() + 0.5F);
    }

    public int getScreenWidth ()
    {
        if (widthPixels <= 0)
        {
            widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }

    public int getScreenHeight ()
    {
        if (heightPixels <= 0)
        {
            heightPixels = mContext.getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }

    public int getScreenHeightWithoutTitlebar ()
    {
        int[] screenWidthAndHeight = getScreenWidthAndHeight();
        return screenWidthAndHeight[1] - getStatusBarHeight() - dip2px(48);
    }

    public int[] getScreenWidthAndHeight ()
    {
        WindowManager mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return new int[]{screenWidth, screenHeight};
    }

    public int getStatusBarHeight ()
    {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try
        {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = mContext.getResources().getDimensionPixelSize(x);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        return sbar;
    }
}
