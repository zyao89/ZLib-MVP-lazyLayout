package com.zyao.zutils.common;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * Class: SdCardUtils
 * Description: SdCard工具类
 * Author: Zyao89
 * Time: 2016/8/17 15:12
 */
public class SdCardUtils
{
    private static volatile SdCardUtils mInstance;
    private final Context mContext;

    private SdCardUtils (Context context)
    {
        mContext = context;
    }

    /* package */
    static SdCardUtils with (Context context)
    {
        if (null == mInstance)
        {
            synchronized (SdCardUtils.class)
            {
                if (null == mInstance)
                {
                    mInstance = new SdCardUtils(context);
                }
            }
        }

        return mInstance;
    }

    public String getSdCardStatus ()
    {
        return Environment.getExternalStorageState();
    }

    public boolean isCanUseSdCard ()
    {
        try
        {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public long getFreeSpace ()
    {
        if (!isCanUseSdCard())
        {
            return 0;
        }

        StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getPath());

        long blockSize = statfs.getBlockSize();
        //long totalBlocks = statfs.getBlockCount();
        long availableBlocks = statfs.getAvailableBlocks();

		/*Log.e(TAG, "****************************************************************************");
        Log.e(TAG, "blockSize:" + blockSize);
        Log.e(TAG, "totalBlocks:" + totalBlocks);
        Log.e(TAG, "availableBlocks:" + availableBlocks);
        Log.e(TAG, "totalBlocks - availableBlocks:" + (totalBlocks - availableBlocks));
        Log.e(TAG, "****************************************************************************");
        */

        return (availableBlocks * blockSize);
    }

    public String getSdCardPath ()
    {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
