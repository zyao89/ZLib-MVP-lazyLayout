package com.zyao.zutils.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.zyao.zutils.Z;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Zyao89 on 2016/10/16.
 */

public class CustomSnackbar
{
    private final String TAG = this.getClass().getSimpleName();
    private static CustomSnackbar mInstance = null;
    private final Context mContext;
    private static Snackbar mSnackbar;

    private CustomSnackbar (Context context)
    {
        mContext = context;
    }

    /* package */
    static CustomSnackbar with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (CustomSnackbar.class)
            {
                if (mInstance == null)
                {
                    mInstance = new CustomSnackbar(context);
                }
            }
        }
        return mInstance;
    }

    public void show (@NonNull final View view, @StringRes final int resId, @CustomToast.Duration final int duration)
    {
        Z.task().autoPost(new Runnable()
        {
            @Override
            public void run ()
            {
                if (mSnackbar == null)
                {
                    mSnackbar = makeText(view, resId, duration);
                }
                else
                {
                    mSnackbar.setText(resId);
                    mSnackbar.setDuration(duration);
                }
                mSnackbar.show();
            }
        });
    }

    public void show (@NonNull final View view, final String text, @CustomToast.Duration final int duration)
    {
        Z.task().autoPost(new Runnable()
        {
            @Override
            public void run ()
            {
                if (mSnackbar == null)
                {
                    mSnackbar = makeText(view, text, duration);
                }
                else
                {
                    mSnackbar.setText(text);
                    mSnackbar.setDuration(duration);
                }
                //                Z.log().i(TAG, "text: " + text);
                mSnackbar.show();
            }
        });
    }

    /**
     * @hide
     */
    @IntDef({Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    @SuppressLint("ShowToast")
    private Snackbar makeText (@NonNull View view, @StringRes int resId, @Duration int duration)
    {
        mSnackbar = Snackbar.make(view, resId, duration);
        return mSnackbar;
    }

    @SuppressLint("ShowToast")
    private Snackbar makeText (@NonNull View view, CharSequence text, @Duration int duration)
    {
        mSnackbar = Snackbar.make(view, text, duration);
        return mSnackbar;
    }
}
