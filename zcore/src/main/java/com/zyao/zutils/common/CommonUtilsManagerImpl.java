package com.zyao.zutils.common;

import android.content.Context;

import com.zyao.zutils.CommonUtilsManager;
import com.zyao.zutils.Z;

/**
 * Class: CommonUtilsManagerImpl
 * Description: 通用工具管理类实现
 * Author: Zyao89
 * Time: 2016/7/27 16:38
 */
public class CommonUtilsManagerImpl implements CommonUtilsManager
{
    private static volatile CommonUtilsManager instance;
    private final Context mContext;

    private CommonUtilsManagerImpl (Context context)
    {
        mContext = context;
    }

    public static void registerInstance (Context context)
    {
        if (instance == null)
        {
            synchronized (CommonUtilsManager.class)
            {
                if (instance == null)
                {
                    instance = new CommonUtilsManagerImpl(context);
                }
            }
        }
        Z.Ext.setCommonUtilsManager(instance);
    }

    @Override
    public DensityUtil density ()
    {
        return DensityUtil.with(mContext);
    }

    @Override
    public CrashUtil crash ()
    {
        return CrashUtil.with(mContext);
    }

    @Override
    public VibratorUtil vibrator ()
    {
        return VibratorUtil.with(mContext);
    }

    @Override
    public NetworkUtil net ()
    {
        return NetworkUtil.with(mContext);
    }

    @Override
    public ScreenLockUtil screenLock ()
    {
        return ScreenLockUtil.with(mContext);
    }

    @Override
    public StringEncryptUtil stringEncrypt (StringEncryptUtil.ENCRYPTION_SCHEME encryptionScheme, String encryptionKey)
    {
        return StringEncryptUtil.createStringEncryptUtil(encryptionScheme, encryptionKey);
    }

    @Override
    public StringUtil str ()
    {
        return StringUtil.with(mContext);
    }

    @Override
    public ByteArrayUtil byteArray ()
    {
        return ByteArrayUtil.with(mContext);
    }

    @Override
    public LocalFileUtil localFile ()
    {
        return LocalFileUtil.with(mContext);
    }

    @Override
    public SdCardUtils sdCard ()
    {
        return SdCardUtils.with(mContext);
    }
}
