package com.zyao.zutils.toast;

import android.content.Context;

import com.zyao.zutils.CommonUtilsManager;
import com.zyao.zutils.ToastUtils;
import com.zyao.zutils.Z;
import com.zyao.zutils.common.ByteArrayUtil;
import com.zyao.zutils.common.CrashUtil;
import com.zyao.zutils.common.DensityUtil;
import com.zyao.zutils.common.LocalFileUtil;
import com.zyao.zutils.common.NetworkUtil;
import com.zyao.zutils.common.ScreenLockUtil;
import com.zyao.zutils.common.SdCardUtils;
import com.zyao.zutils.common.StringEncryptUtil;
import com.zyao.zutils.common.StringUtil;
import com.zyao.zutils.common.VibratorUtil;

/**
 * Class: ToastUtilsImpl
 * Description: 通用土司类实现
 * Author: Zyao89
 * Time: 2016/7/27 16:38
 */
public class ToastUtilsImpl implements ToastUtils
{
    private static volatile ToastUtils instance;
    private final Context mContext;

    private ToastUtilsImpl (Context context)
    {
        mContext = context;
    }

    public static void registerInstance (Context context)
    {
        if (instance == null)
        {
            synchronized (ToastUtils.class)
            {
                if (instance == null)
                {
                    instance = new ToastUtilsImpl(context);
                }
            }
        }
        Z.Ext.setToastUtils(instance);
    }

    @Override
    public CustomToast toast ()
    {
        return CustomToast.with(mContext);
    }

    @Override
    public CustomSnackbar snackbar ()
    {
        return CustomSnackbar.with(mContext);
    }
}
