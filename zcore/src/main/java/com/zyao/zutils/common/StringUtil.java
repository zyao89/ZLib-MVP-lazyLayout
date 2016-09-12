package com.zyao.zutils.common;

import android.content.Context;
import android.text.TextUtils;

import com.zyao.zutils.common.md5.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class: StringUtil
 * Description: 字符串工具类
 * Author: Zyao89
 * Time: 2016/7/27 21:02
 */
public class StringUtil
{
    /** 字符编码格式 */
    public static final String GB2312 = "GB2312";
    public static final String UTF8 = "UTF-8";

    private static StringUtil mInstance = null;
    private final Context mContext;

    private StringUtil (Context context)
    {
        mContext = context;
    }

    /* package */
    static StringUtil with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (StringUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new StringUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 格式化数字为两位数
     *
     * @param c
     *
     * @return
     */
    public String formatCount (int c)
    {
        if (c >= 10)
        {
            return String.valueOf(c);
        }
        else
        {
            return "0" + String.valueOf(c);
        }
    }

    /**
     * 字符串编码转换
     *
     * @param str
     * @param newCharset
     *
     * @return
     */
    public String stringCharset (String str, String newCharset)
    {
        if (str != null)
        {
            // 用默认字符编码解码字符串。与系统相关，Android默认UTF-8
            byte[] bs = str.getBytes();
            try
            {
                // 用新的字符编码生成字符串
                return new String(bs, newCharset);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String inputStreamToString (InputStream is)
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try
        {
            while ((line = in.readLine()) != null)
            {
                buffer.append(line);
            }
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {

                    e.printStackTrace();
                }
                in = null;
            }
        }
        return buffer.toString();
    }

    /**
     * 从XML字符串中提取字段
     *
     * @param str
     * @param str1
     * @param str2
     *
     * @return
     */
    public String getSubString (String str, String str1, String str2)
    {
        int pos1 = str.indexOf(str1);
        int pos2 = str.indexOf(str2);
        if (pos1 < 0 || pos2 < 0 || pos1 + str1.length() >= pos2)
        {
            return null;
        }
        else
        {
            String subString = str.substring(pos1 + str1.length(), pos2);
            return subString;
        }
    }

    /**
     * 将字符串进行base64编码
     *
     * @param src
     *
     * @return
     */
    public String md5Base64 (String src)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(src.getBytes());

            byte[] md5bytes = digest.digest();
            BASE64Encoder encoder = new BASE64Encoder();

            return encoder.encode(md5bytes);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断字符串是否都由数字组成
     *
     * @param str
     *
     * @return
     */
    public boolean isStrNumeric (String str)
    {
        if (TextUtils.isEmpty(str))
        {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * 8位随机数的字符串
     *
     * @return
     */
    public String randomStr ()
    {
        //定义变长字符串
        Random random = new Random();
        //随机生成数字，并添加到字符串
        int nextInt = random.nextInt(90000000);
        nextInt += 10000000;
        return String.valueOf(nextInt);
    }
}
