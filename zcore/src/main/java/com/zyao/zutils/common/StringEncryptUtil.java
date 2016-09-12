package com.zyao.zutils.common;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;


/**
 * Class: StringEncryptUtil
 * Description: 字符串加解密
 * Author: Zyao89
 * Time: 2016/7/18 16:43
 */
public class StringEncryptUtil
{
    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String DES_ENCRYPTION_SCHEME = "DES";

    private static final String UNICODE_FORMAT = "UTF8";
    private static ENCRYPTION_SCHEME mEncryptionScheme;
    private static String mEncryptionKey;
    private static StringEncryptUtil mInstance = null;
    private KeySpec mKeySpec;
    private SecretKeyFactory mKeyFactory;

    private StringEncryptUtil (ENCRYPTION_SCHEME encryptionScheme, String encryptionKey) throws IllegalArgumentException
    {
        if (null == encryptionKey)
        {
            throw new IllegalArgumentException("encryption key was null");
        }
        if (encryptionKey.trim().length() < 24)
        {
            throw new IllegalArgumentException("encryption key was less than 24 characters");
        }

        try
        {
            byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);

            switch (encryptionScheme)
            {
                case DES_EDE:
                    mKeySpec = new DESedeKeySpec(keyAsBytes);
                    break;

                case DES:
                    mKeySpec = new DESKeySpec(keyAsBytes);
                    break;

                default:
                    throw new IllegalArgumentException("encryption scheme not supported: " + encryptionScheme);
            }

            mKeyFactory = SecretKeyFactory.getInstance(encryptionScheme.mScheme);

        }
        catch (InvalidKeyException e)
        {
            throw new IllegalArgumentException(e);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalArgumentException(e);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalArgumentException(e);
        }

    }

    /* package */
    static StringEncryptUtil createStringEncryptUtil (ENCRYPTION_SCHEME encryptionScheme, String encryptionKey)
    {
        if (mInstance == null || mEncryptionScheme == null || !mEncryptionScheme.equals(encryptionScheme) || mEncryptionKey == null || !mEncryptionKey.equals(encryptionKey))
        {
            synchronized (StringEncryptUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new StringEncryptUtil(encryptionScheme, encryptionKey);
                }
            }
        }
        mEncryptionScheme = encryptionScheme;
        mEncryptionKey = encryptionKey;
        return mInstance;
    }

    /**
     * @param unencryptedString 待加密的字符串
     *
     * @return 加密后的字符串
     *
     * @throws IllegalArgumentException 加密过程中可能发生的异常
     * @function stringEncrypt
     * @Description 加密
     */
    public String encrypt (String unencryptedString) throws IllegalArgumentException
    {
        if (null == unencryptedString || unencryptedString.length() == 0)
        {
            throw new IllegalArgumentException("unencrypted string was null or empty");
        }
        try
        {
            SecretKey key = mKeyFactory.generateSecret(mKeySpec);
            Cipher cipher = Cipher.getInstance(mEncryptionScheme.mScheme);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] ciphertext = cipher.doFinal(cleartext);
            byte[] bResult = Base64.encode(ciphertext, Base64.DEFAULT);
            return bytes2String(bResult);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param encryptedStr 加密字符串
     *
     * @return 解密后的字符串
     *
     * @throws IllegalArgumentException 解密异常
     * @function decrypt
     * @Description 解密
     */
    public String decrypt (String encryptedStr) throws IllegalArgumentException
    {
        if (encryptedStr == null || encryptedStr.length() <= 0)
        {
            throw new IllegalArgumentException("stringEncrypt string was null or empty");
        }
        try
        {

            byte[] cleartext = Base64.decode(encryptedStr.getBytes(), Base64.DEFAULT);

            SecretKey key = mKeyFactory.generateSecret(mKeySpec);
            Cipher cipher = Cipher.getInstance(mEncryptionScheme.mScheme);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] ciphertext = cipher.doFinal(cleartext);

            return bytes2String(ciphertext);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param bytes 待转换的字符数组
     *
     * @return 返回字符串
     *
     * @function bytes2String
     * @Description 字符数组转字符串
     */
    private String bytes2String (byte[] bytes)
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 加密类型
     */
    public enum ENCRYPTION_SCHEME
    {
        DES_EDE(DESEDE_ENCRYPTION_SCHEME),
        DES(DES_ENCRYPTION_SCHEME);

        private final String mScheme;

        ENCRYPTION_SCHEME (String scheme)
        {
            mScheme = scheme;
        }
    }

}