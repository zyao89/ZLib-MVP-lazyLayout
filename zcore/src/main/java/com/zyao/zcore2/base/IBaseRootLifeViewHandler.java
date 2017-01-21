package com.zyao.zcore2.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Interface: IBaseRootViewHandler
 * Description: 公共View基础接口类
 * Author: Zyao89
 * Time: 2016/7/20 19:34
 */
interface IBaseRootLifeViewHandler<ViewType extends View>
{
    /**
     * 初始化，系统自动调用
     *
     * @param rootView
     */
    void onCreate (@NonNull ViewType rootView);

    /**
     * View创建完
     */
    void onViewCreated ();

    void onStart ();

    void onStop ();

    void onPause ();

    void onResume ();

    /**
     * 返回键
     *
     * @return true-消费， false-不消费
     */
    boolean onBackPressed ();

    /**
     * 屏幕发生改变监听
     *
     * @param newConfig
     */
    void onConfigurationChanged (Configuration newConfig);

    /**
     * 销毁调用
     */
    void onDestroy ();

    /**
     * 恢复默认
     *
     * @param savedInstanceState
     */
    void resetDefaultState (Bundle savedInstanceState);
}
