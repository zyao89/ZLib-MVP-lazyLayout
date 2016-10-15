package com.zyao.zutils.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntDef;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.zyao.zutils.Z;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * Class: DensityUtil
 * Description: 屏幕宽高适配工具类
 * Author: Zyao89
 * Time: 2016/7/18 16:43
 */
public class CustomToast
{
    private final String TAG = this.getClass().getSimpleName();
    private static CustomToast mInstance = null;
    private final Context mContext;
    private static Toast mToast;

    private CustomToast (Context context)
    {
        mContext = context;
    }

    /* package */
    static CustomToast with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (CustomToast.class)
            {
                if (mInstance == null)
                {
                    mInstance = new CustomToast(context);
                }
            }
        }
        return mInstance;
    }

    /** @hide */
    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration
    {
    }

    public void showShort (final int resId)
    {
        show(resId, Toast.LENGTH_SHORT);
    }

    public void showLong (final int resId)
    {
        show(resId, Toast.LENGTH_LONG);
    }

    public void showShort (final String text)
    {
        show(text, Toast.LENGTH_SHORT);
    }

    public void showLong (final String text)
    {
        show(text, Toast.LENGTH_LONG);
    }

    public void show (final int resId, @Duration final int duration)
    {
        Z.task().autoPost(new Runnable()
        {
            @Override
            public void run ()
            {
                if (mToast == null)
                {
                    mToast = makeText(resId, duration);
                }
                else
                {
                    mToast.setText(resId);
                    mToast.setDuration(duration);
                }
                //                Z.log().i(TAG, "text: " + Z.app().getResources().getString(resId));
                mToast.show();
            }
        });
    }

    public void show (final String text, @Duration final int duration)
    {
        Z.task().autoPost(new Runnable()
        {
            @Override
            public void run ()
            {
                if (mToast == null)
                {
                    mToast = makeText(text, duration);
                }
                else
                {
                    mToast.setText(text);
                    mToast.setDuration(duration);
                }
                //                Z.log().i(TAG, "text: " + text);
                mToast.show();
            }
        });
    }

    @SuppressLint("ShowToast")
    private Toast makeText (int resId, @Duration int duration)
    {
        mToast = Toast.makeText(mContext, resId, duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    @SuppressLint("ShowToast")
    private Toast makeText (CharSequence text, @Duration int duration)
    {
        mToast = Toast.makeText(mContext, text, duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }
}
