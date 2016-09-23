/**
 * Title: ICommonMethod.java
 * Package: com.zyao.zcore2.base
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zcore2.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

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
     * 跳转新的Activity并且关闭当前Activity
     *
     * @param cls
     * @param intent
     */
    void gotoNewActivityAndFinish (@NonNull Class<?> cls, Intent intent);

    /**
     * @param cls
     */
    void gotoNewActivity (@NonNull Class<?> cls);

    /**
     * 跳转新的Activity
     *
     * @param cls
     * @param intent
     */
    void gotoNewActivity (@NonNull Class<?> cls, Intent intent);

    /**
     * 跳转新的Activity, 待返回值
     *
     * @param cls
     * @param intent
     * @param requestCode
     */
    void gotoNewActivityForResult (@NonNull Class<?> cls, Intent intent, int requestCode);

    /**
     * 跳转新的Activity, 待返回值
     *
     * @param cls
     * @param intent
     * @param bundle
     * @param requestCode
     */
    void gotoNewActivityForResult (@NonNull Class<?> cls, Intent intent, Bundle bundle, int requestCode);

    /**
     * 跳转新的Activity
     *
     * @param cls
     * @param bundle
     */
    void gotoNewActivity (@NonNull Class<?> cls, Bundle bundle);

    /**
     * 带动画的跳转
     *
     * @param triggerView
     * @param intent
     */
    void gotoNewActivityByAnim (View triggerView, @NonNull Intent intent);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Intent intent);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes, long durationMills);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, int colorOrImageRes, long durationMills);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, long durationMills);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, long durationMills);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes);

    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, long durationMills);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, long durationMills);

    /**
     * 带动画的跳转
     *
     * @param triggerView
     * @param targetClass
     * @param intent
     * @param colorOrImageRes
     * @param durationMills
     */
    void gotoNewActivityByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes, long durationMills);

    void gotoNewActivityByAnimAndFinish (View triggerView, @NonNull Class<?> targetClass, Intent intent, int colorOrImageRes, long durationMills);

    /**
     * 带动画的跳转
     *
     * @param triggerView
     * @param targetClass
     * @param requestCode
     */
    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, int requestCode);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode, int colorOrImageRes);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode, long durationMills);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, int requestCode, int colorOrImageRes, long durationMills);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Bundle bundle, int requestCode);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode, int colorOrImageRes);

    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode, long durationMills);

    /**
     * 带动画的跳转
     *
     * @param triggerView
     * @param targetClass
     * @param intent
     * @param bundle
     * @param requestCode
     * @param colorOrImageRes
     * @param durationMills
     */
    void gotoNewActivityForResultByAnim (View triggerView, @NonNull Class<?> targetClass, Intent intent, Bundle bundle, int requestCode, int colorOrImageRes, long durationMills);

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
