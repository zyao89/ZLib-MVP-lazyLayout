package com.zyao.zutils.common;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.zyao.zutils.Z;

/**
 * Class: ScreenLockUtil
 * Description: 屏幕锁控制
 * Author: Zyao89
 * Time: 2016/7/27 18:08
 */
public class ScreenLockUtil
{
    private static final String TAG = "ScreenLockUtil";
    private static ScreenLockUtil mInstance = null;
    private final Context mContext;
    private PowerManager.WakeLock mWakeLock = null;
    private boolean mIsUnlock = false;
    private KeyguardManager.KeyguardLock mKeyguardLock = null;

    private ScreenLockUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static ScreenLockUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (ScreenLockUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ScreenLockUtil(context);
                }
            }
        }
        return mInstance;
    }

    public void keepScreenOn ()
    {
        if (mWakeLock == null)
        {
            PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            mWakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, this.getClass().getName());
        }

        if (!mWakeLock.isHeld())
        {
            mWakeLock.acquire();
        }

        cancelLockScreen();

        Z.log().i(TAG, "开启屏幕常亮");
    }

    public void cancelKeepScreenOn ()
    {
        if (mWakeLock != null)
        {
            if (mWakeLock.isHeld())
            {
                mWakeLock.release();
            }
        }

        reenableLockScreen();

        Z.log().i(TAG, "取消屏幕常亮");
    }

    private void cancelLockScreen ()
    {
        if (mIsUnlock)
        {
            return;
        }
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        mKeyguardLock = keyguardManager.newKeyguardLock(this.getClass().getName());
        mKeyguardLock.disableKeyguard();
        mIsUnlock = true;
    }

    private void reenableLockScreen ()
    {
        if (!mIsUnlock || mKeyguardLock == null)
        {
            return;
        }
        mKeyguardLock.reenableKeyguard();
        mIsUnlock = false;
    }
}
