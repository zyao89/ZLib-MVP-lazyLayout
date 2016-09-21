/**
 * Title: ICommonMethod.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Interface: ICommonMethod
 * Description: 通用方法
 * Author: Zyao89
 * Time: 2016/9/21 19:31
 */
interface ICommonMethod
{
    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    void gotoNewActivityAndFinish (@NonNull Class<?> cls);

    /**
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     */
    void gotoNewActivityAndFinish (@NonNull Class<?> cls, Bundle bundle);

    /**
     * 跳转新的Activity
     *
     * @param cls
     */
    void gotoNewActivity (@NonNull Class<?> cls);

    /**
     * 跳转新的Activity
     *
     * @param cls
     * @param bundle
     */
    void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle);

    /**
     * 加载根Fragment
     *
     * @param toFragment
     */
    void loadRootFragment (BaseComponentFragment toFragment);

    /**
     * 替换根Fragment
     *
     * @param toFragment
     * @param addToBack
     */
    void replaceLoadRootFragment (BaseComponentFragment toFragment, boolean addToBack);

    /**
     * 加载多个Fragment
     *
     * @param showPosition
     * @param toFragments
     */
    void loadMultipleRootFragment (int showPosition, BaseComponentFragment... toFragments);
}
