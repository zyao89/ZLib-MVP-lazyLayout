package com.zyao.zutils;

/**
 * Interface: AppInfoManager
 * Description: App信息管理类接口
 * Author: Zyao89
 * Time: 2016/7/18 20:28
 */
public interface AppInfoManager
{
    /**
     * 获取程序名称
     *
     * @return 程序名称
     */
    String getAppName ();

    /**
     * 获取当前程序VersionCode
     *
     * @return VersionCode
     */
    int getCurVersionCode ();

    /**
     * 获取App版本
     *
     * @return App版本
     */
    String getAppVersion ();

    /**
     * 当前应用是否处在前台
     *
     * @return true-前台，false-后台
     */
    boolean isTopActivity ();

    /**
     * 获取当前语言
     *
     * @return 当前语言
     */
    String getAppLocaleLanguage ();

    /**
     * 获取应用包名
     *
     * @return 包名
     */
    String getAppPackageName ();
}
