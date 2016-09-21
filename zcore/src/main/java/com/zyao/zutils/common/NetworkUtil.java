package com.zyao.zutils.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class: NetworkUtil
 * Description: 网络相关工具类
 * Author: Zyao89
 * Time: 2016/7/27 18:00
 */
public class NetworkUtil
{
    private static NetworkUtil mInstance = null;
    private final Context mContext;

    private NetworkUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static NetworkUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (NetworkUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new NetworkUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 判断网络是否连接
     *
     * @return true or false
     */
    public boolean isConnected ()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null)
        {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if ((info != null) && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否WIFI连接
     *
     * @return true or false
     */
    public boolean isWifi ()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
    }


    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    public boolean isMobile ()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 打开网络设置界面
     *
     * @param activity 当前activity
     */
    public void openNetworkSetting (Activity activity)
    {
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        Intent intent = new Intent("/");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
