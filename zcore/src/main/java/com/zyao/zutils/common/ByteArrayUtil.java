package com.zyao.zutils.common;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Class: ByteArrayUtil
 * Description: 字节数组工具类
 * Author: Zyao89
 * Time: 2016/7/28 10:03
 */
public class ByteArrayUtil
{
    private static ByteArrayUtil mInstance = null;
    private final Context mContext;

    private ByteArrayUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static ByteArrayUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (ByteArrayUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ByteArrayUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取字节数组中有效字节
     *
     * @param byteArray
     *
     * @return 有效的字节数组
     */
    public byte[] getValidByte (byte[] byteArray)
    {
        int length = 0;
        for (int i = 0; i < byteArray.length; i++)
        {
            if (byteArray[i] != '\0')
            {
                length++;
            }
            else
            {
                break;
            }
        }
        if (0 == length)
        {
            return null;
        }
        byte[] array = new byte[length];
        System.arraycopy(byteArray, 0, array, 0, length);
        return array;
    }

    /**
     * 将网络库上传的通道名称字节数组转成String
     *
     * @param byteArray 通道名称字节数组
     *
     * @return 通道名称
     */
    public String convertNetSDKByteToString (byte[] byteArray)
    {
        if (null == byteArray || byteArray.length <= 0)
        {
            return null;
        }

        byte[] array = getValidByte(byteArray);
        if (null == array || array.length <= 0)
        {
            return null;
        }
        Locale locale = Locale.getDefault();

        try
        {
            if ("zh_TW".equals(locale.toString()))
            {
                return new String(array, "BIG5").trim();
            }
            else if ("ja_JP".equals(locale.toString()))
            {
                return new String(array, "JISX0208-1").trim();
            }
            else if ("ko_KR".equals(locale.toString()))
            {
                return new String(array, "EUC-KR").trim();
            }
            else if ("iw".equals(locale.getLanguage()) || "IL".equals(locale.getCountry()))
            {
                return new String(array, "ISO-8859-8").trim();
            }
            else
            {
                return new String(array, StringUtil.GB2312).trim();
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return new String(array).trim();
        }
    }
}
