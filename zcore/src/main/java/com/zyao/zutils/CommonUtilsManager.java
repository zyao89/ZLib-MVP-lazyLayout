package com.zyao.zutils;

import com.zyao.zutils.common.ByteArrayUtil;
import com.zyao.zutils.common.CharacterParserUtil;
import com.zyao.zutils.common.CrashUtil;
import com.zyao.zutils.common.DensityUtil;
import com.zyao.zutils.common.LocalFileUtil;
import com.zyao.zutils.common.MultipleClicksUtil;
import com.zyao.zutils.common.NetworkUtil;
import com.zyao.zutils.common.ReflectUtils;
import com.zyao.zutils.common.ScreenLockUtil;
import com.zyao.zutils.common.SdCardUtils;
import com.zyao.zutils.common.StringEncryptUtil;
import com.zyao.zutils.common.StringUtil;
import com.zyao.zutils.common.UTForGBKUtil;
import com.zyao.zutils.common.VibratorUtil;

/**
 * Interface: CommonUtilsManager
 * Description: 通用工具管理接口
 * Author: Zyao89
 * Time: 2016/7/27 16:37
 */
public interface CommonUtilsManager
{
    /**
     * 屏幕分辨率工具
     *
     * @return
     */
    DensityUtil density ();

    /**
     * 全局Crash捕获工具
     *
     * @return
     */
    CrashUtil crash ();

    /**
     * 震动工具
     *
     * @return
     */
    VibratorUtil vibrator ();

    /**
     * 网络工具
     *
     * @return
     */
    NetworkUtil net ();

    /**
     * 屏幕锁控制
     *
     * @return
     */
    ScreenLockUtil screenLock ();

    /**
     * 字符串加解密
     *
     * @param encryptionScheme 加密类型
     * @param encryptionKey    密钥
     *
     * @return
     */
    StringEncryptUtil stringEncrypt (StringEncryptUtil.ENCRYPTION_SCHEME encryptionScheme, String encryptionKey);

    /**
     * 字符串工具类
     *
     * @return
     */
    StringUtil str ();

    /**
     * 字节数组工具类
     *
     * @return
     */
    ByteArrayUtil byteArray ();

    /**
     * 本地文件工具类
     *
     * @return
     */
    LocalFileUtil localFile ();

    /**
     * SdCard工具类
     *
     * @return
     */
    SdCardUtils sdCard ();

    /**
     * 多次点击判断工具类
     *
     * @return
     */
    MultipleClicksUtil mulClick ();

    /**
     * 反射工具类
     *
     * @return
     */
    ReflectUtils reflect ();

    /**
     * 拼音转字母
     *
     * @return
     */
    CharacterParserUtil characterParse ();

    /**
     * UTF或GBK转换
     *
     * @return
     */
    UTForGBKUtil utfOrGbk ();
}
